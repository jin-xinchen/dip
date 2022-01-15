package com.jin.factory;

public class BrowserSelenium implements Browser{
    public static void main(String[] args) {
       String s = System.getProperty("webdriver.firefox.bin");
        System.out.println(s);
    }

}
