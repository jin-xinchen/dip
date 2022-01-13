package com.itcheetah.breakpoint.controller;

import com.itcheetah.breakpoint.constants.UploadConstants;
import com.itcheetah.breakpoint.model.dto.MultipartFileDTO;
import com.itcheetah.breakpoint.model.result.Result;
import com.itcheetah.breakpoint.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @className: LargeFileUploadController
 * @description: 大文件上传类
 * @author: itcheetah
 * @date: 2021/7/31 10:30
 * @Version: 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/fileUpload")
public class LargeFileUploadController {

    private final StringRedisTemplate stringRedisTemplate;

    private final StorageService storageService;


    /**
     * @author itcheetah
     * @description 秒传判断，断点判断
     * @date 2021/7/31 10:33
     * @Param [md5]
     * @return java.lang.Object
     **/
    @PostMapping("/checkFileMd5")
    @ResponseBody
    public Result checkFileMd5(String md5) throws IOException {
        //文件是否上传
        Object processingObj = stringRedisTemplate.opsForHash().get(UploadConstants.FILE_UPLOAD_STATUS, md5);
        if (processingObj == null) {
            return Result.ok("该文件没有上传过");
        }
        boolean processing = Boolean.parseBoolean(processingObj.toString());
        String value = stringRedisTemplate.opsForValue().get(UploadConstants.FILE_MD5_KEY + md5);
        //完整文件上传完成是true，未完成时false
        if (processing) {
            return Result.ok(value,"文件已存在");
        } else {
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<Integer> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    //用空格补齐
                    missChunkList.add(i);
                }
            }
            return Result.ok(missChunkList,"该文件上传了一部分");
        }
    }

    /**
     * 上传文件
     *
     * @param multipartFileDTO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result fileUpload(MultipartFileDTO multipartFileDTO, HttpServletRequest request) {
        //是否是带文件的请求
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            log.info("上传文件start。");
            try {
                // 方法1
//                storageService.uploadFileRandomAccessFile(param);in
                // 方法2 这个更快点
                boolean flag = storageService.uploadFileByMappedByteBuffer(multipartFileDTO);
                if(flag){
                    return Result.ok("上传成功");
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("文件上传失败。{}", multipartFileDTO.toString());
            }
            log.info("上传文件end。");
        }
        return Result.ok("分片上传成功");
    }



}
