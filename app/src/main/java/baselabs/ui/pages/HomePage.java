package baselabs.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import baselabs.ui.BasePage;

/**
 * This class is in charge of executing the different steps available on the page.
 * Provides methods to interact with the home page elements.
 *
 * @version 1.0
 */
public class HomePage extends BasePage {

    @FindBy(css = ".col-md-4 > p > a")
    private WebElement loginLink;

    @FindBy(xpath =  "(//div[contains(@class, 'row')]/div[contains(@class, 'col-md-8')])[2]")
    private WebElement quotesSectionRow;

    /**
     * Constructor to initialize the HomePage elements and wait until the page is fully loaded.
     */
    public HomePage() {
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
        LOG.info("Initialized HomePage with WebDriver and Wait instances");
    }

    /**
     * Waits until the home page elements are fully loaded.
     */
    @Override
    public void waitUntilPageObjectIsLoaded() {
        LOG.info("Waiting until HomePage elements are loaded");
        loginLink = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        quotesSectionRow = wait.until(ExpectedConditions.visibilityOf(quotesSectionRow));
        LOG.info("HomePage elements are loaded");
    }

    /**
     * Clicks on the login link.
     */
    public void clickLoginLink() {
        LOG.info("Clicking on login link");
        click(loginLink);
    }

    /**
     * Verifies that the user has successfully logged in.
     *
     * @return true if the user is logged in, false otherwise
     */
    public boolean verifyIsLoggedSuccesfylly() {
        LOG.info("Verifying if user is logged in successfully");
        boolean isLoggedIn = loginLink.getText().contains("Logout");
        LOG.info("User logged in successfully: " + isLoggedIn);
        return isLoggedIn;
    }

    /**
     * Clicks on the logout link.
     */
    public void clickOnLogout() {
        LOG.info("Clicking on logout link");
        click(loginLink);
    }

    /**
     * Verifies that the user has successfully logged out.
     *
     * @return true if the user is logged out, false otherwise
     */
    public boolean verifyIsLoggedOut() {
        LOG.info("Verifying if user is logged out successfully");
        boolean isLoggedOut = loginLink.getText().contains("Login");
        LOG.info("User logged out successfully: " + isLoggedOut);
        return isLoggedOut;
    }
}
