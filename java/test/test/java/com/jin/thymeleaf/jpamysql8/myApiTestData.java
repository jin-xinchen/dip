package com.jin.thymeleaf.jpamysql8;

import com.jin.thymeleaf.jpamysql8.com.jin.tool.MyExcel;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class myApiTestData {
    MyExcel myExcel = new MyExcel();
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws IOException {
        return myExcel.readExcel("src/test/java/testFile/TestData.xlsx","login");
    }
}
