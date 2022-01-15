package com.jin;


import org.checkerframework.checker.units.qual.Current;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * https://www.cxyzjd.com/article/qq_30044187/52851555
 *
 * 错误提醒：
 * 1）Exception in thread "main" org.openqa.selenium.WebDriverException: Cannot find firefox binary in PATH. Make sure firefox is installed.
 * 出现这个错误，是说明你的 FireFox 文件并没有安装在默认目录下，这时候需要在最开始执行：System.setProperty 设置环境变量  "webdriver.firefox.bin" 将自己机器上 FireFox 的正确路径设置完毕后即可。
 *
 * 2）Exception in thread "main" org.openqa.selenium.UnsupportedCommandException: Bad request
 *
 * 出现这个错误，很有意思。 查了一下 有人说应该是 hosts 出现了问题，加上一个 127.0.0.1  localhost 就行了，但我的 hosts 上肯定有这个玩意，为啥也会出现这个问题呢？
 *
 * 经过调试，发现 127.0.0.1 localhost 的设置必须要在 hosts 文件的最开始，而且如果后面有其他设置后，也不要再出现同样的 127.0.0.1 localhost ，只要有就会出错。（因为我为了方便访问 google 的网站，专门加入了 smarthosts 的内容，导致了 localhost 的重复）
 */
public class OneByFireFox {
    public static void main(String[] args) {
        FirefoxOptions opt = new FirefoxOptions();
        opt.addArguments("disable-infobars");
        opt.setHeadless(true);  // do not show on the browser, let it run in back end.
        // 如果你的 FireFox 没有安装在默认目录，那么必须在程序中设置
//        C:\Program Files\Google\Chrome\Application\chrome.exe
        //System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        // 创建一个 FireFox 的浏览器实例
        WebDriver driver = new FirefoxDriver(opt);

//
//    // 让浏览器访问 Baidu
//    driver.get("http://www.baidu.com");
//    // 用下面代码也可以实现
//    // driver.navigate().to("http://www.baidu.com");
//
//    // 获取 网页的 title
//    System.out.println("1 Page title is: " + driver.getTitle());
//
//    // 通过 id 找到 input 的 DOM
//    WebElement element = driver.findElement(By.id("kw"));//driver.get("http://www.baidu.com");
//
//    // 输入关键字
//    element.sendKeys("zTree");
//
//    // 提交 input 所在的  form
//    element.submit();
//        driver.findElements(By.className("className"));
//        driver.findElements(By.cssSelector(".className"));
//        driver.findElements(By.id("elementId"));
//        driver.findElements(By.linkText("linkText"));
//        driver.findElements(By.name("elementName"));
//        driver.findElements(By.partialLinkText("partialText"));
//        driver.findElements(By.tagName("elementTagName"));
//        driver.findElements(By.xpath("xPath"));
//    // 通过判断 title 内容等待搜索页面加载完毕，间隔10秒
//    (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//        public Boolean apply(WebDriver d) {
//            return d.getTitle().toLowerCase().endsWith("ztree");
//        }
//    });
        driver.get("http://www.darwynn.com");
        System.out.println("1 Page title is: " + driver.getTitle());
        System.out.println("user.dir : "+System.getProperty("user.dir"));

        WebElement element = driver.findElement(By.cssSelector(".text"));
        element.sendKeys("zTree");
        WebElement button = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/form/input[2]"));
        WebElement screenClose = driver.findElement(By.xpath("//*[@id=\"app\"]/div[5]/div[2]/img"));
        if(screenClose.isEnabled()){
            screenClose.click();
        }
        button.click();
        driver.getPageSource().contains("darwynn");
////Wait for the alert to be displayed and store it in a variable
//        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
////Store the alert text in a variable
//        String text = alert.getText();
////Press the OK button
//        alert.accept();

        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().endsWith("darwynn");
            }
        });

        // 显示搜索结果页面的 title
        System.out.println("2 Page title is: " + driver.getTitle());
        System.out.println("CurrentUrl: "+driver.getCurrentUrl());
        //关闭浏览器
        driver.close();
        driver.quit();
    }
}
