package com.jin.factory;

import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SeleniumBrowserTest {
    WebDriver driver = null;
    SeleniumBrowser browser = new SeleniumBrowser();
    @Before
    public void before02(){
        System.out.println("Before");
        driver =  browser.openWebsite("https://www.google.com","f");
    }

    @Ignore
    public void testOpenBrowser() throws InterruptedException {
        System.out.println("After");
        SeleniumBrowser b = new SeleniumBrowser();
        WebDriver d =  b.openWebsite("","c");
//        d.get("https://www.google.com");
//        System.out.println("aaaa:"+d.getTitle());
        Assert.assertEquals(d.getTitle(),"");
        Thread.sleep(1000);
//        d.close();
        d.quit();
        Thread.sleep(1000);
        d = null;
        d = b.openWebsite("","f");
//        System.out.println(d.getTitle());
        Thread.sleep(1000);
        Assert.assertEquals(d.getTitle(),"");
        d.quit();
    }

    @Test
    public void testScreenshot() {
        System.out.println("run...");
        Path p = Paths.get("target/test.png");
        try {
            browser.screenshot(p);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

    }
    @After
    public void after() throws InterruptedException {
        System.out.println("”@After”");
        Thread.sleep(1000);
        driver.quit();
    }
    @BeforeClass
    public static void beforeClass() {
//        System.out.println("@BeforeClass");
    };

    @AfterClass
    public static void afterClass() {
//        System.out.println("”@AfterClass”");
    };
}