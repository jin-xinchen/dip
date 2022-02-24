package com.jin.tool.zipcodes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jin.tool.WriterExcelUtil;
import com.jin.tool.shipping.CanadaPost;

import com.jin.tool.shipping.ProvinceTerritory;
import com.jin.tool.shipping.ServicesOfCanadaPost;
import com.jin.tool.shipping.ServicesRemoteOfCanadaPost;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.FeatureDescriptor;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://www.canadapost-postescanada.ca/cpc/en/support/articles/addressing-guidelines/postal-codes.page
 * https://www.ic.gc.ca/eic/site/bsf-osb.nsf/eng/br03396.html
 *
 * https://www.canadapost-postescanada.ca/delivery-standards/domestic/v1/validateFsaPc?code=X0E
 * Postal code must be in A1A 1A1 format.
 * https://www.canadapost-postescanada.ca/delivery-standards/domestic/v1/postalCodeStandards?from=X0e&to=Y0A
 *{"fromProvinceName":"Northwest Territories","toProvinceName":"Yukon","services":[{"id":1,"name":"Priority™"},{"id":2,"name":"Xpresspost™"},{"id":3,"name":"Expedited Parcel™ or flat rate box"},{"id":4,"name":"Regular Parcel™"}],"rows":[{"name":"Y0A","rows":[{"to":["Y0A"],"ds":[8,8,14,14]}]}]}
 *
 *
 * https://www.canadapost-postescanada.ca/cpc/en/tools/delivery-standards.page?ecid=murl08002548
 * 1 Delivery standards are based on normal delivery conditions and are subject to change without notice. The on-time delivery guarantee may be modified during peak period (see canadapost.ca/notice) or suspended due to causes beyond Canada Post’s reasonable control, including, but not limited to, acts of God, epidemics, labour disruptions, equipment failures or unanticipated surges in volume. Find the delivery standard from your postal code to a Canadian destination at canadapost.ca/deliverytool.
 * jackson gson fastjson www.json.com org.json.JSONObject
 */
public class handleExcel01Cagary {
    static String f="2021_DELIVERY_STANDARDS_BY_ORIGIN";
    static Path pathFolderSrc = Paths.get(System.getProperty("user.dir"), "zipcodes/"+f+".xlsx");
    static Path pathFolderTarget = Paths.get(System.getProperty("user.dir"), "output/json/"+f);

