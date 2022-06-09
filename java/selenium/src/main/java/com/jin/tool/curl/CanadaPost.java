package com.jin.tool.curl;

import com.jin.tool.canadapost.Group;
import com.jin.tool.canadapost.ParserCanadapost;
import com.jin.tool.canadapost.Shipment;
import com.jin.tool.canadapost.ShipmentInfo;
import org.dom4j.DocumentException;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CanadaPost {
    private String k="w4sZ-NjM5MmUzZTM5M2I1ZGQ0Yjo3MGRiNjZkNTYyY2UwZDZlN2MyzjNh";

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public List<Group> getGroups() throws DocumentException {
        String[] cmds ={"curl","--location","--request","GET","https://soa-gw.canadapost.ca/rs/0009833772/0009833772/group",
                "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic "+k,
                "--header","Cookie: OWSPRD001SHIP=ship_02660_s013ptom001"};
        List<Group> gs = new ArrayList<Group>();
            gs = ParserCanadapost.getGroupsFromXML(Curl.execCurl(cmds));
        return gs;
    }
    public List<Shipment> getShipmentsInGroup(String url) throws DocumentException {
        String[] cmds ={"curl","--location","--request","GET","https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/318641652971248020",
                "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic "+k,
                "--header","Cookie: OWSPRD001SHIP=ship_02660_s013ptom001"};
        cmds[4]=url;
        List<Shipment> sms = new ArrayList<Shipment>();
        sms = ParserCanadapost.getShipmentsFromXML(Curl.execCurl(cmds));
        return sms;
    }
    private ShipmentInfo getShipmentInfo(String href) throws DocumentException {
        String[] cmds ={"curl","--location","--request","GET","https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/318641652971248020",
                "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic "+k,
                "--header","Cookie: OWSPRD001SHIP=ship_02660_s013ptom001"};
        cmds[4]=href;
        ShipmentInfo sInfo = ParserCanadapost.getShipmentInfoFromXML(Curl.execCurl(cmds));
        return sInfo;
    }

    /**
             "https://www.canadapost-postescanada.ca/track-reperage/en#/search?searchFor=9833772553625322";
             https://www.canadapost-postescanada.ca/track-reperage/rs/track/json/package?pins=9833772553625322
             https://www.canadapost-postescanada.ca/track-reperage/assets/i18n/locales.json
             https://www.canadapost-postescanada.ca/track-reperage/assets/i18n/fr.json
             https://www.canadapost-postescanada.ca/track-reperage/assets/i18n/en.json
             https://www.canadapost-postescanada.ca/track-reperage/assets/i18n/locales.json
             {
                 "locales": ["en", "fr"],
                 "prefix": "routes."
             }
             https://www.canadapost-postescanada.ca/track-reperage/meta/en
             {"siteKey":"6LcNuYwaAAAAAJzTWsiwLRN5VuwBL0azUtS3xHFS","cpwa":"https://payment.canadapost.ca/payment","eventName":"trackweb-event","mma":false,"serviceAlert":"","language":"en"}
             https://www.canadapost-postescanada.ca/track-reperage/rs/track/json/package?refNbrs=9833772553625322
             https://www.canadapost-postescanada.ca/track-reperage/rs/track/json/package?refNbrs=9833772553625322
             [{"refNbr1":"9833772553625322","error":{"cd":"004","descEn":"No PIN History","descFr":"Aucun historique pour le NIP"}}]
             https://www.canadapost-postescanada.ca/track-reperage/rs/track/json/package?pins=9833772553625322
             [{"pin":"9833772553625322","error":{"cd":"004","descEn":"No PIN History","descFr":"Aucun historique pour le NIP"}}]
     https://www.canadapost-postescanada.ca/track-reperage/rs/track/json/package?pins=9833772175809322
     [{"pin":"9833772175809322","deliveryOptions":[{"cd":"SignatureOption","compliance":"No","descEn":"Signature Required","descFr":"Signature Requise"}],"finalEvent":true,"delivered":true,"status":"Delivered","shippedDateTime":{"date":"2022-06-07","time":"02:24:20","zoneOffset":"-04:00"},"acceptedDate":"2022-05-20","attemptedDlvryDate":"2022-05-25","actualDlvryDate":"2022-05-25","shipToAddr":{"addrLn1":"","addrLn2":"","countryCd":"","city":"","regionCd":"","postCd":""},"latestEvent":{"cd":"1498","webCd":"CODE-001","datetime":{"date":"2022-05-25","time":"19:11:34","zoneOffset":"-04:00"},"locationAddr":{"countryCd":"CA","countryNmEn":"Canada","countryNmFr":"Canada","city":"MARKHAM","regionCd":"ON","postCd":"501953542381620c42478a48d9f31895"},"descEn":"Delivered","descFr":"Livré","dnc":"103107756503640","type":"Delivered"},"eventCds":["1498","1701","1703","1488","0174","0170","2300","3000"],"addtnlDestInfo":"MARKHAM, ON","suppressSignature":true,"lagTime":false,"canadianDest":false,"shipperPostalCode":""}]
     */
    private String getTrackJsonPackage(String pin){
        String[] cmds ={"curl","--location","--request","GET",
//                "https://www.canadapost-postescanada.ca/track-reperage/rs/track/json/package?pins=9833772175809322"
                "https://www.canadapost-postescanada.ca/track-reperage/rs/track/json/package?pins="+pin
        };
        return Curl.execCurl(cmds);
    }

    /**
     curl --location --request DELETE 'https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/9833772553625322' \
     --header 'Accept: application/vnd.cpc.shipment-v8+xml' \
     --header 'Accept-language: en-CA' \
     --header 'Content-Type: application/vnd.cpc.shipment-v8+xml' \
     --header 'Authorization: Basic xxx' \
     --header 'Cookie: OWSPRD001SHIP=ship_01279_s008ptom001'
     * @return
     */
    private String voidShipment(String pin){
        String[] cmds ={"curl","-I","--location","--request","DELETE",
                "https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/"+pin,
                "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic "+k,
                "--header","Cookie: OWSPRD001SHIP=ship_01279_s008ptom001"};
        return Curl.execCurl(cmds);
    }
    private String getPriceOfShipment(String priceLink) {
        String[] cmds ={"curl","--location","--request","GET",
                priceLink,
                "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic "+k,
                "--header","Cookie: OWSPRD001SHIP=ship_01279_s008ptom001"};
        return Curl.execCurl(cmds);
    }
    private String getDetailsOfShipment(String detailsLink) {
        String[] cmds ={"curl","--location","--request","GET",
                detailsLink,
                "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic "+k,
                "--header","Cookie: OWSPRD001SHIP=ship_01279_s008ptom001"};
        return Curl.execCurl(cmds);
    }



    public static void main(String[] args) {
        CanadaPost canadaPost = new CanadaPost();
        CanadaPostTracking canadaPostTracking = new CanadaPostTracking(canadaPost.getK());
        BufferedReader br = null;
        try {
            List<Group> gs = canadaPost.getGroups();
            for(int i=0;i<gs.size();i++){
                System.out.println(i+"->"+gs.get(i).toXML());
            }
            br = new BufferedReader(new InputStreamReader(System.in));
            Group currentG = new Group();
            List<Shipment> shipments=new ArrayList<>();
            ShipmentInfo currentShipment =null;
            String echo="Group(p/g ?)";

            while (true) {
                System.out.print(echo+" Enter:>");
                String input = null;
                input = br.readLine();
                System.out.print("input:" + input);
                System.out.println("-----------------\n");
                String[] inputs = input.split(" ");
                String shipmentIndex = "";
                String command = inputs[0];
                if(inputs.length>=2){
                    shipmentIndex = inputs[1];
                }
                if ("q".equals((input))) {
                    System.out.println("Exit!");
                    System.exit(0);
                } else if ("p".equals(input)) {
                    for (int i = 0; i < gs.size(); i++) {
                        System.out.println(i + "->" + gs.get(i).toXML());
                    }
                    echo = "Group(p|g ?)";
                }
                else if(command.equals("g")){
                    try {
                        if(shipmentIndex==null || shipmentIndex.isEmpty() || shipmentIndex.isBlank()){
                            System.out.println(input);
                        }else{
                            int i = Integer.parseInt(shipmentIndex);
                            if(i>=gs.size()){
                                System.out.println( "i>=gs.size():"+i+">="+gs.size());
                            }else if(gs.size()==0){
                                System.out.println( "gs.size():"+gs.size());
                            }else{
                                currentG = gs.get(i);
                                currentG.setTagIndex(i);
                                System.out.println(currentG.getHref());
                                shipments=canadaPost.getShipmentsInGroup(currentG.getHref());
                                for(int n=0;n<shipments.size();n++){
                                    System.out.println(n+"->"+shipments.get(n).toXML());
                                }
                            }
                        }
                        echo = "Group(p|g"+currentG.tagIndex+"-"+currentG.groupId+".Shipments(sview ?)";//sview 1
                    }catch (NumberFormatException e){
                        System.out.println(input+": "+e.getMessage());
                    }
                }
                else if(command.equals("sview")){
                    if(shipmentIndex==null || shipmentIndex.isEmpty() || shipmentIndex.isBlank()){
                        System.out.println(input);
                    }else{
                        int shipmentI = Integer.parseInt(shipmentIndex);
                        if(shipmentI>=shipments.size()){
                            System.out.println( "shipmentI>=shipments.size():"+shipmentI+">="+shipments.size());
                        }else if(shipments.size()==0){
                            System.out.println( "shipments.size():"+shipments.size());
                        }else{
                            System.out.println(shipments.get(shipmentI).getHref());
                            currentShipment = canadaPost.getShipmentInfo(shipments.get(shipmentI).getHref());
                            currentShipment.tagIndex= shipmentI;
                            System.out.println(currentShipment.toXML());
                        }
                    }
                    if(currentShipment==null){
                        echo = "Group(p|g"+currentG.tagIndex+"-"+currentG.groupId+".Shipments(sview ?).shipment(strack/svoid/price/details)";//strack
                    }else{
                        echo = "Group(p|g"+currentG.tagIndex+"-"+currentG.groupId+".Shipments(sview "+currentShipment.tagIndex+").shipment(strack/svoid/price/details)";//strack
                    }
                }else if(command.equals("strack")){
                    if(currentShipment!=null && !currentShipment.trackingPin.isBlank()&& !currentShipment.trackingPin.isEmpty()){
                        System.out.println(canadaPost.getTrackJsonPackage(currentShipment.trackingPin));
                        System.out.println(canadaPostTracking.getDeliveryConfirmationCertificate(currentShipment.trackingPin));
                    }else{
                        System.out.println("null");
                    }if(currentShipment==null){
                        echo = "Group(p|g"+currentG.tagIndex+"-"+currentG.groupId+".Shipments(sview ?).shipment(strack/svoid/price/details)";//strack
                    }else {
                        echo = "Group(p|g" + currentG.tagIndex + "-" + currentG.groupId + ".Shipments(sview " + currentShipment.tagIndex + ").shipment(svoid)";//svoid
                    }
                }else if(command.equals("svoid")){
                    if(currentShipment!=null && !currentShipment.shipmentId.isBlank()&& !currentShipment.shipmentId.isEmpty()){
                        System.out.println(currentShipment.trackingPin +"--"+currentShipment.shipmentId);
                        System.out.println(
                        canadaPost.voidShipment(currentShipment.shipmentId));
                    }else{
                        System.out.println("null");
                    }
                    if(currentShipment==null){
                        echo = "Group(p|g"+currentG.tagIndex+"-"+currentG.groupId+".Shipments(sview ?).shipment(strack/svoid/price/details)";//strack
                    }else {
                        echo = "Group(p|g" + currentG.tagIndex + "-" + currentG.groupId + ".Shipments(sview " + currentShipment.tagIndex + ")";//
                    }
                }else if(command.equals("price")){
                    if(currentShipment!=null && !currentShipment.shipmentId.isBlank()&& !currentShipment.shipmentId.isEmpty()){
                        System.out.println(currentShipment.trackingPin +"--"+currentShipment.shipmentId);
                        String l = currentShipment.getPriceLink();
                        System.out.println(l);
                        System.out.println(
                                canadaPost.getPriceOfShipment(l));

                    }else{
                        System.out.println("null");
                    }
                    if(currentShipment==null){
                        echo = "Group(p|g"+currentG.tagIndex+"-"+currentG.groupId+".Shipments(sview ?).shipment(strack/svoid/price/details)";//strack
                    }else {
                        echo = "Group(p|g" + currentG.tagIndex + "-" + currentG.groupId + ".shipment(sview" +currentShipment.tagIndex + "strack/svoid/price/details)";//
                    }
                }else if(command.equals("details")){
                    if(currentShipment!=null && !currentShipment.shipmentId.isBlank()&& !currentShipment.shipmentId.isEmpty()){
                        System.out.println(currentShipment.trackingPin +"--"+currentShipment.shipmentId);
                        String l = currentShipment.getDetailsLink();
                        System.out.println(l);
                        System.out.println(
                                canadaPost.getDetailsOfShipment(l));

                    }else{
                        System.out.println("null");
                    }
                    if(currentShipment==null){
                        echo = "Group(p|g"+currentG.tagIndex+"-"+currentG.groupId+".Shipments(sview ?).shipment(strack/svoid/price/details)";//strack
                    }else {
                        echo = "Group(p|g" + currentG.tagIndex + "-" + currentG.groupId + ".shipment(sview" +  currentShipment.tagIndex + ")|strack/svoid/price/details)";//
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            System.out.println("getGroups() error!...");
            System.out.println(e.getMessage());
        }
        System.out.println("-----end-- see you--");
    }




}
