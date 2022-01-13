import md5 from "js-md5";//md5
import axios from "axios";//axios
export const uploadByPieces = ({
           file,//文件
           pieceSize = 2,//默认单片2M
           progress,
           success,//成功返回前台页面的值
           error,
       }) => {
    // 上传过程中用到的变量
    let fileMD5 = ""; // 总文件列表
    const chunkSize = pieceSize * 1024 * 1024; // pieceSize MB一片
    const chunkCount = Math.ceil(file.size / chunkSize); // 总片数
    var chunkArr = [];//断点续传用到的数组
    var http = process.env.VUE_APP_API_BASE_URL;//ip地址
    // 获取md5
    const readFileMD5 = () => {
        // 读取视频文件的md5
        let fileRederInstance = new FileReader();
        fileRederInstance.readAsBinaryString(file);
        fileRederInstance.addEventListener("load", (e) => {
            let fileBolb = e.target.result;
            fileMD5 = md5(fileBolb);
            const formData = new FormData();
            formData.append("md5", fileMD5);//md5
            axios
                .post(http + "/fileUpload/checkFileMd5", formData)
                .then((res) => {
                    if (res.data.message == "文件已存在") {
                        //文件已存在不走后面分片了，直接返回文件地址到前台页面
                        success && success(res);
                    } else {
                        //文件不存在存在两种情况，一种是返回data：null代表未上传过 一种是data:[xx，xx] 还有哪几片未上传
                        if (!res.data.data) {
                            //还有几片未上传情况，断点续传
                            chunkArr = res.data.data;
                        }
                        readChunkMD5();
                    }
                })
                .catch((e) => {});
        });
    };
    const getChunkInfo = (file, currentChunk, chunkSize) => {
        //获取对应下标下的文件片段
        let start = currentChunk * chunkSize;
        let end = Math.min(file.size, start + chunkSize);
        //对文件分块
        let chunk = file.slice(start, end);
        return { start, end, chunk };
    };
    // 针对每个文件进行chunk处理
    const readChunkMD5 = () => {
        // 先判断 是否存在片段 数组长度0 无片段 反之有片段 断点续传
        if (chunkArr.length > 0) {
            for (let item of chunkArr) {
                const { chunk } = getChunkInfo(file, item, chunkSize);
                uploadChunk({ chunk, currentChunk: item, chunkCount });
            }
        } else {
            for (var i = 0; i < chunkCount; i++) {
                const { chunk } = getChunkInfo(file, i, chunkSize);
                uploadChunk({ chunk, currentChunk: i, chunkCount });
            }
        }
    };
    const uploadChunk = (chunkInfo) => {
        // 上传接口 并发上传
        let fetchForm = new FormData();
        fetchForm.append("chunk", chunkInfo.currentChunk);
        fetchForm.append("chunkSize", chunkSize);
        fetchForm.append("chunks", chunkInfo.chunkCount);
        fetchForm.append("file", chunkInfo.chunk);
        fetchForm.append("md5", fileMD5);
        fetchForm.append("name", file.name);
        axios
            .post(http + "/fileUpload/upload", fetchForm)
            .then((res) => {
                // message 为上传成功时候 代表 后端已经把分片文件合并成一个完整文件 这时走校验接口取返回的文件地址返回给前台页面
                if (res.data.message == "上传成功") {
                    const formData = new FormData();
                    formData.append("md5", fileMD5);
                    axios
                        .post(http + "/fileUpload/checkFileMd5", formData)
                        .then((res) => {
                            if (res.data.message == "文件已存在") {
                                //这时res.data.data 就是文件地址了 返回到前台页面
                                success && success(res);
                            } else {
                            }
                        });
                }
            })
            .catch((e) => {
                error && error(e);
            });
    };
    readFileMD5(); // 开始执行代码
};
