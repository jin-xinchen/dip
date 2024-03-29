package com.jin.po.darwynn;

import com.jin.factory.Browser;
import com.jin.factory.SeleniumBrowser;
//import org.jdom2.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Homepage {
    private Browser browser;
    private String url = "https://www.darwynn.com";
    private String inputSearchXpath = "//*[@id=\"app\"]/div[2]/div[2]/div/form/input[1]";

    public void setDriver(int type) {
        if(type==1){
            driver = browser.openWebsite(url,"c");
        }else{
            driver = browser.runWebsiteBackend(url,"c");
        }
    }

    private WebDriver driver = null;
    private String title = "darwynn";
    public void setTitle(String title) {
        this.title = title;
    }

    public Homepage(){
        browser = new SeleniumBrowser();
    }

    public void search(String key) throws Exception {
        if(driver==null) {
            throw new IOException("In search(), driver is null.");
        }
        long start = System.currentTimeMillis();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                long end = System.currentTimeMillis();
                long n = end - start;
//                System.out.println("time---->----:"+n);
                return d.getTitle().toLowerCase().endsWith(title);
            }
        });
        browser.inputText(inputSearchXpath,key);
        browser.clickElement("//*[@id=\"app\"]/div[5]/div[2]/img");
        browser.clickElement("//*[@id=\"app\"]/div[2]/div[2]/div/form/input[2]");
//        System.out.println("CurrentUrl: "+driver.getCurrentUrl());
        driver.manage().window().maximize();
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    List<WebElement> ListE = null;
//                ListE = d.findElements(By.xpath("//*[@id=\"app\"]/div[5]/div[3]/ul/li[20]/div[1]/a/img"));
//   //*[@id="app"]/div[5]/div[3]/ul
                    WebElement goodsList = d.findElement(By.className("sld_goods_list"));
                    return  goodsList.isEnabled();
                }catch (Exception e){
                    return false;
                }
//                return d.getPageSource().contains(key);
            }
        });
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
//           Thread.sleep(4000);
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ID")));
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[5]/div[3]/ul")));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
              return browser.getDisplay("//*[@id=\"app\"]/div[5]/div[3]/ul/li[1]/div[1]/a/img");
            }
        });
//        browser.screenshot("target/1.png");
        browser.logScreenshot();
//        browser.screenshotLong("target/1long.png");
        browser.logScreenshotLong();

    }
    public void quitDriver(){
        driver.quit();
    }

}
