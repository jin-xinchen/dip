package com.jin.tool.curl;

import com.jin.tool.canadapost.Group;
import com.jin.tool.canadapost.ParserCanadapost;
import org.dom4j.DocumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CanadaPost {
    String k="w4sZ-NjM5MmUzZTM5M2I1ZGQ0Yjo3MGRiNjZkNTYyY2UwZDZlN2MyzjNh";
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
    public String getShipment(String url){
        String[] cmds ={"curl","--location","--request","GET","https://soa-gw.canadapost.ca/rs/0009833772/0009833772/shipment/318641652971248020",
                "--header","Accept: application/vnd.cpc.shipment-v8+xml",
                "--header","Accept-language: en-CA",
                "--header","Content-Type: application/vnd.cpc.shipment-v8+xml",
                "--header","Authorization: Basic "+k,
                "--header","Cookie: OWSPRD001SHIP=ship_02660_s013ptom001"};
        cmds[4]=url;
        return Curl.execCurl(cmds);

    }

    public static void main(String[] args) {
        CanadaPost canadaPost = new CanadaPost();
        BufferedReader br = null;
        try {
            List<Group> gs = canadaPost.getGroups();
            for(int i=0;i<gs.size();i++){
                System.out.println(i+"->"+gs.get(i).toXML());
            }
            br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            System.out.print("Enter:");
            String input = null;
                input = br.readLine();
            System.out.println("input:"+input);
            System.out.println("-----------------\n");
            if("q".equals((input))){
                System.out.println("Exit!");
                System.exit(0);
            }else if("p".equals(input)){
                for(int i=0;i<gs.size();i++){
                    System.out.println(i+"->"+gs.get(i).toXML());
                }
            }else {
                int indexGs = Integer.parseInt(input);
                Group p = gs.get(indexGs);
                String shipment = canadaPost.getShipment(p.getHref());
                System.out.println(shipment);
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.println("-----end-- see you--");
    }
}
