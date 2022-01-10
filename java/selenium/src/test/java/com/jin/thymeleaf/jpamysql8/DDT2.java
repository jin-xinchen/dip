package com.jin.thymeleaf.jpamysql8;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * http s : / / blog.csdn.net/u011541946/article/details/75269332
 */
public class DDT2 {
    @Test
    public void test1(){
        System.out.println(getClass().getName());
    }
    @Test
    public void test2() throws Exception{
        System.out.println(getClass().getName());
        throw new Exception();

    }
    @Test(dataProvider="testdata")
    public void TestLogin(String username,String password) throws InterruptedException{
//        System.setProperty("webdriver.chrome.driver",".\\Tools\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver","D:\\tools\\test\\chromedriver.exe");

//        WebDriver driver = new ChromeDriver();
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.baidu.com");
        driver.findElement(By.xpath("//*[@id='u1']/a[7]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id='TANGERAM__PSP_8__userName']")).clear();
        driver.findElement(By.xpath("//*[@id='TANGERAM__PSP_8__userName']")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id='TANGERAM__PSP_8__password']")).clear();
        driver.findElement(By.xpath("//*[@id='TANGERAM__PSP_8__password']")).sendKeys(password);

        driver.quit();
        driver.close();
    }
    @DataProvider(name="testdata")
    public Object[][] TestDataFeed() throws Exception{
//        File src = new File(".\\testFile\\TestData.xlsx");
        File src = new File("src/test/java/testFile/TestData.xlsx");
        FileInputStream fis = new FileInputStream(src);
        @SuppressWarnings("resource")
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh1 = wb.getSheetAt(0);
        int numberrow = sh1.getPhysicalNumberOfRows();
        Object [][] baidudata = new Object[numberrow][2];
        for(int i=0;i<numberrow;i++){
            baidudata[i][0]=sh1.getRow(i).getCell(0).getStringCellValue();
            baidudata[i][1]=sh1.getRow(i).getCell(1).getStringCellValue();
        }
        return baidudata;
    }
}
