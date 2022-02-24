package com.jin.tool.shipping;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jin.tool.WriterExcelUtil;
import org.apache.poi.ss.usermodel.Row;

public class CanadaPost {
    public static JsonArray getServicesOfCanadaPost() {

        JsonArray ptJA = new JsonArray();
        JsonObject Pr=new JsonObject();
        Pr.addProperty("name","Priority" );
        Pr.addProperty("id",1 );

        JsonObject Xp=new JsonObject();
        Xp.addProperty("id",2);
        Xp.addProperty("name","Xpresspost");

        JsonObject Ex=new JsonObject();
        Ex.addProperty("id",3);
        Ex.addProperty("name","Expedited Parcel™ or flat rate box");
        JsonObject Re = new JsonObject();
        Re.addProperty("id",4);
        Re.addProperty("name","Regular Parcel™");

        ptJA.add((JsonElement) Pr);
        ptJA.add((JsonElement) Xp);
        ptJA.add((JsonElement) Ex );
        ptJA.add((JsonElement) Re );

        return ptJA;
    }
    public static JsonObject getServicesOfCanadaPost(Row row) {

        JsonObject Pr=new JsonObject();
//        Priority
        Pr.addProperty("1", WriterExcelUtil.getValue(row.getCell(1)));//sheet.getRow(4+1).getCell(2).getNumericCellValue()
//        Xpresspost
        Pr.addProperty("2", WriterExcelUtil.getValue(row.getCell(2)));
//        Expedited
        Pr.addProperty("3", WriterExcelUtil.getValue(row.getCell(3)));
//        Regular
        Pr.addProperty("4", WriterExcelUtil.getValue(row.getCell(4)));
        return Pr;

        //Priority	Xpresspost	Expedited Parcel	Regular Parcel
//        JsonObject Priority=new JsonObject();
//        Priority.addProperty("Priority", WriterExcelUtil.getValue(sheet.getRow(3).getCell(1)));//sheet.getRow(4+1).getCell(2).getNumericCellValue()
//        jsonStr= gson.toJson(Priority);
//        JsonObject Xpresspost=new JsonObject();
//        Xpresspost.addProperty("Xpresspost", WriterExcelUtil.getValue(sheet.getRow(3).getCell(2)));
//        JsonObject Expedited=new JsonObject();
//        Expedited.addProperty("Expedited", WriterExcelUtil.getValue(sheet.getRow(3).getCell(3)));
//        JsonObject Regular = new JsonObject();
//        Regular.addProperty("Regular", WriterExcelUtil.getValue(sheet.getRow(4-1).getCell(4)));
//        JsonArray jA = new JsonArray();
//        jA.add((JsonElement) Priority);
//        jA.add((JsonElement) Xpresspost);
//        jA.add((JsonElement) Expedited);
//        jA.add((JsonElement) Regular);
    }
}
