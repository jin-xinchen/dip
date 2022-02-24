package com.jin.tool.zipcodes;

import com.google.gson.*;
import com.jin.tool.WriterExcelUtil;
import com.jin.tool.shipping.CanadaPost;
import com.jin.tool.shipping.ProvinceTerritory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
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
public class handleExcel00Vancouver {
    static String f="2021_DELIVERY_STANDARDS_BY_ORIGIN";
    static Path pathFolderSrc = Paths.get(System.getProperty("user.dir"), "zipcodes/"+f+".xlsx");
    static Path pathFolderTarget = Paths.get(System.getProperty("user.dir"), "output/json/"+f);

    public static void main(String[] args) {

//        try {
//            write("demo");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            read2json(pathFolderSrc.toString(), pathFolderTarget.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean read2json(String src, String target) throws IOException {
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
//            for(int i=0;i<numberOfSheets;i++){
//                sheet = wb.getSheetAt(i);
//            }
//            wb.setSheetOrder(sheetname,pos);
//            wb.removeName(0);
//            wb.getActiveSheetIndex();
//            wb.setActiveSheet(0);
            List definedNames = new ArrayList();
//            definedNames = wb.getAllNames();
//            Iterator<List> definedNameIterator = definedNames.iterator();
//            while (definedNameIterator.hasNext()) {
//                Name name = (Name) definedNameIterator.next();
//                System.out.println(name.getNameName());
//                System.out.println(name.getRefersToFormula());
//                System.out.println();
//            }
//            https://www.canadapost-postescanada.ca/cpc/en/tools/delivery-standards.page?ecid=murl08002548
            Map<String,String> province_territory = ProvinceTerritory.getProviceTerritoryOfVancouverOfCanada();
            Set<String> keys =province_territory.keySet();
            int n = keys.size();
            Iterator<String> it=keys.iterator();

            sheet = wb.getSheetAt(0);
            myWriter = new FileWriter(target+"_"+sheet.getSheetName()+System.currentTimeMillis()+".json");
            RichTextString s = sheet.getRow(0).getCell(1).getRichStringCellValue();
            String[] sa = s.getString().split("\r\n");
//            myWriter.append("{");

            String[] sa1 = sa[0].split("\n");
//            myWriter.append("\"t\":\""+sa1[0]+"\"");//title
//            myWriter.append(",\"d\":\""+sa1[1]+"\"");//date
//            JsonElement jsonElement = gson.toJsonTree();
            JsonObject jE = new JsonObject();

            jE.getAsJsonObject().addProperty("t", sa1[0]);
            jE.getAsJsonObject().addProperty("d", sa1[1]);
            jE.getAsJsonObject().addProperty("from", "Vancouver");
            jE.add("services",CanadaPost.getServicesOfCanadaPost());

            while(it.hasNext()){
                String key = it.next();
                String v=province_territory.get(key);
                String[] ss = v.split(",");
                int n1 =Integer.valueOf(ss[1]);
                int n0= Integer.valueOf(ss[0]);
                int nn = n1-n0;
                JsonArray ptJA = new JsonArray();
                for(int i=1;i<=nn;i++){
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


//            Map<Integer, List<String>> data = new HashMap<>();
//            int i = 0;
//            for(Row row:sheet){
//                data.put(i,new ArrayList<String>());
//                for(Cell cell:row){
//                    switch(cell.getCellType()){
//                        case STRING:
//                            System.out.println(cell.getStringCellValue());
////                            System.out.println(cell.getRichStringCellValue().getString());
//                            break;
//                        case NUMERIC:data.get(i).add(cell.getNumericCellValue()+"");
//                            break;
//                        case FORMULA: cell.getCellFormula(); break;
//                        case _NONE:break;
//                        case BLANK: break;
//                        case BOOLEAN: cell.getBooleanCellValue();break;
//                        case ERROR:break;
//                        default:data.get(i).add(" ");
//                    }
//
////                    if(DateUtil.isCellDateFormatted(cell)){
////                        data.get(i).add(cell.getDateCellValue()+"");
////                    }
//                }
//                i++;
//            }
//            myWriter.append("}");
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



    static void write(String target) throws IOException {
        Workbook wb = new XSSFWorkbook();

        Sheet sheet=wb.createSheet("demo");
//        HSSFSheet cloneSheet =wb.cloneSheet(0);
        sheet.setColumnWidth(0,6000);

        Row header = sheet.createRow(0);

        CellStyle hs = wb.createCellStyle();
        hs.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        hs.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font=((XSSFWorkbook)wb).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);
        hs.setFont(font);

        Cell hc=header.createCell(0);
        hc.setCellValue("Name");
        hc.setCellStyle(hs);

        hc=header.createCell(1);
        hc.setCellValue("Age");
        hc.setCellStyle(hs);

        CellStyle cs01 = wb.createCellStyle();
        cs01.setWrapText(true);
        Row r1 = sheet.createRow(1);
        Cell c01=r1.createCell(0);
        c01.setCellValue("John");
        c01.setCellStyle(cs01);
        Cell c11=r1.createCell(1);
        c01.setCellValue(20);
        c01.setCellStyle(cs01);

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0,path.length()-1)+"temp.xlsx";
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(fileLocation);
            wb.write(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                os.close();
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }


    }
}


//https://javaee.github.io/jsonp/
//    public static void main0(String[] args) {
//        // Parse back
//        final String result = "{\"name\":\"Falco\",\"age\":3,\"bitable\":false}";
//        final JsonParser parser = Json.createParser(new StringReader(result));
//        String key = null;
//        String value = null;
//        while (parser.hasNext()) {
//            final Event event = parser.next();
//            switch (event) {
//                case KEY_NAME:
//                    key = parser.getString();
//                    System.out.println(key);
//                    break;
//                case VALUE_STRING:
//                    value = parser.getString();
//                    System.out.println(value);
//                    break;
//            }
//        }
//        parser.close();
//    }
//JsonObject json = Json.createObjectBuilder()
//        .add("name", "Falco")
//        .add("age", BigDecimal.valueOf(3))
//        .add("biteable", Boolean.FALSE).build();
//    String result = json.toString();
//
//        System.out.println(result);