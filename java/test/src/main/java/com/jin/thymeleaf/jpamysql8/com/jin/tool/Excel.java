package com.jin.thymeleaf.jpamysql8.com.jin.tool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//https : / / blog.csdn.net/weixin_39430584/article/details/82345317
public class Excel {
    private static HSSFWorkbook  workbook2003xls; //2003的.xls
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook; // 2007的excel .xlsx
    private static XSSFCell Cell;
    private static XSSFRow Row;
    private static String path;
    public static void setExcelFile(String path,String SheetName) throws Exception{
        FileInputStream ExcelFile;
        ExcelFile = new FileInputStream(path);
        ExcelWBook=new XSSFWorkbook(ExcelFile);
        ExcelWSheet=ExcelWBook.getSheet(SheetName);

        //2003的.xls
        //HSSFWorkbook  workbook2003xls;
        workbook2003xls=new HSSFWorkbook(new FileInputStream(new File(path)));
    }
    public static String getCellData(int RowNum,int ColNum){
        Cell=ExcelWSheet.getRow(RowNum).getCell(ColNum);
        String CellData=Cell.getCellType()== CellType.STRING?Cell.getStringCellValue():"";
        return CellData;
    }
    public static void setCellData(int RowNum,int ColNum,String Result) throws Exception{
        Row=ExcelWSheet.getRow(RowNum);
        Cell=Row.getCell(ColNum);
        if(Cell==null){
            Cell=Row.createCell(ColNum);
            Cell.setCellValue(Result);
        }else{
            Cell.setCellValue(Result);

        }
        FileOutputStream fileOut = new FileOutputStream(path);
        ExcelWBook.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

}