    public static void main(String[] args) {
        try {
            for(int i =2;i<13;i++){
                build2FunctionContent(pathFolderSrc.toString(), pathFolderTarget.toString(),i,3880);
//                System.out.println(countFSA(pathFolderSrc.toString(), pathFolderTarget.toString(),i));
            }

//            build2FunctionContent(pathFolderSrc.toString(), pathFolderTarget.toString(),1,388);

//            read2json(pathFolderSrc.toString(), pathFolderTarget.toString(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean read2json(String src, String target,int indexOfSheet) throws IOException {
        boolean b = true;
        FileWriter myWriter;
        Gson gson = new Gson();
//        canadapost.ca/deliverytool
        try {//https://blog.csdn.net/kerongao/article/details/110087936
            //https://blog.csdn.net/J080624/article/details/73551070
            FileInputStream file = new FileInputStream(new File(src));
            Workbook wb = new XSSFWorkbook(file);
//            Workbook wb = WorkbookFactory.create(file);
            System.out.println(wb.getSpreadsheetVersion());
            Sheet sheet;
            int numberOfSheets = wb.getNumberOfSheets();
            Map<String,String> province_territory = ProvinceTerritory.getProviceTerritoryOfCalgaryOfCanada();
            Set<String> keys =province_territory.keySet();
            int n = keys.size();
            Iterator<String> it=keys.iterator();

            sheet = wb.getSheetAt(indexOfSheet);
            myWriter = new FileWriter(target+"_"+sheet.getSheetName()+System.currentTimeMillis()+".json");
            RichTextString s = sheet.getRow(0).getCell(1).getRichStringCellValue();
            String[] sa = s.getString().split("\r\n");

            String[] sa1 = sa[0].split("\n");

            JsonObject jE = new JsonObject();

            jE.getAsJsonObject().addProperty("t", sa1[0]);
            jE.getAsJsonObject().addProperty("d", sa1[1]);
            jE.getAsJsonObject().addProperty("from", "Vancouver");
            jE.add("services",CanadaPost.getServicesOfCanadaPost());

            while(it.hasNext()){
                String key = it.next();
                String v=province_territory.get(key);
                String[] ss = v.split(",");
                int n1 =Integer.valueOf(ss[1]);//387
                int n0= Integer.valueOf(ss[0])+1;//384
                int nn = n1-n0;
                JsonArray ptJA = new JsonArray();
                for(int i=0;i<=nn;i++){
                    JsonObject Re = new JsonObject();
                    String vzips =WriterExcelUtil.getValue(sheet.getRow(i+n0).getCell(0));
                    JsonObject days = CanadaPost.getServicesOfCanadaPost(sheet.getRow(i+n0));
                    Re.add(vzips, days);
                    ptJA.add(Re) ;
                }
                jE.add(key,ptJA);
            }

            String jsonStr = "";//gson.toJson(jE);
            jsonStr=gson.toJson(jE);
            myWriter.append(jsonStr);
            myWriter.close();
        } catch (FileNotFoundException e) {
            b = false;
            e.printStackTrace();

        } catch (IOException e) {
            b=false;
            e.printStackTrace();
        }finally {

        }
        return b;
    }
    static boolean build2FunctionContent(String src, String target,int indexOfSheet,int numberOfrow) throws IOException {
        boolean b = true;
        FileWriter myWriter;
        Gson gson = new Gson();
//        canadapost.ca/deliverytool
//        https://www.canadapost-postescanada.ca/delivery-standards/domestic/v1/postalCodeStandards?from=Y0A&to=X0e
        try {//https://blog.csdn.net/kerongao/article/details/110087936
            //https://blog.csdn.net/J080624/article/details/73551070
            FileInputStream file = new FileInputStream(new File(src));
            Workbook wb = new XSSFWorkbook(file);
//            Workbook wb = WorkbookFactory.create(file);
            System.out.println(wb.getSpreadsheetVersion());
            Sheet sheet;
            sheet = wb.getSheetAt(indexOfSheet);
            myWriter = new FileWriter(target+"_"+sheet.getSheetName()+System.currentTimeMillis()+".txt");
            Map<String,String> province_territory = ProvinceTerritory.getProviceTerritoryOfVancouverOfCanada();
            Set<String> keys =province_territory.keySet();

            myWriter.append("public static Map<String, String> getProviceTerritoryOf"+sheet.getSheetName()+"OfCanada() {\n");
            myWriter.append("Map<String,String> province_territory = new HashMap<String,String>();\n");
            int in =0;
            do {
                Row row = sheet.getRow(in);
                if (row == null && sheet.getRow(in + 1) == null && sheet.getRow(in + 2) == null) {
                    break;
                }
                numberOfrow=in;
                in++;
            }while(true);
            String str = "";
            int end= numberOfrow-1; int last=end;
            for(int i=end;i>2;i--){

                String  s = sheet.getRow(i).getCell(0).getStringCellValue();
                Iterator<String> it=keys.iterator();
                while(it.hasNext()){
                    String k = it.next();
                    int ii = s.indexOf(k);
                    if(ii>-1){
                        myWriter.append("province_territory.put(\""+k+"\","+i+"+\",\"+"+last+");\r\n");
                        last =i;
                    }
                }

            }
            myWriter.append(str);
            myWriter.append("return province_territory;\n");
            myWriter.append("}");
            myWriter.close();
        } catch (FileNotFoundException e) {
            b = false;
            e.printStackTrace();

        } catch (IOException e) {
            b=false;
            e.printStackTrace();
        }finally {

        }
        return b;
    }

//1670 FSAs
    static int countFSA(String src,String target,int indexOfSheet) throws IOException {
        int n = 0;
        FileWriter myWriter;
        Gson gson = new Gson();
//        canadapost.ca/deliverytool
//        https://www.canadapost-postescanada.ca/delivery-standards/domestic/v1/postalCodeStandards?from=Y0A&to=X0e
        try {
            FileInputStream file = new FileInputStream(new File(src));
            Workbook wb = new XSSFWorkbook(file);
//            Workbook wb = WorkbookFactory.create(file);
//            System.out.println(wb.getSpreadsheetVersion());
            Sheet sheet;
            sheet = wb.getSheetAt(indexOfSheet);
            System.out.print(sheet.getSheetName()+" ");
            myWriter = new FileWriter(target+"_FSA_"+sheet.getSheetName()+System.currentTimeMillis()+".txt");
            String str = "";
            boolean bb=true;int i =1;
            Set<String> keys = new HashSet<>();
            do {
                Row row = sheet.getRow(i);
                if(row==null && sheet.getRow(i+1) ==null && sheet.getRow(i+2)==null){
                    break;
                }
                Cell cell = sheet.getRow(i).getCell(0);
                String  s = cell.getStringCellValue();
//                System.out.println(s);
                Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z]");
                Matcher m = p.matcher(s);
                while(m.find()){
                    String k = m.group();
//                    System.out.println(k);
                    keys.add(k);
                }
                i++;
            }while (bb);
            n = keys.size();
            Gson gson1 = new Gson();
            String joS = gson.toJson(keys);
            myWriter.append(joS);
            myWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return n;
    }
    /**
     * {"Calgary":{"E2V":{"1":"4","2":"4","3":"9","4":"11"}}}
     * @param indexOfSheet
     */
    static void FSA_services_province_file(String src,String target,int indexOfSheet){
        FileWriter myWriter;
        Gson gson = new Gson();
        try {
            FileInputStream file = new FileInputStream(new File(src));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet;
            sheet = wb.getSheetAt(indexOfSheet);
            System.out.print(sheet.getSheetName()+" ");
            myWriter = new FileWriter(target+"_services_"+sheet.getSheetName()+".txt");
            String str = "";
            boolean bb=true;int i =1;
            Map<String,ServicesOfCanadaPost> FSAs = new HashMap<String,ServicesOfCanadaPost>();
            do {
                Row row = sheet.getRow(i);
                if(row==null && sheet.getRow(i+1) ==null && sheet.getRow(i+2)==null){
                    break;
                }
                Cell cell = row.getCell(0);
                String  s = cell.getStringCellValue();
                Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z]");
                Matcher m = p.matcher(s);
                ServicesOfCanadaPost socp = new ServicesOfCanadaPost();
                if(row.getCell(1).getCellType()==CellType.NUMERIC){
                    socp.setS1(WriterExcelUtil.getValue(row.getCell(1)));
                }
                if(row.getCell(2).getCellType()==CellType.NUMERIC){
                    socp.setS2(WriterExcelUtil.getValue(row.getCell(2)));
                }
                if(row.getCell(3).getCellType()==CellType.NUMERIC){
                    socp.setS3(WriterExcelUtil.getValue(row.getCell(3)));
                }
                if(row.getCell(4).getCellType()==CellType.NUMERIC){
                    socp.setS4(WriterExcelUtil.getValue(row.getCell(4)));
                }
                while(m.find()){
                    String k = m.group();
                    FSAs.put(k,socp);
                }
                i++;
            }while (bb);
//            n = FSAs.size();
            Gson gson1 = new Gson();
            String joS = gson.toJson(FSAs);
            myWriter.append("{\""+sheet.getSheetName()+"\":");
            myWriter.append(joS);
            myWriter.append("}");
            myWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
    /**
     * number index of Sheet
     * @param indexOfSheet
     * @return String {"Calgary":{"E2V":{"1":"4","2":"4","3":"9","4":"11"}}}
     */
    static String FSA_services(String src,int indexOfSheet){
        StringBuilder myString = new StringBuilder();
        Gson gson = new Gson();
        try {
            FileInputStream file = new FileInputStream(new File(src));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet;
            sheet = wb.getSheetAt(indexOfSheet);
            System.out.print(sheet.getSheetName()+" ");
            boolean bb=true;int i =1;
            Map<String,ServicesOfCanadaPost> FSAs = new HashMap<String,ServicesOfCanadaPost>();
            do {
                Row row = sheet.getRow(i);
                if(row==null && sheet.getRow(i+1) ==null && sheet.getRow(i+2)==null){
                    break;
                }
                Cell cell = row.getCell(0);
                String  s = cell.getStringCellValue();
                Pattern p = Pattern.compile("[ABCEGHJKLMNPRSTVXY][\\d][A-Z]");
                Matcher m = p.matcher(s);
                ServicesOfCanadaPost socp = new ServicesOfCanadaPost();
                if(row.getCell(1).getCellType()==CellType.NUMERIC){
                    socp.setS1(WriterExcelUtil.getValue(row.getCell(1)));
                }
                if(row.getCell(2).getCellType()==CellType.NUMERIC){
                    socp.setS2(WriterExcelUtil.getValue(row.getCell(2)));
                }
                if(row.getCell(3).getCellType()==CellType.NUMERIC){
                    socp.setS3(WriterExcelUtil.getValue(row.getCell(3)));
                }
                if(row.getCell(4).getCellType()==CellType.NUMERIC){
                    socp.setS4(WriterExcelUtil.getValue(row.getCell(4)));
                }
                while(m.find()){
                    String k = m.group();
                    FSAs.put(k,socp);
                }
                i++;
            }while (bb);
            String joS = gson.toJson(FSAs);
//            myString.append("}");
            myString.append("\""+sheet.getSheetName()+"\":");
            myString.append(joS);
//            myString.append("}");

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return myString.toString();
    }

    /**
     * {
     * "Calgary":{"E2V":{"s1":"4","s2":"4","s3":"9","s4":"11"},...},
     * "Vancouver":{"K4B":{"s1":"3","s2":"4","s3":"7","s4":"9"},...}
     * }
     * @param src String
     * @param target String
     */
    static void FSA_services_from_cities(String src,String target){
        FileWriter myWriter;
        Gson gson = new Gson();
        try {
            myWriter = new FileWriter(target+"_all_province.txt");
            myWriter.append("{");
            int n=13;
            for(int i =0;i<n;i++){
                String province = FSA_services(src,i);
                if(i==(n-1)){
                    myWriter.append(province);
                }else {
                    myWriter.append(province+",");
                }

            }
            myWriter.append("}");
            myWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
    /**
     * @return  {"Northwest Territories":["FSA","FSA"]}
     */
    static String FSA_ProvinceTerritory(String src){
        Gson gson = new Gson();
        try {
            Map<String,Set<String>> provinces = new HashMap<>();
            Workbook wb = new XSSFWorkbook(src);
            int n=wb.getNumberOfSheets();
            for(int i=0;i<n;i++){
                Sheet sheet = wb.getSheetAt(i);
                Set<String> set =ProvinceTerritory.getProvinceTerritoryOfVancouverOfCanada().keySet();
                Iterator<String> it=set.iterator();
                while(it.hasNext()){
                    String k = it.next();
//                    k ="Yukon Territory";
                    Set<String> FSAs = getFSAsOfProvince(sheet,k);
                    provinces.put(k,FSAs);
                }
            }
            return gson.toJson(provinces);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * create fsa_ProvinceTerritory.txt
     * {"Northwest Territories":["X1A","X0E","X0G"],"New Brunswick":["E8B","E8A",...]}
     * @param src
     * @param target
     * @throws IOException
     */
    static void FSA_ProvinceTerritory2file(String src,String target) throws IOException {
        FileWriter f = new FileWriter(target);
        f.append(FSA_ProvinceTerritory(src));
        f.close();
    }
    private static Set<String> getFSAsOfProvince(Sheet sheet,String provinceName) {
        int ii=0;
        Set<String> fsas = new HashSet<>();
        do{
            Row row=sheet.getRow(ii);
            if(row==null && sheet.getRow(ii+1)==null&&sheet.getRow(ii+2)==null){
                break;
            }
            Cell cell = row.getCell(0);
            String v =WriterExcelUtil.getValue(cell);
            Pattern pa=Pattern.compile("[A-Z]\\d[A-Z]");
            if(v.indexOf(provinceName)>-1){
                int iii=ii; boolean bProvince=true;
                do{
                    Row row2=sheet.getRow(iii+1);
                    Cell cell2 =row2.getCell(0);
                    String v2 =WriterExcelUtil.getValue(cell2);
                    Matcher ma = pa.matcher(v2);
                    bProvince=false;
                    while(ma.find()){
                        String k = ma.group();
                        fsas.add(k);
                        bProvince=true;
                    }
                    iii++;
                }while(bProvince);
                System.out.println(iii-ii);
                break;
            }
            ii++;
        }while(true);
        return fsas;
    }
    /**
     * A0G Remote - A0G0B3, A0G0B8, A0G0C9, A0G0E8, A0G0H5, A0G0J5, A0G0J8, A0G1R0, A0G1W0, A0G2B0, A0G2W0, A0G2X0, A0G3V0, A0G3Z0, A0G4A0, A0G4B0, A0G4H0
     * Y0B Remote - Y0B0B1, Y0B1N0 N/A	8	14	14
     * {
     * "Vancouver":{
     * "Y0B":{"s":{"s1":"N/A","s2":"8","s3":"14","s4":"14"},"Remote":["Y0B0B1", "Y0B1N0"]},
     * }
     * }
     */
    public String getJsonServicesRemoteOfCanadaPost(String src){
        try {
            Workbook wb = new XSSFWorkbook(src);
            int n = wb.getNumberOfSheets();
            Map<String, Map<String,ServicesRemoteOfCanadaPost>> remotes = new HashMap<String, Map<String,ServicesRemoteOfCanadaPost>>();
            for(int i=0;i<n;i++){
                Sheet sheet = wb.getSheetAt(i);
                Map<String, ServicesRemoteOfCanadaPost> map2 = getRemotesOfCity(sheet);//new HashMap<String, Set<ServicesRemoteOfCanadaPost>>();
                remotes.put(sheet.getSheetName(),map2);
            }
            Gson gson = new Gson();
            return gson.toJson(remotes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Map<String, ServicesRemoteOfCanadaPost> getRemotesOfCity(Sheet sheet) {
        Map<String, ServicesRemoteOfCanadaPost> map = new HashMap<>();
        int i=0;
        do {
            Row row = sheet.getRow(i);
            if (row == null && sheet.getRow(i + 1) == null && sheet.getRow(i + 2) == null) {
                break;
            }
            Cell cell = row.getCell(0);
            String v2 = WriterExcelUtil.getValue(cell);
            //Y0B Remote - Y0B0B1, Y0B1N0
            if(v2.indexOf("Remote")>-1){
                ServicesRemoteOfCanadaPost sRem = new ServicesRemoteOfCanadaPost();
                String v1 = v2.replaceAll("Remote","");
                String[] remotes=v1.split("-");
                ServicesOfCanadaPost soc = new ServicesOfCanadaPost(
                        WriterExcelUtil.getValue(row.getCell(1)),
                        WriterExcelUtil.getValue(row.getCell(2)),
                        WriterExcelUtil.getValue(row.getCell(3)),
                        WriterExcelUtil.getValue(row.getCell(4))
                        );

                sRem.setS(soc);
                sRem.setRemote(remotes[1].replaceAll(" ","").split(","));
                map.put(remotes[0].trim(),sRem);
            }
            i++;
        }while(true);
        return map;
    }


    /**
     * {"t":"DELIVERY STANDARD IN BUSINESS DAYS","d":"(as of September 20th 2021 - Subject to change without notice1 )",
     * "from":"Vancouver",
     * "services":[{"name":"Priority","id":1},{"id":2,"name":"Xpresspost"},
     * {"id":3,"name":"Expedited Parcel™ or flat rate box"},
     * {"id":4,"name":"Regular Parcel™"}],
     * {"Northwest Territories":["FSA","FSA"]}
     */
}
