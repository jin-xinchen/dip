package com.jin.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * https://curl.se/docs/faq.html#How_do_I_tell_curl_to_follow_H
 */
public class DemoTest {
    public static void main(String[] args) {
        String data ="{\"orderBy\":0\"maxResults\":50}";
        String WEFI_MAIL_URL ="http://www.mysite.com/";
//        HttpClient httpclient = new DefaultHttpClient();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String out = "";
        try {
            HttpPost httpost = new HttpPost(WEFI_MAIL_URL);
            httpost.setHeader("User-Agent","Apache-HttpClient/4.1 (java 1.5)");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("id","105"));
            nvps.add(new BasicNameValuePair("json", data));
            httpost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
//            public static final Charset UTF_8 = Charset.forName("UTF-8");
            HttpResponse response = httpclient.execute(httpost);
            //方法 2 不能方法1 并用。
//            if (response.getStatusLine().getStatusCode() == 200) {
//                HttpEntity resEntity = response.getEntity();
//                //EntityUtils.toString 底层也采取类似方法1：InputStream is = entity.getContent();方法
//                String message = EntityUtils.toString(resEntity, "utf-8");
//                System.out.println(message);
//            } else {
//                System.out.println("请求失败");
//            }
            //方法 1
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ( (line = br.readLine()) != null) {
                    out += line;
                }
                is.close();
                return;
//                if(isTimeOut == false){
//                    _loadActual();
//                }
//                else{
//                    return;
//                }
            } else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (httpclient != null) {
            httpclient.getConnectionManager().shutdown();
        }
    }
}


