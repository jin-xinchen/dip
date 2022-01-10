package com.jin.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

//import com.isoftstone.core.common.constant.RequestConstants;
//import com.isoftstone.core.common.tools.XmlTool;
//import com.isoftstone.core.service.intf.ServiceOfStringPara;

/**
 * http s : / / w w w .cnblogs.com/jepson6669/p/9358244.html#headline1-14
 * https://github.com/hunterhacker/jdom/
 * 德勤定价系统,由核心主动调用
 * @author King
 *
 */
public class DeloittePricingSingleCarImpl
//        implements ServiceOfStringPara
{
    private  String serviceUrl = "http://10.30.0.35:7001/ZSInsUW/Auto/PricingService";

//    private static Logger log = Logger.getLogger(DeloittePricingSingleCarImpl.class.getName());

    public String invoke(String sRequest) {

        StringBuffer soapRequestData = new StringBuffer();
        soapRequestData.append("<soapenv:Envelope");
        soapRequestData.append("  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
        soapRequestData.append("  xmlns:prov=\"http://provider.webservice.zsins.dtt.com/\">");
        soapRequestData.append(" <soapenv:Header/> ");
        soapRequestData.append("<soapenv:Body>");
        soapRequestData.append("<prov:executePrvPricing>");
        soapRequestData.append("<arg0>");
        soapRequestData.append("<![CDATA[" + sRequest + "]]>");
        soapRequestData.append("</arg0>");
        soapRequestData.append("</prov:executePrvPricing>");
        soapRequestData.append(" </soapenv:Body>");
        soapRequestData.append("</soapenv:Envelope>");

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(serviceUrl);

        StringEntity content =new StringEntity(soapRequestData.toString(), Charset.forName("UTF-8"));// 第二个参数，设置后才会对，内容进行编码
        content.setContentType("application/soap+xml; charset=UTF-8");
        content.setContentEncoding("UTF-8");
        httppost.setEntity(content);

        //用下面的服务器端以UTF-8接收到的报文会乱码,原因未知
//        HttpEntity reqEntity = EntityBuilder.create().setContentType(
//                ContentType.TEXT_PLAIN) // .TEXT_PLAIN
//                .setText(soapRequestData.toString()).build();
//        httppost.setEntity(reqEntity);
//        httppost.addHeader("Content-Type",
//                "application/soap+xml; charset=utf-8");

        HttpResponse response = null;
        Document doc = null;
        String returnXml = null;
        String sentity = null;
        try {
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                sentity = EntityUtils.toString(resEntity, "UTF-8");
                SAXBuilder builder = new SAXBuilder();
//                Document doc = builder.build(new File(filename));
                doc = builder.build(sentity);
//                doc = XmlTool.getDocument(sentity, RequestConstants.ENCODE);
                System.out.println(doc.toString());
                Element eRoot = doc.getRootElement();
                Element body = eRoot.getChild("Body", eRoot.getNamespace());
                Element resp = (Element) body.getChildren().get(0);
                Element returnele = resp.getChild("return");
                returnXml = returnele.getText().toString();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            log.info("发送给德勤定价系统的请求报文：\n" + soapRequestData.toString());
//            log.info("德勤定价系统返回的响应报文：\n" + sentity);
//            log.info("返回给核心的的报文:\n" + returnXml);
        }
        return returnXml;
    }


    public String getServiceUrl() {
        return serviceUrl;
    }


    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }


    public static void main(String[] args) throws Exception{
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String path = ClassLoader.getSystemResource("").getPath();

//        String filePath = path + "static"+ SystemConstant.SF_FILE_SEPARATOR+"sitemap.xml";
        String filePath = path + "static"+ File.separator+"sitemap.xml"; //File.pathSeparator

        File file = new File("src/test/java/testFile/test.txt");
        System.out.println(file.exists());

        String temp2 = null;
        StringBuilder sb2 = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"GBK");
        BufferedReader br = new BufferedReader(isr);
        temp2 = br.readLine();

        while( temp2 != null ){
            sb2.append(temp2);
            temp2 = br.readLine();
        }
        String sss = sb2.toString();
//        System.out.println(sss.toString());
        new DeloittePricingSingleCarImpl().invoke(sss);
    }
}

