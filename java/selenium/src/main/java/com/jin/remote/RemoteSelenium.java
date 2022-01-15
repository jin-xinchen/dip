package com.jin.remote;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Duration;
import java.util.Set;

public class RemoteSelenium {
    public static void main(String[] args) {
        try{
        RemoteSelenium rs = new RemoteSelenium();
//            System.out.println(    takeScreenshot());
            rs.screenshot("https://www.runoob.com/design-pattern/factory-pattern.html",
                    1l,300,500,500,
                    "/home/Desktop/test/ImageIO2.jpg"
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 截图
     *
     * @param domain          地址  https://www.baidu.com/
     * @param correlationId   图片附件保留关联id
     * @param rollingDistance 滚动距离 300
     * @param waitTime        滚动等待时间(毫秒) 500
     * @param implicitlyWait  浏览器等待时间
     * @return void
     * @date 2020/9/28 10:48
     * @author Dora
     **/
    public void screenshot(String domain, Long correlationId
            , int rollingDistance, long waitTime, long implicitlyWait, String fileName) throws Exception {
        if (domain ==null || correlationId==null) {
            throw new Exception("[域名|关联id]不能为空");
        }
        long o = System.currentTimeMillis();
//        import org.openqa.selenium.logging.LogType;
//import org.openqa.selenium.logging.LoggingPreferences;
//        log.info("网站:{},截图-上传开始时间:{}", domain, o);
        WebDriver driver = this.createWebDriver();
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        try {
//          Deprecated  driver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
            driver.manage().window().maximize();
            driver.get(domain);

            new WebDriverWait(driver, Duration.ofSeconds(300)).until(drive -> ((JavascriptExecutor) drive)
                    .executeScript("return document.readyState").equals("complete"));
//            (new WebDriverWait(driver, Duration.ofSeconds(10))).until(new ExpectedCondition<Boolean>() {
//                public Boolean apply(WebDriver d) {
//                    return d.getTitle().toLowerCase().endsWith("darwynn");
//                }
//            });
            driver.manage().window().setSize(new Dimension(1920, 1080));
            JavascriptExecutor je = (JavascriptExecutor) driver;
            int height = Integer.parseInt(je.executeScript("return document.body.scrollHeight") + "");
//            log.info("{},当前页面高度:{}", domain, height);
            int frequency = height % rollingDistance == 0 ? height / rollingDistance : height / rollingDistance + 1;
            for (int i = 0; i < frequency; i++) {
                int length = i * rollingDistance;
                Thread.sleep(waitTime);
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + length + ")");
            }
            driver.manage().window().setSize(new Dimension(1440, height));
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,0)");
//            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFile(scrFile, new File("/home/Desktop/test/image01.png"));//disk root
//            https://mvnrepository.com/artifact/ru.yandex.qatools.ashot/ashot/1.5.3
            //import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

//            Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver);
            Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);


//            log.info("网站:{},截图-上传结束时间:{}", domain, System.currentTimeMillis() - o);
//            File outputfile = new File("saved.png");
//            ImageIO.write(image,"jpg", outputfile);
            Path screenshotPath = com.jin.tool.fileToolForWindow.getScreenshotPath();
            javax.imageio.ImageIO.write(screenshot.getImage(),"jpg",  new File(screenshotPath.toString())); // 产生图片不成功，需要再测试。
//            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
//            log.error(e.getMessage());
        } finally {
        }
        driver.quit();
    }
//h t t p s : // b l o g .csdn.net/qq_36934544/article/details/113243223
    public static WebDriver createWebDriver01() {
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("--disable-extensions--");
        options.addArguments("proxy=null");
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--incognito");

//        org.openqa.selenium.remote.DesiredCapabilities capability =
//                org.openqa.selenium.remote.DesiredCapabilities.chrome();
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.LINUX);
        WebDriver driver= null;
        try {
            String CHROME_DRIVER_PATH = "";
            driver = new org.openqa.selenium.remote.RemoteWebDriver(new java.net.URL(CHROME_DRIVER_PATH),
                    capability);
        } catch (java.net.MalformedURLException e) {
//            logger.error("driver异常：{}", e);
        }
        return driver;
    }
    public static WebDriver createWebDriver() {
        WebDriver driver= null;
        try {
            driver = new ChromeDriver();
        } catch (Exception e) {
//            logger.error("driver异常：{}", e);
        }
        return driver;
    }
//https://github.com/GladsonAntony
    private static String takeScreenshot01() {
        try {
            // now copy the screenshot to desired location using copyFile
            Path screenshotPath = com.jin.tool.fileToolForWindow.getScreenshotPath();
            WebDriver driver = createWebDriver();
            Screenshot screenshot = new AShot()
                    .takeScreenshot(driver);

            try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(300));
                driver.manage().window().maximize();
                driver.get("https://www.runoob.com/design-pattern/factory-pattern.html");
                new WebDriverWait(driver, Duration.ofSeconds(300)).until(drive -> ((JavascriptExecutor) drive)
                        .executeScript("return document.readyState").equals("complete"));
                driver.manage().window().setSize(new Dimension(1920, 1080));
                JavascriptExecutor je = (JavascriptExecutor) driver;
                int height = Integer.parseInt(je.executeScript("return document.body.scrollHeight") + "");
                int rollingDistance = 300;
                int waitTime = 200;
                int frequency = height % rollingDistance == 0 ? height / rollingDistance : height / rollingDistance + 1;
                for (int i = 0; i < frequency; i++) {
                    int length = i * rollingDistance;
                    Thread.sleep(waitTime);
                    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + length + ")");
                }
                driver.manage().window().setSize(new Dimension(1440, height));
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight,0)");

            } catch (Exception e) {
                e.printStackTrace();
            }
            ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotPath.toString()));

            return screenshotPath.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void demo01() throws InterruptedException, IOException {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://stackoverflow.com/questions/54724963/ashot-is-not-taking-screenshot-of-correct-element");
        Thread.sleep(2000);
        WebElement webElement = driver.findElement(By.id("post-form"));
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver,webElement);

        Path screenshotPath = com.jin.tool.fileToolForWindow.getScreenshotPath();
        ImageIO.write(screenshot.getImage(),"PNG",new File(screenshotPath.toString()));
        Thread.sleep(2000);
        driver.close();
        driver.quit();
    }


}
