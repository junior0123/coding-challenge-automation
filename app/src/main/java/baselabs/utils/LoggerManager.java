package baselabs.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

/**
 * Singleton class to manage logging using Log4j2.
 */
public class LoggerManager {
    private Logger logger;
    private static LoggerManager instance;

    /**
     * Private constructor to initialize the logger.
     */
    private LoggerManager() {
        initialize();
    }

    /**
     * Returns the singleton instance of LoggerManager.
     *
     * @return the singleton instance of LoggerManager
     */
    public static LoggerManager getInstance() {
        if (instance == null) {
            instance = new LoggerManager();
        }
        return instance;
    }

    /**
     * Initializes the logger with the configuration file.
     */
    private void initialize() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "log4j2.properties");
        context.setConfigLocation(file.toURI());
        logger = LogManager.getLogger(LogManager.class);
    }

    /**
     * Logs a message at the specified level.
     *
     * @param level   the logging level
     * @param message the message to log
     */
    private void log(Level level, String message) {
        getInstance().logger.log(level, message);
    }

    /**
     * Logs a debug message.
     *
     * @param message the message to log
     */
    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    /**
     * Logs an error message.
     *
     * @param message the message to log
     */
    public void error(String message) {
        log(Level.ERROR, message);
    }

    /**
     * Logs an info message.
     *
     * @param message the message to log
     */
    public void info(String message) {
        log(Level.INFO, message);
    }

    /**
     * Logs a warning message.
     *
     * @param message the message to log
     */
    public void warn(String message) {
        log(Level.WARN, message);
    }
}
