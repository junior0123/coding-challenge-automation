package baselabs.ui;

import baselabs.framework.CredentialsManager;
import baselabs.framework.selenium.DriverManager;
import org.openqa.selenium.WebDriver;
import baselabs.ui.pages.*;
import baselabs.utils.LoggerManager;

/**
 * Singleton class to manage navigation between pages.
 */
public class PageTransporter {
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static final CredentialsManager CREDENTIALS_MANAGER = CredentialsManager.getInstance();
    private String homePageURL;

    private WebDriver driver;
    private static PageTransporter instance;

    /**
     * Protected constructor to initialize the PageTransporter instance.
     */
    protected PageTransporter() {
        initialize();
    }

    /**
     * Returns the singleton instance of PageTransporter.
     *
     * @return the singleton instance of PageTransporter
     */
    public static PageTransporter getInstance() {
        if (instance == null) {
            instance = new PageTransporter();
        }
        return instance;
    }

    /**
     * Initializes the PageTransporter with the WebDriver and base URL.
     */
    private void initialize() {
        LOG.info("Initializing Page Transporter");
        this.driver = DriverManager.getInstance().getWebDriver();
        this.homePageURL = CREDENTIALS_MANAGER.getBaseURL();
    }

    /**
     * Navigates to the specified URL.
     *
     * @param url the URL to navigate to
     */
    private void goToURL(String url) {
        LOG.info("Navigating to URL: " + url);
        driver.navigate().to(url);
    }

    /**
     * Navigates to the Login page.
     *
     * @return a new instance of LoginPage
     */
    public LoginPage navigateToLoginPage() {
        LOG.info("Navigating to Login Page");
        goToURL(homePageURL + "login");
        return new LoginPage();
    }
}
