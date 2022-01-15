/*

--------------------------------------------
文件分块需要在前端进行处理，可以利用强大的js库或者现成的组件进行分块处理。需要确定分块的大小和分块的数量，然后为每一个分块指定一个索引值。

为了防止上传文件的分块与其它文件混淆，采用文件的md5值来进行区分，该值也可以用来校验服务器上是否存在该文件以及文件的上传状态。

如果文件存在，直接返回文件地址；
如果文件不存在，但是有上传状态，即部分分块上传成功，则返回未上传的分块索引数组；
如果文件不存在，且上传状态为空，则所有分块均需要上传。
fileRederInstance.readAsBinaryString(file);
fileRederInstance.addEventListener("load", (e) => {
    let fileBolb = e.target.result;
    fileMD5 = md5(fileBolb);
    const formData = new FormData();
    formData.append("md5", fileMD5);
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
在调用上传接口前，通过slice方法来取出索引在文件中对应位置的分块。

const getChunkInfo = (file, currentChunk, chunkSize) => {
       //获取对应下标下的文件片段
       let start = currentChunk * chunkSize;
       let end = Math.min(file.size, start + chunkSize);
       //对文件分块
       let chunk = file.slice(start, end);
       return { start, end, chunk };
   };
之后调用上传接口完成上传。
--------------------------------------------
*/
//mocha-js文件上传
const readable = fs.createReadStream(filePath,
{encoding:'utf-8',highWaterMark:chunkSize})
async function Upload(readable, fileType, fileFid,){
  let fileSize = fs.statSync(filePath).size
  let totalChunks=parseInt(fileSize/chunkSize)===0?1:parseInt(fileSize/chunkSize)
  let actChunks=fileSize/chunkSize
  let chunkNumber=0
  let fileName="sample.json1"
  let data='';
  let uploadRes;
  for await(let chunk of readable){
     //chunk.length
     chunkNumber=chunkNumber+1
     if(actChunks>totalChunks && chunkNumber==totalChunks){
       data = chunk
     }
     else if(chunkNumber===(totalChunks+1)||actChunks<1){
       data=data+chunk
       uploadRes=await uploadChunk(fileType,totalChunks,chunkSize,data,f)
       return true
     }else if(chunkNumber<totalChunks){
       data = chunk
       uploadRes=await uploadChunk(fileType,chunkNumber,chunkSize,data,f)
     }
  }
  return false
}
function uploadChunk(fileType,chunkNumber,chunkSize,getChunk,fileSize,fileFid,)
{
    let url='/actions/files/upload?'+`resumableChunkNumber=${chunkNumber}&resumableChunkNumber`
    const form=new FormData();
    form.append('file',getChunk,{"filename":filename,"contentType":"application"})
    APIHeaders(form.getHeaders())
    form.append('resumableChunkNumber',chunkNumber)
    form.append('resumableChunkSize',chunkSize)
    form.append('resumableCurrentChunkSize',getChunk.length)
    form.append('resumableTotalSize',fileSize)
    form.append('resumableIdentifier',fileFid)
    form.append('resumableType','')
    form.append('resumableFilename',fileName)
    form.append('resumableRelativePath',fileName)
    form.append('resumableTotalChunks',totalChunks)
    form.append('type',fileType)
    return APIPost(url,form)
}
def getBigFileMD5(file_path):
  md5_obj=hashlib.md5()
  max_buf = 8190
  f=open(file_path,'rb')
  while True:
     buf = f.read(max_buf)
     if not buf:
        break;
     md5_obj.update(buf)
     f.close()
     file_hash=md5_obj.hexdigest()

