package com.jin.tool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.apache.poi.common.usermodel.Hyperlink;
//import org.apache.poi.hslf.examples.CreateHyperlink;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

public class AddPhotoUrl {
//From： htt p s : / / developer.aliyun.com/article/320022
     public static void main(String[] args) {
         XSSFWorkbook workbook = new XSSFWorkbook();
         try {
             FileOutputStream out = new FileOutputStream("AddPhotoUrl.xlsx");
             XSSFSheet sheet = workbook.createSheet("1");
             XSSFCellStyle style = workbook.createCellStyle();
             XSSFFont font = workbook.createFont();
             font.setUnderline(XSSFFont.U_DOUBLE);
             font.setColor(IndexedColors.RED.getIndex());
             style.setFont(font);

             /**
              * cell中实现URL超链接
              */
             sheet.setColumnWidth(2, 4000);
             Row row = sheet.createRow(2);
             Cell cell = row.createCell(2);
             cell.setCellValue("Angel挤一挤的博客");
             cell.setCellStyle(style);


             CreationHelper createHelper = workbook.getCreationHelper();
             //实现超链接的类   参数有4.5种，有URL，FILE等不同的类型
//             XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
             XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
             link.setAddress("http://www.cnblogs.com/sxdcgaq8080/");
             cell.setHyperlink(link);


             /**
              * cell实现File超链接
              */
             XSSFCellStyle style2 = workbook.createCellStyle();
             row = sheet.createRow(3);
             cell = row.createCell(2);
             cell.setCellValue("新建文件夹.txt");
             XSSFFont font2 = workbook.createFont();
//             font2.setColor(HSSFColor.YELLOW.index);
//             font2.setColor(XSSFFont.COLOR_RED);
             font2.setColor(IndexedColors.YELLOW.index);
             style2.setFont(font2);
             cell.setCellStyle(style2);
//             link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_FILE);
             link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.FILE);
             link.setAddress("f:/新建文本.txt");//路径不对，可能会出问题
             cell.setHyperlink(link);

             /**
              * cell实现邮箱超链接   注意邮箱的这个address的写法
              */
             XSSFCellStyle style3 = workbook.createCellStyle();
             row = sheet.createRow(4);
             cell = row.createCell(2);
             cell.setCellValue("Angel的邮箱");
             XSSFFont font3 = workbook.createFont();
//             font3.setColor(HSSFColor.VIOLET.index);
             font3.setColor(IndexedColors.VIOLET.index);
             style3.setFont(font3);
//             link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_EMAIL);
             link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.EMAIL);
             link.setAddress("mailto:18292813417@163.com?"+"subject=Hyperlink");
             cell.setHyperlink(link);

             /**
              * cell实现 插入图片
              */
             row = sheet.createRow(5);
             cell = row.createCell(5);
             row.setHeight((short) 1000);
             //画图的顶级管理器
             XSSFDrawing patriarch = sheet.createDrawingPatriarch();
             //为图片管理器配置参数
             //参数1  第一个单元格中的x轴坐标
             //参数2 第一个单元格中的y轴坐标
             //参数3 第二个单元格中的x轴坐标
             //参数4 第二个单元格中的y轴坐标
             //参数5
             //参数6
             //参数7
             //参数8
             XSSFClientAnchor anchor = new XSSFClientAnchor(100, 100, 255, 255, 13, 9, 14, 16);
             anchor.setAnchorType(AnchorType.DONT_MOVE_DO_RESIZE);

             ByteArrayOutputStream byteOutPut = new ByteArrayOutputStream();
             //读取到图片信息
             BufferedImage  bufferImage =ImageIO.read(new File("F:/酷我音乐.png"));
             //将图片写入到ByteArrayOutputStream中
             ImageIO.write(bufferImage, "png", byteOutPut);
             //参数1 代表图片的位置信息               参数2 代表图片来源
             patriarch.createPicture(anchor, workbook.addPicture(byteOutPut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));

             /**
              * 设置打印区域
              */
             //设置哪一个sheet中的第几行到第几行  第几列到第几列
             workbook.setPrintArea(0, 1, 9, 1, 10);
             //设置纸张大小
             sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
             //设置是否显示网格线
             sheet.setDisplayGridlines(true);
             //设置是否打印网格线
             sheet.setPrintGridlines(true);

             workbook.write(out);
             out.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
