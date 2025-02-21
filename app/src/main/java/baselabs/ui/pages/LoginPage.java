package baselabs.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import baselabs.ui.BasePage;

/**
 * Page object class for the Login page.
 * Provides methods to interact with the login page elements.
 */
public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@value='Login']")
    private WebElement loginButton;

    @FindBy(css = ".error")
    private WebElement errorMessage;

    /**
     * Constructor to initialize the LoginPage elements and wait until the page is fully loaded.
     */
    public LoginPage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
        LOG.info("Initialized LoginPage with WebDriver and Wait instances");
    }

    /**
     * Waits until the login page elements are fully loaded.
     */
    public void waitUntilPageObjectIsLoaded() {
        LOG.info("Waiting until LoginPage elements are loaded");
        usernameInput = wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
        passwordInput = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        LOG.info("LoginPage elements are loaded");
    }

    /**
     * Gets the error message displayed on the login page.
     *
     * @return the error message text
     */
    public String getErrorMessage() {
        LOG.info("Getting error message text");
        return getText(errorMessage);
    }

    /**
     * Checks if the error message is displayed on the login page.
     *
     * @return true if the error message is displayed, false otherwise
     */
    public boolean isErrorMessageDisplayed() {
        LOG.info("Checking if error message is displayed");
        return isElementDisplayed(errorMessage);
    }

    /**
     * Logs in with valid credentials and navigates to the HomePage.
     *
     * @param username the username to log in with
     * @param password the password to log in with
     * @return a new instance of HomePage
     */
    public HomePage loginWithValidCredentials(String username, String password) {
        LOG.info("Logging in with valid credentials");
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        LOG.info("Logged in with valid credentials");
        return new HomePage();
    }

    /**
     * Attempts to log in with invalid credentials.
     *
     * @param username the username to log in with
     * @param password the password to log in with
     */
    public void loginWithInvalidCredentials(String username, String password) {
        LOG.info("Attempting to log in with invalid credentials");
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        LOG.info("Attempted to log in with invalid credentials");
    }

    /**
     * Refreshes the login page.
     */
    public void refreshPage() {
        LOG.info("Refreshing the login page");
        driver.navigate().refresh();
    }
}
