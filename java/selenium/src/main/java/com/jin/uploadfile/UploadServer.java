package com.jin.uploadfile;

/*
如果文件完整上传，返回文件路径；部分上传则返回未上传的分块数组；如果未上传过返回提示信息。

❝
在上传分块时会产生两个文件，一个是文件主体，一个是临时文件。临时文件可以看做是一个数组文件，为每一个分块分配一个值为127的字节。

❞
校验MD5值时会用到两个值：

文件上传状态：只要该文件上传过就不为空，如果完整上传则为true，部分上传返回false；
文件上传地址：如果文件完整上传，返回文件路径；部分上传返回临时文件路径。
 */
public class UploadServer {
    /**
     * 校验文件的MD5
     **/
//    public Result checkFileMd5(String md5) throws IOException {
//        //文件是否上传状态：只要该文件上传过该值一定存在
//        Object processingObj = stringRedisTemplate.opsForHash().get(UploadConstants.FILE_UPLOAD_STATUS, md5);
//        if (processingObj == null) {
//            return Result.ok("该文件没有上传过");
//        }
//        boolean processing = Boolean.parseBoolean(processingObj.toString());
//        //完整文件上传完成时为文件的路径，如果未完成返回临时文件路径（临时文件相当于数组，为每个分块分配一个值为127的字节）
//        String value = stringRedisTemplate.opsForValue().get(UploadConstants.FILE_MD5_KEY + md5);
//        //完整文件上传完成是true，未完成返回false
//        if (processing) {
//            return Result.ok(value,"文件已存在");
//        } else {
//            File confFile = new File(value);
//            byte[] completeList = FileUtils.readFileToByteArray(confFile);
//            List<Integer> missChunkList = new LinkedList<>();
//            for (int i = 0; i < completeList.length; i++) {
//                if (completeList[i] != Byte.MAX_VALUE) {
//                    //用空格补齐
//                    missChunkList.add(i);
//                }
//            }
//            return Result.ok(missChunkList,"该文件上传了一部分");
//        }
//    }
//    public void test(){
//
////        分块上传、文件合并
////        上边我们提到了利用文件的md5值来维护分块和文件的关系，因此我们会将具有相同md5值的分块进行合并，由于每个分块都有自己的索引值，所以我们会将分块按索引像插入数组一样分别插入文件中，形成完整的文件。
////
////        分块上传时，要和前端的分块大小、分块数量、当前分块索引等对应好，以备文件合并时使用，此处我们采用的是「磁盘映射」的方式来合并文件。
//
//        //读操作和写操作都是允许的
//        RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
////它返回的就是nio通信中的file的唯一channel
//        FileChannel fileChannel = tempRaf.getChannel();
////写入该分片数据   分片大小 * 第几块分片获取偏移量
//        long offset = CHUNK_SIZE * multipartFileDTO.getChunk();
////分片文件大小
//        byte[] fileData = multipartFileDTO.getFile().getBytes();
////将文件的区域直接映射到内存
//        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
//        mappedByteBuffer.put(fileData);
//// 释放
//        FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
//        fileChannel.close();
//        //--------------------------------------------------------
//        //--------------------------------------------------------
//        //每当完成一次分块的上传，还需要去检查文件的上传进度，看文件是否上传完成。
//        RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");
////把该分段标记为 true 表示完成
//        accessConfFile.setLength(multipartFileDTO.getChunks());
//        accessConfFile.seek(multipartFileDTO.getChunk());
//        accessConfFile.write(Byte.MAX_VALUE);
//
////completeList 检查是否全部完成,如果数组里是否全部都是(全部分片都成功上传)
//        byte[] completeList = FileUtils.readFileToByteArray(confFile);
//        byte isComplete = Byte.MAX_VALUE;
//        for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
//            //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
//            isComplete = (byte) (isComplete & completeList[i]);
//        }
//        accessConfFile.close();
//        //--------------------------------------------------------
//        //--------------------------------------------------------
//        //然后更新文件的上传进度到Redis中。
//
////更新redis中的状态：如果是true的话证明是已经该大文件全部上传完成
//        if (isComplete == Byte.MAX_VALUE) {
//            stringRedisTemplate.opsForHash().put(UploadConstants.FILE_UPLOAD_STATUS, multipartFileDTO.getMd5(), "true");
//            stringRedisTemplate.opsForValue().set(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getMd5(), uploadDirPath + "/" + fileName);
//        } else {
//            if (!stringRedisTemplate.opsForHash().hasKey(UploadConstants.FILE_UPLOAD_STATUS, multipartFileDTO.getMd5())) {
//                stringRedisTemplate.opsForHash().put(UploadConstants.FILE_UPLOAD_STATUS, multipartFileDTO.getMd5(), "false");
//            }
//            if (!stringRedisTemplate.hasKey(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getMd5())) {
//                stringRedisTemplate.opsForValue().set(UploadConstants.FILE_MD5_KEY + multipartFileDTO.getMd5(), uploadDirPath + "/" + fileName + ".conf");
//            }
//        }
//    }
}
