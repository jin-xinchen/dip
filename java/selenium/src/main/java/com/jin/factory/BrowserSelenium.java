package com.jin.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Path;

public class BrowserSelenium implements Browser {
    public static void main(String[] args) {
       String s = System.getProperty("webdriver.firefox.bin");
        System.out.println(s);
    }

    @Override
    public WebDriver openWebsite(String url, String type) {
        return null;
    }

    @Override
    public WebDriver runWebsiteBackend(String url, String type) {
        return null;
    }

    @Override
    public void screenshot(Path targetFile) throws IOException, InterruptedException {

    }

    @Override
    public void screenshot(String targetFile) throws IOException, InterruptedException {

    }

    @Override
    public WebElement locator(String xpath) {
        return null;
    }

    @Override
    public void inputText(String xpath, String value) {

    }

    @Override
    public void screenshotLong(String image) throws IOException {

    }

    @Override
    public void logScreenshotLong() throws IOException {

    }

    @Override
    public void logScreenshot() throws IOException, InterruptedException {

    }

    @Override
    public boolean getDisplay(String xpath) {
        return false;
    }

    @Override
    public void clickElement(String xpath) throws Exception {

    }
}
