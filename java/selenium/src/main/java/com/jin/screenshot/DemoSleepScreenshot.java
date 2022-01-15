package com.jin.screenshot;


import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
From: h ttp s : / / www.codenong.com/20009211/
 */
public class DemoSleepScreenshot {
    public static void main(String[] args) {
        DemoSleepScreenshot();
//        DemoWait();
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
