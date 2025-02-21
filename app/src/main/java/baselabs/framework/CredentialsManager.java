package baselabs.framework;

import baselabs.utils.LoggerManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton class to manage credentials and environment settings.
 */
public class CredentialsManager {
    private Properties properties;
    private static final LoggerManager LOG = LoggerManager.getInstance();
    private static CredentialsManager instance;
    private String envId;

    /**
     * Private constructor to initialize the CredentialsManager instance.
     */
    private CredentialsManager() {
        initialize();
    }

    /**
     * Initializes the CredentialsManager by reading environment variables and loading properties.
     */
    private void initialize() {
        LOG.info("Reading environment variables");
        String wpEnvironmentId = System.getProperty("envId");
        if ((wpEnvironmentId == null) || (wpEnvironmentId.isEmpty())) {
            envId = "local";
        } else {
            envId = wpEnvironmentId.toLowerCase();
        }
        LOG.info("Environment ID --> " + envId);

        properties = new Properties();
        String propertiesFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "environments.properties";
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);
            LOG.info("Loaded properties from file: " + propertiesFile);
        } catch (IOException e) {
            LOG.error("Failed to load properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of CredentialsManager.
     *
     * @return the singleton instance of CredentialsManager
     */
    public static CredentialsManager getInstance() {
        if (instance == null) {
            instance = new CredentialsManager();
        }
        return instance;
    }

    /**
     * Gets the environment setting for the specified key.
     *
     * @param setting the key of the setting
     * @return the value of the setting
     */
    private String getEnvironmentSetting(String setting) {
        String value = (String) getInstance().properties.get(setting);
        LOG.info("Getting environment setting for key: " + setting + " --> " + value);
        return value;
    }

    /**
     * Gets the environment ID.
     *
     * @return the environment ID
     */
    public String getEnvId() {
        LOG.info("Getting environment ID: " + envId);
        return envId;
    }

    /**
     * Gets the base URL for the current environment.
     *
     * @return the base URL
     */
    public String getBaseURL() {
        String baseURL = getEnvironmentSetting(getEnvId() + ".baseURL");
        LOG.info("Getting base URL: " + baseURL);
        return baseURL;
    }

    /**
     * Gets the username for the specified user role in the current environment.
     *
     * @param userRole the user role
     * @return the username
     */
    public String getUsername(String userRole) {
        String username = getEnvironmentSetting(getEnvId() + "." + userRole + ".username");
        LOG.info("Getting username for role: " + userRole + " --> " + username);
        return username;
    }

    /**
     * Gets the password for the specified user role in the current environment.
     *
     * @param userRole the user role
     * @return the password
     */
    public String getPassword(String userRole) {
        String password = getEnvironmentSetting(getEnvId() + "." + userRole + ".password");
        LOG.info("Getting password for role: " + userRole + " --> " + password);
        return password;
    }
}
