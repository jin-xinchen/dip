
var xmlHttp=false;      
function createXMLHttpRequest() {
  if (window.ActiveXObject){
      xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
  }else if (window.XMLHttpRequest){
      xmlHttp = new XMLHttpRequest();
  }  
}
function check(){
createXMLHttpRequest(); //XMLHttpRequest
xmlHttp.onreadystatechange=callback;   //callback function
            nameStr=myform.name.value;
            passStr=myform.pass.value;
var url="check?name="+nameStr+"&pass="+passStr; 
//sent into servlet/check
xmlHttp.open("get",url);      //to server
xmlHttp.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
xmlHttp.send(null); 
}
function callback(){
        if(xmlHttp.readyState==4){  
        if(xmlHttp.status==200){
    var str = xmlHttp.responseText;
            alert(str);
if(str.length==2){    //get permission
                           document.getElementById("state").innerHTML="Logined";
var tdName=document.getElementById("tdName");
                            tdName.replaceChild(document.createTextNode(nameStr),tdName.firstChild);//replace node
 var tdPass=document.getElementById("pwdBox");
 tdPass.innerHTML="";
var trButtom=document.getElementById("buttom");
trButtom.innerHTML='<font color=\"red\">login</font>  
<div> 
<input type="button" onclick="exit()" value="Logout" /></div>';
        trButtom.name="yes"; 
             }else{
                            document.getElementById("state").innerHTML="<font color=\"red\">Error!</font>";
                        }   
                    }
                }       
            }