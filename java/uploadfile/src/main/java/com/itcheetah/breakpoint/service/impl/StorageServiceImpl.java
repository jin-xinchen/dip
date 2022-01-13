package com.itcheetah.breakpoint.service.impl;

import com.itcheetah.breakpoint.constants.UploadConstants;
import com.itcheetah.breakpoint.model.dto.MultipartFileDTO;
import com.itcheetah.breakpoint.service.StorageService;
import com.itcheetah.breakpoint.utils.FileMD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zyq
 * @description 存储实现类
 * @date 2021/5/21 11:17
 **/
@Slf4j
@Service
@Transactional
public class StorageServiceImpl implements StorageService {

    // 保存文件的根目录
    private Path rootPath;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //这个必须与前端设定的值一致
    @Value("${breakpoint.upload.chunkSize}")
    private long CHUNK_SIZE;

    @Value("${breakpoint.upload.dir}")
    private String finalDirPath;

    @Autowired
    public StorageServiceImpl(@Value("${breakpoint.upload.dir}") String location) {
        this.rootPath = Paths.get(location);
    }


    @Override
    public void deleteAll() {
        log.info("开发初始化清理数据，start");
        FileSystemUtils.deleteRecursively(rootPath.toFile());
        stringRedisTemplate.delete(UploadConstants.FILE_UPLOAD_STATUS);
        stringRedisTemplate.delete(UploadConstants.FILE_MD5_KEY);
        log.info("开发初始化清理数据，end");
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootPath);
        } catch (FileAlreadyExistsException e) {
            log.error("文件夹已经存在了，不用再创建。");
        } catch (IOException e) {
            log.error("初始化root文件夹失败。", e);
        }
    }

    @Override
    public void uploadFileRandomAccessFile(MultipartFileDTO multipartFileDTO) throws IOException {
        String fileName = multipartFileDTO.getName();
        String tempDirPath = finalDirPath + multipartFileDTO.getMd5();
        String tempFileName = fileName + "_tmp";
        File tmpDir = new File(tempDirPath);
        File tmpFile = new File(tempDirPath, tempFileName);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");
        long offset = CHUNK_SIZE * multipartFileDTO.getChunk();
        //定位到该分片的偏移量
        accessTmpFile.seek(offset);
        //写入该分片数据
        accessTmpFile.write(multipartFileDTO.getFile().getBytes());
        // 释放
        accessTmpFile.close();

        boolean isOk = checkAndSetUploadProgress(multipartFileDTO, tempDirPath);
        if (isOk) {
            boolean flag = renameFile(tmpFile, fileName);
            System.out.println("upload complete !!" + flag + " name=" + fileName);
        }
    }

    @Override
    public boolean uploadFileByMappedByteBuffer(MultipartFileDTO multipartFileDTO) throws IOException {
        //路径：服务器存储地址/md5字符串
        String uploadDirPath = finalDirPath + multipartFileDTO.getMd5();
        File tmpDir = new File(uploadDirPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        String fileName = multipartFileDTO.getName();
        //临时文件名
        String tempFileName = fileName + "_tmp";
        File tmpFile = new File(uploadDirPath, tempFileName);

        //读操作和写操作都是允许的
        RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
        //它返回的就是nio通信中的file的唯一channel
        FileChannel fileChannel = tempRaf.getChannel();

        //写入该分片数据   分片大小 * 第几块分片获取偏移量
        long offset = CHUNK_SIZE * multipartFileDTO.getChunk();
        //分片文件大小
        byte[] fileData = multipartFileDTO.getFile().getBytes();
        //将文件的区域直接映射到内存
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
        mappedByteBuffer.put(fileData);
        // 释放
        FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
        fileChannel.close();

        boolean isOk = checkAndSetUploadProgress(multipartFileDTO, uploadDirPath);
        if (isOk) {
            boolean flag = renameFile(tmpFile, fileName);
            System.out.println("upload complete !!" + flag + " name=" + fileName);
            return flag;
        }
        return false;
    }

    /**
     * 检查并修改文件上传进度
     *
     * @multipartFileDTO multipartFileDTO
     * @multipartFileDTO uploadDirPath
     * @return
     * @throws IOException
     */
    private boolean checkAndSetUploadProgress(MultipartFileDTO multipartFileDTO, String uploadDirPath) throws IOException {
        String fileName = multipartFileDTO.getName();
        //路径/filename.conf
        File confFile = new File(uploadDirPath, fileName + ".conf");
        RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");
        //把该分段标记为 true 表示完成
        System.out.println("set part " + multipartFileDTO.getChunk() + " complete");
        accessConfFile.setLength(multipartFileDTO.getChunks());
        accessConfFile.seek(multipartFileDTO.getChunk());
        accessConfFile.write(Byte.MAX_VALUE);

        //completeList 检查是否全部完成,如果数组里是否全部都是(全部分片都成功上传)
        byte[] completeList = FileUtils.readFileToByteArray(confFile);
        byte isComplete = Byte.MAX_VALUE;
        for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
            //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
            isComplete = (byte) (isComplete & completeList[i]);
            System.out.println("check part " + i + " complete?:" + completeList[i]);
        }

        accessConfFile.close();
        //更新redis中的状态：如果是true的话证明是已经该大文件全部上传完成
        if (isComplete == Byte.MAX_VALUE) {
            stringRedisTemplate.opsForHash().put(UploadConstants.FILE_UPLOAD_STATUS, multipartFileDTO.getMd5(), "true");
            stringRedisTemplate.opsForValue().set(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getMd5(), uploadDirPath + "/" + fileName);
            return true;
        } else {
            if (!stringRedisTemplate.opsForHash().hasKey(UploadConstants.FILE_UPLOAD_STATUS, multipartFileDTO.getMd5())) {
                stringRedisTemplate.opsForHash().put(UploadConstants.FILE_UPLOAD_STATUS, multipartFileDTO.getMd5(), "false");
            }
            if (!stringRedisTemplate.hasKey(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getMd5())) {
                stringRedisTemplate.opsForValue().set(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getMd5(), uploadDirPath + "/" + fileName + ".conf");
            }
            return false;
        }
    }

    /**
     * 文件重命名
     *
     * @multipartFileDTO toBeRenamed   将要修改名字的文件
     * @multipartFileDTO toFileNewName 新的名字
     * @return
     */
    public boolean renameFile(File toBeRenamed, String toFileNewName) {
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            log.info("File does not exist: " + toBeRenamed.getName());
            return false;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        //修改文件名
        return toBeRenamed.renameTo(newFile);
    }

}
