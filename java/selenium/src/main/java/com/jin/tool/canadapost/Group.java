package com.jin.tool.canadapost;

public class Group extends Link{
//    <link rel="group" href="https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment?groupId=2022-05-27" media-type="application/vnd.cpc.shipment-v8+xml"/>
//        <group-id>2022-05-27</group-id>
    public String groupId="";
    public String toXML(){
        return "<link rel=\"group\" href=\""+href+"\" media-type=\""+mediaType+"\"/>\n" +
                "<group-id>"+groupId+"</group-id>";
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
