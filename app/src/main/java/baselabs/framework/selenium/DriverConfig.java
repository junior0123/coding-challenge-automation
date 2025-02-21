package baselabs.framework.selenium;

import baselabs.utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * Singleton class to manage WebDriver configuration settings.
 */
public class DriverConfig {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final String WEB_DRIVER_FILE_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "main" + File.separator + "resources" + File.separator + "webdriver.properties";
    private static DriverConfig instance;
    private String browser;
    private Properties properties;

    /**
     * Protected constructor to initialize the DriverConfig instance.
     */
    protected DriverConfig() {
        initialize();
    }

    /**
     * Returns the singleton instance of DriverConfig.
     *
     * @return the singleton instance of DriverConfig
     */
    public static DriverConfig getInstance() {
        if (instance == null || instance.properties == null) {
            instance = new DriverConfig();
        }
        return instance;
    }

    /**
     * Initializes the DriverConfig by reading the WebDriver configuration properties.
     */
    private void initialize() {
        LOG.info("Reading WebDriver config");
        String selectedBrowser = System.getProperty("browser");
        if ((selectedBrowser == null) || (selectedBrowser.isEmpty())) {
            browser = "chrome";
        } else {
            browser = selectedBrowser.toLowerCase();
        }
        LOG.info("Selected browser is --> " + browser);

        properties = new Properties();
        Properties webDriverProperties = new Properties();
        try {
            webDriverProperties.load(new FileInputStream(WEB_DRIVER_FILE_PATH));
            LOG.info("Loaded WebDriver properties from file: " + WEB_DRIVER_FILE_PATH);
        } catch (IOException e) {
            LOG.error("Unable to load properties file: " + e.getMessage());
        }
        properties.putAll(webDriverProperties);
    }

    /**
     * Returns the browser name.
     *
     * @return the browser name
     */
    public String getBrowser() {
        LOG.info("Getting browser: " + browser);
        return browser;
    }

    /**
     * Gets the WebDriver setting for the specified key.
     *
     * @param setting the key of the setting
     * @return the value of the setting
     */
    private String getWebDriverSetting(String setting) {
        String value = (String) properties.get(setting);
        LOG.info("Getting WebDriver setting for key: " + setting + " --> " + value);
        return value;
    }

    /**
     * Returns the implicit wait time.
     *
     * @return the implicit wait time
     */
    public Duration getImplicitWaitTime() {
        long millis = Long.parseLong(getWebDriverSetting("webdriver.implicit.wait.time"));
        LOG.info("Getting implicit wait time: " + millis + " milliseconds");
        return Duration.ofMillis(millis);
    }

    /**
     * Returns the timeout duration.
     *
     * @return the timeout duration
     */
    public Duration getTimeout() {
        long millis = Long.parseLong(getWebDriverSetting("webdriver.timeout"));
        LOG.info("Getting timeout duration: " + millis + " milliseconds");
        return Duration.ofMillis(millis);
    }

    /**
     * Returns the polling time duration.
     *
     * @return the polling time duration
     */
    public Duration getPollingTime() {
        long millis = Long.parseLong(getWebDriverSetting("webdriver.polling.time"));
        LOG.info("Getting polling time duration: " + millis + " milliseconds");
        return Duration.ofMillis(millis);
    }

    /**
     * Returns whether headless mode is enabled.
     *
     * @return true if headless mode is enabled, false otherwise
     */
    public boolean getHeadlessMode() {
        boolean headlessMode = Boolean.parseBoolean(getWebDriverSetting("webdriver.headless.mode"));
        LOG.info("Getting headless mode: " + headlessMode);
        return headlessMode;
    }
}
