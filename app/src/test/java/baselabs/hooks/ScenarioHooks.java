package baselabs.hooks;

import baselabs.framework.selenium.DriverManager;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import baselabs.utils.LoggerManager;

import java.util.logging.Level;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY;
import static org.openqa.selenium.edge.EdgeDriverService.EDGE_DRIVER_SILENT_OUTPUT_PROPERTY;

/**
 * This class contains hooks that are executed before and after scenarios.
 */
public class ScenarioHooks {
    private static final LoggerManager LOG = LoggerManager.getInstance();

    /**
     * Disables other Java loggers and automation messages from the browser.
     */
    public void disableOtherJavaLoggers() {
        System.setProperty(CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        System.setProperty(EDGE_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        java.util.logging.Logger.getLogger("").setLevel(Level.OFF);
    }

    /**
     * This method is executed before each scenario to disable Java loggers and initialize the WebDriver.
     *
     * @param scenario the current scenario
     */
    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        LOG.info("Scenario: --> " + scenario.getName());
        disableOtherJavaLoggers();
        DriverManager.getInstance().getWebDriver();
    }

    /**
     * This method is responsible to close the driver once the test is complete.
     */
    @AfterAll
    public static void afterAll() {
        DriverManager.getInstance().quitWebDriver();
    }
}
