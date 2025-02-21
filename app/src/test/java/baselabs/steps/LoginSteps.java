package baselabs.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import baselabs.framework.CredentialsManager;
import baselabs.ui.PageTransporter;
import baselabs.ui.pages.HomePage;
import baselabs.ui.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * This class contains the step definitions for the login-related scenarios.
 */
public class LoginSteps {

    private final PageTransporter pageTransporter;
    private LoginPage loginPage;
    private HomePage homePage;

    /**
     * Constructor to initialize the PageTransporter instance.
     */
    public LoginSteps() {
        this.pageTransporter = PageTransporter.getInstance();
    }

    /**
     * Navigates to the Login page.
     */
    @Given("I navigate to the Login page")
    public void navigateToLoginPage() {
        loginPage = pageTransporter.navigateToLoginPage();
    }

    /**
     * Logs in to the page using valid credentials for the specified user role.
     *
     * @param userRole the role of the user whose credentials will be used for login
     */
    @When("I login to the page using valid credentials for {string}")
    public void loginWithCredentials(String userRole) {
        String username = CredentialsManager.getInstance().getUsername(userRole);
        String password = CredentialsManager.getInstance().getPassword(userRole);
        homePage = loginPage.loginWithValidCredentials(username, password);
    }

    /**
     * Verifies that the user has successfully logged in to the home page.
     */
    @Then("I should login to home page successfully")
    public void verifySuccesfullyLogin() {
        boolean isLoggedIn = homePage.verifyIsLoggedSuccesfylly();
        assertTrue(isLoggedIn, "User is not logged in");
    }

    /**
     * Logs out from the page.
     */
    @When("I logout from the page")
    public void logoutFromPage() {
        homePage.clickLoginLink();
    }

    /**
     * Verifies that the user has successfully logged out.
     */
    @Then("I should logout successfully")
    public void verifySuccessfullyLogout() {
        boolean isLoggedOut = homePage.verifyIsLoggedOut();
        assertTrue(isLoggedOut, "User is not logged out");
    }

    /**
     * Attempts to log in using the specified username and password.
     *
     * @param username the username to be used for login
     * @param password the password to be used for login
     */
    @When("I attempt to log in using username {string} and password {string}")
    public void attemptToLoginWithInvalidCredentials(String username, String password) {
        loginPage.loginWithInvalidCredentials(username, password);
    }

    /**
     * Verifies that an error message indicating login failure is displayed.
     */
    @Then("I should see an error message indicating login failure")
    public void verifyLoginFailure() {
        boolean isErrorMessageDisplayed = loginPage.isErrorMessageDisplayed();
        assertTrue(isErrorMessageDisplayed, "Error message is not displayed");
    }

    /**
     * Refreshes the current page.
     */
    @When("I refresh the page")
    public void refreshPage() {
        loginPage.refreshPage();
    }

    /**
     * Verifies that the user is still logged in after refreshing the page.
     */
    @Then("I should still be logged in")
    public void verifyStillLoggedIn() {
        boolean isLoggedIn = homePage.verifyIsLoggedSuccesfylly();
        assertTrue(isLoggedIn, "User is not logged in");
    }
}
