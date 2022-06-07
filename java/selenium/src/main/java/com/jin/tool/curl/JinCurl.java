package com.jin.tool.curl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JinCurl {
//    https://codeantenna.com/a/keEYWf5nCn
    //https://codeantenna.com/a/z9uIxrdp3s
    //在服务器上的文件jre/lib/security/java.security中  jdk.tls.disabledAlgorithms=SSLv2Hello,SSLv3,TLSv1,TLSv1.1设定后，服务器只接受TLS1.2连接并拒绝较低的安全协议版本。
    public static String execCurl(String[] cmds){
        ProcessBuilder process = new ProcessBuilder(cmds);
//https://blog.csdn.net/zhaofuqiangmycomm/article/details/108620887
        Process p = null;
        try {
            p = process.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getHttpPost(String address, String requestJson){
        String[] cmds ={"curl","--location","--request","GET","https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/318641652971248020",
        "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic NjM5MmUzZTM5M2I1ZGQ0Yjo3MGRiNjZkNTYyY2UwZDZlN2MyZjNh",
                "--header","Cookie: OWSPRD001SHIP=ship_02660_s013ptom001"};
//        curl --location --request GET 'https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/318641652971248020' \
//        --header 'Accept: application/vnd.cpc.shipment-v8+xml' \
//        --header 'Accept-language: en-CA' \
//        --header 'Content-Type: application/vnd.cpc.shipment-v8+xml' \
//        --header 'Authorization: Basic NjM5MmUzZTM5M2I1ZGQ0Yjo3MGRiNjZkNTYyY2UwZDZlN2MyZjNh' \
//        --header 'Cookie: OWSPRD001SHIP=ship_02660_s013ptom001'
//        "--connect-timeout", "5","m","6"
        String resMsg = execCurl(cmds);
        System.out.println(resMsg);
        return resMsg;
    }
public static void test(){//https://www.cnblogs.com/miaoying/p/12426857.html
    String[] cmds = {"curl", "-X", "POST",
            "http://localhost:9999/my/url?param1=1&param2=2",
            "-H", "accept: */*", "-H", "Content-Type: application/json;charset=UTF-8", "-d"
            , "{ \\\"bodyName\\\": \\\"bodyValue\\\"}"};
    System.out.println(execCurl(cmds));
}
    public static void main(String[] args) {
        //test();
        getHttpPost(null,null);
    }
}
