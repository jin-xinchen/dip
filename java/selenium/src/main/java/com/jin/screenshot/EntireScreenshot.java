package com.jin.screenshot;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class EntireScreenshot {
    public static void main(String[] args) {
       demo01(); // OK
//        screenshot("https://tousu.sina.com.cn/complaint/view/17352132083/", "D:\\11111111.png");
//        screenshot("https://www.cjavapy.com/article/411/", "D:\\22221111.png");
    }

    /**
     * this method is OK which can make full screenshot.
     * Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
     *
     * From : h t t p s : / / blog.csdn.net/weixin_39542477/article/details/116054058?spm=1001.2101.3001.6650.17&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-17.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-17.pc_relevant_default&utm_relevant_index=24
     */
    public static void demo01(){
        WebDriver driver;
//        System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.cjavapy.com/article/411/");
//take screenshot of the entire page
        Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            Path tmpPath = com.jin.tool.fileToolForWindow.getScreenshotPath();
            ImageIO.write(screenshot.getImage(),"PNG",new File(tmpPath.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    public static void screenshot(String url, String image){
//        String CHROME_DRIVER_PATH= "d:\\webdrivers\\chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_PATH);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);
        Screenshot st = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(driver);
        try {
            ImageIO.write(st.getImage(),"PNG",new File(image));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            driver.quit();
        }

    }

    /**
     * this method is not OK, which can not make full screenshot.
     */
    public static void demo02(){
        WebDriver driver;
//        System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
        driver = new ChromeDriver();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get("https://www.cjavapy.com/article/411/");
            new WebDriverWait(driver, Duration.ofSeconds(300))
                    .until(dr -> ((JavascriptExecutor) dr)
                    .executeScript("return document.readyState").equals("complete"));
            JavascriptExecutor jexec = (JavascriptExecutor) driver;
            int width = Integer.parseInt(jexec.executeScript("return document.body.scrollWidth")+"");
            int height = Integer.parseInt(jexec.executeScript("return document.body.scrollHeight")+"");
            int rollingDistance = 200;
            int frequency = height % rollingDistance == 0 ? height / rollingDistance : height / rollingDistance + 1;
            for (int i = 0; i < frequency; i++) {
                int length = i * rollingDistance;
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + length + ")");
            }
            driver.manage().window().setSize(new Dimension(width, height));
            Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver);
            com.jin.tool.fileToolForWindow.createDirectory("D:\\temp\\");
            BufferedImage image = screenshot.getImage();
            ImageIO.write(image, "PNG", new File("D:\\temp\\" + "AShot_BBC_Entire.png"));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

}
