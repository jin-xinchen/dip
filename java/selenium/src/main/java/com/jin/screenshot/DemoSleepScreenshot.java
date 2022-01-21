package com.jin.screenshot;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
From: h ttp s : / / www.codenong.com/20009211/
 */
public class DemoSleepScreenshot {
    public static void main(String[] args) throws IOException, InterruptedException {
//        DemoSleepScreenshot();
//        DemoWait();
        test01();
    }
    private static void test01() throws InterruptedException, IOException {
        //http s : // github.com/rpuch/test-selenium-headless-chrome/blob/master/src/main/java/com/rpuch/test/headless/Main.java
        RemoteWebDriver driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
        try {
            driver.navigate().to("https://example.com/");
            File file = driver.getScreenshotAs(OutputType.FILE);
            Files.copy(file.toPath(), Paths.get("target/screenshot.png"), StandardCopyOption.REPLACE_EXISTING);
//            Files.copy(file.toPath(), Paths.get("screenshot/screenshot.png"), StandardCopyOption.REPLACE_EXISTING);

            // adding a delay to visually check whether there is a browser window
            Thread.sleep(1000);
        } finally {
            driver.close();
        }
    }
    private static void DemoSleepScreenshot(){
        FirefoxOptions opt = new FirefoxOptions();
        opt.addArguments("disable-infobars");
        opt.setHeadless(true);  // do not show on the browser, let it run in back end.
        WebDriver driver = new FirefoxDriver(opt);
        driver.get("http://www.site.com");
        //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        try {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("/home/Desktop/test/image.png"));//disk root
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.close();
    }
    //https://github.com/seleniumhq/selenium
    private static void DemoWait(){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.selenium.dev/");
        //==1==
        // wait way 1
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
//           Thread.sleep(4000);
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ID")));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"main_navbar\"]/div/span/input")));
        element.sendKeys("test");
        //==2==
        // wait way 2
//        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return d.getTitle().toLowerCase().endsWith("darwynn");
//            }
//        });
    }
    public void pause(Integer milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
