package com.jin.tool.canadapost;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ParserCanadapost {
    public static List<Group> getGroupsFromXML(String strXML) throws DocumentException {
        List<Group> gs = new ArrayList<Group>();
        Document doc = null;
        if(strXML==null || strXML.isBlank() || strXML.isEmpty()){
            return gs;
        }else {
            doc = DocumentHelper.parseText(strXML);
            Element root = doc.getRootElement();
            Iterator it = root.elementIterator();
            while (it.hasNext()) {
                Element group = (Element) it.next();
//                System.out.println(group.getName() + " : " + group.getTextTrim());
                Element link = group.element("link");
                Group g = new Group();
                g.href= link.attribute("href").getValue();
                g.mediaType=link.attribute("media-type").getValue();
                g.rel=link.attribute("rel").getValue();
                g.groupId =group.element("group-id").getTextTrim();
                gs.add(g);
//                System.out.println(g.toXML());
            }
        }
        return gs;
    }

    public static List<Shipment> getShipmentsFromXML(String strXML) throws DocumentException {
        List<Shipment> ss = new ArrayList<Shipment>();
        Document doc = null;
        if(strXML==null || strXML.isBlank() || strXML.isEmpty()){
            return ss;
        }else {
            doc = DocumentHelper.parseText(strXML);
            Element root = doc.getRootElement();
            Iterator it = root.elementIterator();
            while (it.hasNext()) {
                Element link = (Element) it.next();
                Shipment shipment = new Shipment();
                shipment.href= link.attribute("href").getValue();
                shipment.mediaType=link.attribute("media-type").getValue();
                shipment.rel=link.attribute("rel").getValue();
                ss.add(shipment);
            }
        }
        return ss;
    }

    public static ShipmentInfo getShipmentInfoFromXML(String strXML) throws DocumentException {
        ShipmentInfo si = new ShipmentInfo();

        Document doc = null;
        if(strXML==null || strXML.isBlank() || strXML.isEmpty()){
            return si;
        }else {
            doc = DocumentHelper.parseText(strXML);
            Element root = doc.getRootElement();
            Iterator it = root.elementIterator();
            while (it.hasNext()) {
                Element sInfo = (Element) it.next();
                if(sInfo.getName().equals("customer-request-id")){
                    si.customerRequestId = sInfo.getTextTrim();
                }else
                if(sInfo.getName().equals("shipment-id")){
                    si.shipmentId = sInfo.getTextTrim();
                }else
                if(sInfo.getName().equals("shipment-status")){
                    si.shipmentStatus = sInfo.getTextTrim();
                }else
                if(sInfo.getName().equals("tracking-pin")){
                    si.trackingPin = sInfo.getTextTrim();
                } else
                if(sInfo.getName().equals("links")){
                    List<Link> links = getLinks(sInfo);
                    si.setLinks(links);
                }
            }
        }
        return si;
    }

    private static List<Link> getLinks(Element links1) {
        List<Link> links = new ArrayList<>();
        Iterator it = links1.elementIterator();
        while (it.hasNext()) {
            Element link = (Element) it.next();
            Link L = new Link();
            L.href= link.attribute("href").getValue();
            L.mediaType=link.attribute("media-type").getValue();
            L.rel=link.attribute("rel").getValue();
            links.add(L);
        }
        return links;
    }
}
