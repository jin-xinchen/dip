package com.jin.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Path;

public interface Browser {
    WebDriver openWebsite(String url, String type);
    WebDriver runWebsiteBackend(String url, String type);

    void screenshot(Path targetFile) throws IOException, InterruptedException;
    void screenshot(String targetFile) throws IOException, InterruptedException;

    WebElement locator(String xpath);

    void inputText(String xpath, String value);

    void screenshotLong(String image) throws IOException;

    void logScreenshotLong() throws IOException;

    void logScreenshot() throws IOException, InterruptedException;
}
