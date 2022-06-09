package com.jin.tool.canadapost;

import java.util.ArrayList;
import java.util.List;

/**
 * <?xml version="1.0" encoding="UTF-8"?>
 * <shipment-info xmlns="http://www.canadapost.ca/ws/shipment-v8">
 *     <customer-request-id>DARWH02001SO060622000001</customer-request-id>
 *     <shipment-id>906161654537700282</shipment-id>
 *     <shipment-status>created</shipment-status>
 *     <tracking-pin>9833772553625322</tracking-pin>
 *     <links>
 *         <link rel="self" href="https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282" media-type="application/vnd.cpc.shipment-v8+xml"/>
 *         <link rel="details" href="https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282/details" media-type="application/vnd.cpc.shipment-v8+xml"/>
 *         <link rel="price" href="https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282/price" media-type="application/vnd.cpc.shipment-v8+xml"/>
 *         <link rel="group" href="https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment?groupId=2022-06-06" media-type="application/vnd.cpc.shipment-v8+xml"/>
 *         <link rel="label" href="https://soa-gw.canadapost.ca/rs/artifact/6392e3e393b5dd4b/10342920948/0" media-type="application/pdf" index="0"/>
 *     </links>
 * </shipment-info>
 */
public class ShipmentInfo {
    public List<Link> links=new ArrayList<Link>();
    public String customerRequestId ="";
    public String shipmentId="";
    public String shipmentStatus="";
    public String trackingPin="";
    public int tagIndex=0;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getCustomerRequestId() {
        return customerRequestId;
    }

    public void setCustomerRequestId(String customerRequestId) {
        this.customerRequestId = customerRequestId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getTrackingPin() {
        return trackingPin;
    }

    public void setTrackingPin(String trackingPin) {
        this.trackingPin = trackingPin;
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"<shipment-info xmlns=\"http://www.canadapost.ca/ws/shipment-v8\">\n");
        sb.append("<customer-request-id>"+customerRequestId+"</customer-request-id>\n");
        sb.append("<shipment-id>"+shipmentId+"</shipment-id>\n");
        sb.append("<shipment-status>"+shipmentStatus+"</shipment-status>\n");
        sb.append("<tracking-pin>"+trackingPin+"</tracking-pin>\n");
        sb.append("<links>\n");
        for(int i=0;i<links.size();i++){
            sb.append(links.get(i).toXML());
        }
//        sb.append("<link rel=\"self\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>");
//        sb.append("<link rel=\"details\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282/details\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>");
//        sb.append("<link rel=\"price\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282/price\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>");
//        sb.append("<link rel=\"group\" href=\"https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment?groupId=2022-06-06\" media-type=\"application/vnd.cpc.shipment-v8+xml\"/>");
//        sb.append("<link rel=\"label\" href=\"https://soa-gw.canadapost.ca/rs/artifact/6392e3e393b5dd4b/10342920948/0\" media-type=\"application/pdf\" index=\"0\"/>");
        sb.append("</links>\n");
        sb.append("</shipment-info>");
        return sb.toString();
    }
    public String getPriceLink(){
        String l="";
        for(int i=0;i<links.size();i++){
            if(links.get(i).rel.equals("price")){
                l=links.get(i).href;
                break;
            }
        }
        return l;
    }
    public String getDetailsLink(){
        String l="";
        for(int i=0;i<links.size();i++){
            if(links.get(i).rel.equals("details")){
                l=links.get(i).href;
                break;
            }
        }
        return l;
    }
}
