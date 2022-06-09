package com.jin.tool.canadapost;

public class Link {
//    <?xml version="1.0" encoding="UTF-8"?>
//<shipments xmlns="http://www.canadapost.ca/ws/shipment-v8">
//    <link rel="shipment" href="https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/906161654537700282" media-type="application/vnd.cpc.shipment-v8+xml"/>
//    <link rel="shipment" href="https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/236271654537760473" media-type="application/vnd.cpc.shipment-v8+xml"/>
//</shipments>
    String rel="shipment";
    String href="";
    String mediaType="application/vnd.cpc.shipment-v8+xml";

    public String toXML(){
        return "<link rel=\"group\" href=\""+href+"\" media-type=\""+mediaType+"\"/>\n";
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
