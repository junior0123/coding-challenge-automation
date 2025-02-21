package baselabs.ui;

import baselabs.framework.selenium.DriverManager;
import baselabs.utils.LoggerManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

/**
 * Abstract base class for all page objects.
 * Provides common methods and utilities for interacting with web elements.
 */
public abstract class BasePage {
    protected static final LoggerManager LOG = LoggerManager.getInstance();
    protected final WebDriver driver;
    protected final Wait<WebDriver> wait;

    /**
     * Constructor to initialize the WebDriver and Wait instances.
     */
    public BasePage() {
        DriverManager driverManager = DriverManager.getInstance();
        this.driver = driverManager.getWebDriver();
        this.wait = driverManager.getFluentWait();
        PageFactory.initElements(driver, this);
        LOG.info("Initialized BasePage with WebDriver and Wait instances");
    }

    /**
     * Abstract method to be implemented by subclasses to wait until the page object is fully loaded.
     *
     * @throws WebDriverException if the page object is not loaded
     */
    public abstract void waitUntilPageObjectIsLoaded() throws WebDriverException;

    /**
     * Clicks on the specified web element.
     *
     * @param element the web element to click
     */
    protected void click(WebElement element) {
        try {
            LOG.info("Attempting to click element: " + element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            LOG.info("Clicked element: " + element);
        } catch (Exception e) {
            LOG.error("Failed to click element: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Types the specified text into the specified web element.
     *
     * @param element the web element to type into
     * @param text    the text to type
     */
    protected void type(WebElement element, String text) {
        try {
            LOG.info("Attempting to type text into element: " + element);
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            LOG.info("Typed text into element: " + element);
        } catch (Exception e) {
            LOG.error("Failed to type text: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the specified web element is displayed.
     *
     * @param element the web element to check
     * @return true if the element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            LOG.info("Checking if element is displayed: " + element);
            wait.until(ExpectedConditions.visibilityOf(element));
            boolean isDisplayed = element.isDisplayed();
            LOG.info("Element displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            LOG.error("Element is not displayed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the text of the specified web element.
     *
     * @param element the web element to get the text from
     * @return the text of the element
     */
    protected String getText(WebElement element) {
        try {
            LOG.info("Getting text from element: " + element);
            wait.until(ExpectedConditions.visibilityOf(element));
            String text = element.getText();
            LOG.info("Got text from element: " + text);
            return text;
        } catch (Exception e) {
            LOG.error("Failed to get text: " + e.getMessage());
            throw e;
        }
    }
}
