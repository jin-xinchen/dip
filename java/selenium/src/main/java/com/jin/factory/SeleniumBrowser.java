package com.jin.factory;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

public class SeleniumBrowser implements Browser{
    public static void main(String[] args) {
       String s = System.getProperty("webdriver.firefox.bin");
        System.out.println(s);
    }
    private WebDriver driver =null;
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
    @Override
    public void screenshot(String targetFile) throws IOException, InterruptedException {
        Path p = Paths.get(targetFile);
        screenshot(p);
    }
    public void setMillis(long millis){
        this.millis = millis;
    }
    @Override
    public WebElement locator(String xpath){
        WebElement el = driver.findElement(By.xpath(xpath));
        return el;
    }
    @Override
    public void inputText(String xpath, String value){
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
            ImageIO.write(st.getImage(),"PNG",new File(image));
        } catch (IOException e) {
            throw e;
        }finally {
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
/**
 ChromeOptions createChromeOptions(Config config, Proxy proxy) {
 ChromeOptions options = new ChromeOptions();
 options.setHeadless(config.headless());
 if (!config.browserBinary().isEmpty()) {
 log.info("Using browser binary: " + config.browserBinary());
 options.setBinary(config.browserBinary());
 }
 options.merge(createCommonCapabilities(config, proxy));
 options = transferChromeOptionsFromSystemProperties(options);
 log.config("Chrome options:" + options.toString());
 return options;
 }
 origin: selenide/selenide
 RemoteDriverFactory.getHeadlessCapabilities(...)
 private Capabilities getHeadlessCapabilities(Config config, Browser browser) {
 log.info("Starting in headless mode");
 if (browser.isChrome()) {
 ChromeOptions options = new ChromeOptions();
 options.setHeadless(config.headless());
 return options;
 } else if (browser.isFirefox()) {
 FirefoxOptions options = new FirefoxOptions();
 options.setHeadless(config.headless());
 return options;
 } else {
 log.warning("Headless mode on remote server is only supported for Chrome/Firefox, setting will be ignored.");
 }
 return new DesiredCapabilities();
 }
 origin: galenframework/galen
 SeleniumBrowserFactory.getBrowserCapabilities(...)
 public static DesiredCapabilities getBrowserCapabilities(String driverParameter, boolean headless) {
 DesiredCapabilities capabilities = null;
 if (driverParameter == null || driverParameter.equalsIgnoreCase(FIREFOX)) {
 capabilities = DesiredCapabilities.firefox();
 FirefoxOptions options = new FirefoxOptions();
 options.setHeadless(headless);
 capabilities.merge(options);
 }
 else if (driverParameter.equalsIgnoreCase(IE)) {
 capabilities = DesiredCapabilities.internetExplorer();
 capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
 capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
 }
 else if (driverParameter.equalsIgnoreCase(CHROME)) {
 capabilities = DesiredCapabilities.chrome();
 ChromeOptions options = new ChromeOptions();
 options.setHeadless(headless);
 options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
 capabilities.merge(options);
 }
 return capabilities;
 }
 origin: org.mycore/selenium-utils
 MCRChromeDriverFactory.getChromeOptions(...)
 private ChromeOptions getChromeOptions(Locale locale) {
 String formattedLocale = locale.getCountry().isEmpty() ? locale.getLanguage()
 : locale.getLanguage() + "-" + locale.getCountry().toLowerCase(Locale.ROOT) + "," + locale.getLanguage();
 ChromeOptions opts = new ChromeOptions();
 HashMap<String, String> prefs = new HashMap<>();
 prefs.put("intl.accept_languages", formattedLocale);
 opts.setExperimentalOption("prefs", prefs);
 LogManager.getLogger().info("is headless: {}", isHeadless());
 opts.setHeadless(isHeadless());
 opts.addArguments("--force-color-profile=srgb");//makes screenshots analyzable
 return opts;
 }
 }
 origin: paypal/SeLion
 ChromeCapabilitiesBuilder.getDefaultChromeOptions()
 private ChromeOptions getDefaultChromeOptions() {
 final String userAgent = getUserAgent();
 final ChromeOptions options = new ChromeOptions();
 options.addArguments("--test-type");
 options.addArguments("--ignore-certificate-errors");
 if ((userAgent != null) && (!userAgent.trim().isEmpty())) {
 options.addArguments("--user-agent=" + userAgent);
 }
 options.setHeadless(Boolean.parseBoolean(getLocalConfigProperty(ConfigProperty.BROWSER_RUN_HEADLESS)));

 return options;
 }
 }
 origin: alfa-laboratory/akita
 CustomDriverProvider.getChromeDriverOptions(...)
 */
 /**
 * Задает options для запуска Chrome драйвера
 * options можно передавать, как системную переменную, например -Doptions=--load-extension=my-custom-extension
 * @return ChromeOptions
 */
/*
 private ChromeOptions getChromeDriverOptions(DesiredCapabilities capabilities) {
    log.info("---------------Chrome Driver---------------------");
    ChromeOptions chromeOptions = !options[0].equals("") ? new ChromeOptions().addArguments(options) : new ChromeOptions();
    chromeOptions.setCapability(CapabilityType.BROWSER_VERSION, loadSystemPropertyOrDefault(CapabilityType.BROWSER_VERSION, VERSION_LATEST));
    chromeOptions.setHeadless(getHeadless());
    chromeOptions.merge(capabilities);
    return chromeOptions;
}
origin: Frameworkium/frameworkium-core
        ChromeImpl.getCapabilities()
@Override
public ChromeOptions getCapabilities() {
        ChromeOptions chromeOptions = new ChromeOptions();
        // useful defaults
        chromeOptions.setCapability(
        "chrome.switches",
        Collections.singletonList("--no-default-browser-check"));
        chromeOptions.setCapability(
        "chrome.prefs",
        ImmutableMap.of("profile.password_manager_enabled", "false"));
        // Workaround Docker/Travis issue
        if (Boolean.parseBoolean(System.getenv("CHROME_NO_SANDBOX"))) {
        chromeOptions.addArguments("--no-sandbox");
        }
        // Use Chrome's built in device emulators
        if (Property.DEVICE.isSpecified()) {
        chromeOptions.setExperimentalOption(
        "mobileEmulation",
        ImmutableMap.of("deviceName", Property.DEVICE.getValue()));
        }
        chromeOptions.setHeadless(Property.HEADLESS.getBoolean());
        return chromeOptions;
        }
        origin: net.jangaroo/jangaroo-maven-plugin
        JooTestMojo.createWebDriver(...)
private WebDriver createWebDriver(DriverManagerType driverManagerType) throws IllegalArgumentException, WebDriverManagerException {
        switch (driverManagerType) {
        case CHROME:
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments(jooUnitWebDriverBrowserArguments);
        getLog().info("Starting Chrome with " + jooUnitWebDriverBrowserArguments.size() + " arguments: " + String.join(" ", jooUnitWebDriverBrowserArguments));
        return new ChromeDriver(chromeOptions);
        case FIREFOX:
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(true);
        firefoxOptions.addArguments(jooUnitWebDriverBrowserArguments);
        getLog().info("Starting Firefox with " + jooUnitWebDriverBrowserArguments.size() + " arguments: " + String.join(" ", jooUnitWebDriverBrowserArguments));
        return new FirefoxDriver(firefoxOptions);
        case EDGE:
        return new EdgeDriver(); // no headless mode and no arguments yet :-(
        case IEXPLORER:
        return new InternetExplorerDriver(); // no headless mode and no arguments yet :-(
        case OPERA:
        return new OperaDriver(); // no headless mode and no arguments yet :-(
        }
        throw new IllegalArgumentException();
        }
        origin: com.galenframework/galen-core
        SeleniumBrowserFactory.getBrowserCapabilities(...)
public static DesiredCapabilities getBrowserCapabilities(String driverParameter, boolean headless) {
        DesiredCapabilities capabilities = null;
        if (driverParameter == null || driverParameter.equalsIgnoreCase(FIREFOX)) {
        capabilities = DesiredCapabilities.firefox();
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(headless);
        capabilities.merge(options);
        }
        else if (driverParameter.equalsIgnoreCase(IE)) {
        capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        }
        else if (driverParameter.equalsIgnoreCase(CHROME)) {
        capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        capabilities.merge(options);
        }
        return capabilities;
        }
        origin: searls/jasmine-maven-plugin
        WebDriverFactory.createChromeDriver(...)
private static WebDriver createChromeDriver(WebDriverConfiguration config) {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        return new ChromeDriver(customizeCapabilities(new ChromeOptions().setHeadless(true), config));
        }
        }
        origin: vmi/selenese-runner-java
        ChromeDriverFactory.newChromeOptions(...)
        ChromeOptions options = new ChromeOptions();
        if (driverOptions.has(HEADLESS))
        options.setHeadless(driverOptions.getBoolean(HEADLESS));
        Proxy proxy = newProxy(driverOptions);
        if (proxy != null)
        origin: Rsl1122/Plan-PlayerAnalytics
        SeleniumExtension.getChromeWebDriver()
private WebDriver getChromeWebDriver() {
        if (Boolean.parseBoolean(System.getenv(CIProperties.IS_TRAVIS))) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/usr/bin/google-chrome-stable");
        chromeOptions.setHeadless(true);
        chromeOptions.setCapability(SUPPORTS_JAVASCRIPT, true);
        return new ChromeDriver(chromeOptions);
        } else {
        return new ChromeDriver();
        }
        }
        origin: Rsl1122/Plan-PlayerAnalytics
        SeleniumDriver.getChromeWebDriver()
private WebDriver getChromeWebDriver() {
        if (Boolean.parseBoolean(System.getenv(CIProperties.IS_TRAVIS))) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("/usr/bin/google-chrome-stable");
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.setCapability(SUPPORTS_JAVASCRIPT, true);
        return new ChromeDriver(chromeOptions);
        } else {
        return new ChromeDriver();
        }
        }
        origin: mirkosertic/Bytecoder
        BytecoderUnitTestRunner.newDriverForTest()
private WebDriver newDriverForTest() {
final ChromeOptions theOptions = new ChromeOptions().setHeadless(true);
        theOptions.addArguments("--js-flags=experimental-wasm-eh");
        theOptions.addArguments("--enable-experimental-wasm-eh");
final LoggingPreferences theLoggingPreferences = new LoggingPreferences();
        theLoggingPreferences.enable(LogType.BROWSER, Level.ALL);
        theOptions.setCapability(CapabilityType.LOGGING_PREFS, theLoggingPreferences);
final DesiredCapabilities theCapabilities = DesiredCapabilities.chrome();
        theCapabilities.setCapability(ChromeOptions.CAPABILITY, theOptions);
        return new RemoteWebDriver(DRIVERSERVICE.getUrl(), theCapabilities);
        }
        origin: de.mirkosertic.bytecoder/bytecoder-core
        BytecoderUnitTestRunner.newDriverForTest()
private WebDriver newDriverForTest() {
final ChromeOptions theOptions = new ChromeOptions().setHeadless(true);
        theOptions.addArguments("--js-flags=experimental-wasm-eh");
        theOptions.addArguments("--enable-experimental-wasm-eh");
final LoggingPreferences theLoggingPreferences = new LoggingPreferences();
        theLoggingPreferences.enable(LogType.BROWSER, Level.ALL);
        theOptions.setCapability(CapabilityType.LOGGING_PREFS, theLoggingPreferences);
final DesiredCapabilities theCapabilities = DesiredCapabilities.chrome();
        theCapabilities.setCapability(ChromeOptions.CAPABILITY, theOptions);
        return new RemoteWebDriver(DRIVERSERVICE.getUrl(), theCapabilities);
        }
        origin: mozilla/zest
        ZestClientLaunch.invoke(...)
        } else if ("Chrome".equalsIgnoreCase(this.browserType)) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(isHeadless());
        if (isProfilePathSet()) {
        Path path = Paths.get(profilePath);
        origin: org.bitbucket.iamkenos/cissnei-selenium
        ChromeSetup.setDriverOptions()
        options.setHeadless(drHeadless);
        origin: TinkoffCreditSystems/QVisual
        WebDriverCapabilities.setupChrome()
        chromeOptions.addArguments("test-type=webdriver");
        chromeOptions.addArguments("use-mock-keychain");
        chromeOptions.setHeadless(BROWSER_HEADLESS);
        origin: de.otto/jlineup-core
        BrowserUtils.getWebDriverByConfig(...)
        options.setHeadless(true);
        options.addArguments("--window-size=" + width + "," + jobConfig.windowHeight);
//From: https://www.tabnine.com/code/java/methods/org.openqa.selenium.chrome.ChromeOptions/setHeadless
 */
