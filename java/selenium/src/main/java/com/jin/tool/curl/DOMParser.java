package com.jin.tool.curl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.util.Iterator;

/***
 * <dependency>
 * 　　<groupId>dom4j</groupId>
 * 　　<artifactId>dom4j</artifactId>
 * 　　<version>1.6.1</version>
 * </dependency>
 * https://www.cnblogs.com/acm-bingzi/p/java_dom4j.html
 * https://blog.csdn.net/qq_36551991/article/details/119063761
 */
public class DOMParser {
    public static void main(String[] args) {
        String strXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <Notification xmlns=\"http://mns.aliyuncs.com/doc/v1/\"> <TopicOwner>1692545896541241</TopicOwner> <TopicName>MyTopic</TopicName> <Subscriber>1692545896541241</Subscriber> <SubscriptionName>bing-test3</SubscriptionName> <MessageId>C39FB8C345BBFBA8-1-1687F6FAADD-200000015</MessageId> <MessageMD5>CAA1E9F5E9F854ACD8297B100BF8CCF9</MessageMD5> <Message>{\"jobId\":\"2384a4d89b1d4f1e869559e2ff8c9fad\",\"requestId\":\"639D1D03-1557-4AD7-9AD7-691F02834516\",\"Type\":\"Transcode\",\"state\":\"Success\",\"type\":\"Transcode\",\"State\":\"Success\",\"JobId\":\"2384a4d89b1d4f1e869559e2ff8c9fad\",\"RequestId\":\"639D1D03-1557-4AD7-9AD7-691F02834516\"}</Message> <PublishTime>1548326251229</PublishTime> </Notification>";
strXML="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<shipment-info xmlns=\"http://www.canadapost.ca/ws/shipment-v8\">\n" +
        "    <customer-request-id>DARWH02001SO060622000001</customer-request-id>\n" +
        "    <shipment-id>906161654537700282</shipment-id>\n" +
        "    <shipment-status>created</shipment-status>\n" +
        "    <tracking-pin>9833772553625322</tracking-pin>\n" +
        "    <links>\n" +
        "        <link rel=\"self\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>\n" +
        "        <link rel=\"details\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282/details\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>\n" +
        "        <link rel=\"price\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282/price\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>\n" +
        "        <link rel=\"group\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment?groupId=2022-06-06\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>\n" +
        "        <link rel=\"label\" href=\"https://soa-gw.canadapost.ca/rs/artifact/6392e3e393b5dd4b/10342920948/0\" media-type=\"application/pdf\" index=\"0\"/>\n" +
        "    </links>\n" +
        "</shipment-info>";
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(strXML);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();// 指向根节点

        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();// 一个Item节点
            System.out.println(element.getName() + " : " + element.getTextTrim());
        }
    }
}

