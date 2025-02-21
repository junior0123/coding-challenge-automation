package baselabs.framework.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import baselabs.utils.LoggerManager;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class to manage Selenium WebDriver instances.
 */
public class DriverManager {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    public static final DriverConfig DRIVER_CONFIG = DriverConfig.getInstance();
    private static DriverManager instance;
    private WebDriver driver;
    private Wait<WebDriver> wait;

    /**
     * Protected constructor to initialize the DriverManager instance.
     */
    protected DriverManager() {
        initialize();
    }

    /**
     * Returns the singleton instance of DriverManager.
     *
     * @return the singleton instance of DriverManager
     */
    public static DriverManager getInstance() {
        if (instance == null || instance.driver == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    /**
     * Initializes the WebDriver based on the browser configuration.
     */
    private void initialize() {
        LOG.info("Initializing Selenium WebDriver Manager");
        switch (DRIVER_CONFIG.getBrowser()) {
            case "chrome" -> {
                LOG.info("Setting up ChromeDriver");
                DriverService.Builder<ChromeDriverService, ChromeDriverService.Builder> builder = new ChromeDriverService.Builder()
                        .withSilent(true);
                ChromeDriverService service = builder.build();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

                chromeOptions.addArguments("--password-store=basic");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", prefs);

                if (DRIVER_CONFIG.getHeadlessMode()) {
                    chromeOptions.addArguments("--headless");
                }

                driver = new ChromeDriver(service, chromeOptions);
                LOG.info("ChromeDriver initialized");
            }
            case "edge" -> {
                LOG.info("Setting up EdgeDriver");
                DriverService.Builder<EdgeDriverService, EdgeDriverService.Builder> builder = new EdgeDriverService.Builder()
                        .withSilent(true);
                EdgeDriverService service = builder.build();

                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                edgeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

                edgeOptions.addArguments("--password-store=basic");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                edgeOptions.setExperimentalOption("prefs", prefs);

                if (DRIVER_CONFIG.getHeadlessMode()) {
                    edgeOptions.addArguments("--headless");
                }

                driver = new EdgeDriver(service, edgeOptions);
                LOG.info("EdgeDriver initialized");
            }
            case "firefox" -> {
                LOG.info("Setting up FirefoxDriver");
                String firefoxLogFilePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator
                        + "firefox.log";
                DriverService.Builder<GeckoDriverService, GeckoDriverService.Builder> builder = new GeckoDriverService.Builder()
                        .withLogFile(new File(firefoxLogFilePath));
                GeckoDriverService service = builder.build();

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                firefoxOptions.setLogLevel(FirefoxDriverLogLevel.FATAL);
                if (DRIVER_CONFIG.getHeadlessMode()) {
                    firefoxOptions.addArguments("--headless");
                }

                driver = new FirefoxDriver(service, firefoxOptions);
                LOG.info("FirefoxDriver initialized");
            }
            default -> {
                LOG.error("Unsupported browser: " + DRIVER_CONFIG.getBrowser());
                throw new IllegalArgumentException("Unsupported browser: " + DRIVER_CONFIG.getBrowser());
            }
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(DRIVER_CONFIG.getImplicitWaitTime());
        wait = new FluentWait<>(driver)
                .withTimeout(DRIVER_CONFIG.getTimeout())
                .pollingEvery(DRIVER_CONFIG.getPollingTime())
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class)
                .ignoring(StaleElementReferenceException.class);

        LOG.info("WebDriver initialized and configured");
    }

    /**
     * Returns the WebDriver instance.
     *
     * @return the WebDriver instance
     */
    public WebDriver getWebDriver() {
        LOG.info("Returning WebDriver instance");
        return driver;
    }

    /**
     * Returns the FluentWait instance.
     *
     * @return the FluentWait instance
     */
    public Wait<WebDriver> getFluentWait() {
        LOG.info("Returning FluentWait instance");
        return wait;
    }

    /**
     * Quits the WebDriver instance.
     */
    public void quitWebDriver() {
        try {
            LOG.info("Closing WebDriver");
            driver.quit();
        } catch (Exception e) {
            LOG.error("Failed to close WebDriver: " + e.getMessage());
        }
        driver = null;
    }
}
