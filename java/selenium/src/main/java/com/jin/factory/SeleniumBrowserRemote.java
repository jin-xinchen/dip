package com.jin.factory;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Collections;

public class SeleniumBrowserRemote implements Browser {
    private RemoteWebDriver driver =null;
    private long millis = 1000;
    @Override
    public WebDriver openWebsite(String url, String type) {
        //chrome
        if (type.equalsIgnoreCase("c")) {
            ChromeOptions opt = new ChromeOptions();
//            opt.addArguments("disable-infobars");
            // From htt p s://sqa.stackexchange.com/questions/32444/how-to-disable-infobar-from-chrome
//            opt.setExperimentalOption("useAutomationExtension", false);
            opt.setExperimentalOption("excludeSwitches",
                    Collections.singletonList("enable-automation"));
            driver = new ChromeDriver(opt);
        } else if (type.equalsIgnoreCase("f")){
            //FirefoxDriver
            driver = new FirefoxDriver();
        }
        driver.get(url);
        return driver;
    }
    @Override
    public WebDriver runWebsiteBackend(String url, String type){
        //chrome
        if (type.equalsIgnoreCase("c")) {
            ChromeOptions opt = new ChromeOptions();
            opt.setHeadless(true);
            driver = new ChromeDriver(opt);
        } else if (type.equalsIgnoreCase("f")){
            //FirefoxDriver
            FirefoxOptions opt = new FirefoxOptions();
            opt.setHeadless(true);
            driver = new FirefoxDriver(opt);
        }
        driver.get(url);
        return driver;
    }
    @Override
    public void screenshot(Path targetFile) throws IOException, InterruptedException {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Files.copy(file.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
//        Files.copy(file.toPath(), Paths.get("target/screenshot.png"), StandardCopyOption.REPLACE_EXISTING);
        Thread.sleep(millis);
    }
    public void setMillis(long millis){
        this.millis = millis;
    }
    @Override
    public void screenshot(String targetFile) throws IOException, InterruptedException {
        Path p = Paths.get(targetFile);
        screenshot(p);
    }
    @Override
    public WebElement locator(String xpath){
        WebElement el = driver.findElement(By.xpath(xpath));
        return el;
    }
    public void inputText(String xpath ,String value){
        locator(xpath).sendKeys(value);
    }

    @Override
    public void screenshotLong(String image) throws IOException {
//        String CHROME_DRIVER_PATH= "d:\\webdrivers\\chromedriver.exe";
//        System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_PATH);
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//        driver.manage().timeouts().setScriptTimeout(Duration.ofSeconds(30));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        driver.get(url);
        Screenshot st = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(this.driver);
        try {
            ImageIO.write(st.getImage(), "PNG", new File(image));
        } catch (IOException e) {
            throw e;
        } finally {
//            driver.quit();
        }
    }

    @Override
    public void logScreenshotLong() throws IOException {
//        WebDriver driver;
//        System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
//        driver=new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        driver.get("https://www.cjavapy.com/article/411/");
//take screenshot of the entire page
        Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
        try {
            Path tmpPath = com.jin.tool.fileToolForWindow.getScreenshotPath();
            ImageIO.write(screenshot.getImage(),"PNG",new File(tmpPath.toString()));
        } catch (IOException e) {
            throw e;
        }
//        driver.quit();
    }
    @Override
    public void logScreenshot() throws IOException, InterruptedException {
        Path tmpPath = com.jin.tool.fileToolForWindow.getScreenshotPath();
        this.screenshot(tmpPath);
    }
    @Override
    public boolean getDisplay(String xpath){
        ////*[@id="app"]/div[5]/div[3]/ul/li[1]/div[1]/a/img
        WebElement image = driver.findElement(By.xpath(xpath));
        Boolean imageLoaded1 = (Boolean) ((JavascriptExecutor)driver)
                .executeScript(
                        "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0"
                        , image);
        if (!imageLoaded1)
        {
            return false;
        }
        return true;
    }

}
