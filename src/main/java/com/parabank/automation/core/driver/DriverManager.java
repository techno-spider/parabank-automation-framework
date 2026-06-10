package com.parabank.automation.core.driver;

import com.parabank.automation.utils.LoggingUtil;
import org.openqa.selenium.WebDriver;

/**
 * Singleton manager that holds the WebDriver instance for the current thread.
 * Supports ThreadLocal for parallel execution (coming later).
 */
public final class DriverManager {

    private static final LoggingUtil log = LoggingUtil.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {}

    /**
     * Initializes the WebDriver for the current thread.
     */
    public static void initDriver(BrowserType browserType, boolean headless, int implicitWait, int pageLoadTimeout) {
        if (driverThreadLocal.get() != null) {
            log.debug("Driver already exists. Quitting existing driver first.");
            quitDriver();
        }
        WebDriver driver = DriverFactory.createDriver(browserType, headless, implicitWait, pageLoadTimeout);
        driverThreadLocal.set(driver);
        log.debug("Driver initialized for thread: {}",
                  Thread.currentThread()
                        .getName());
    }

    /**
     * Returns the WebDriver instance for the current thread.
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver not initialized. Call DriverManager.initDriver() first.");
        }
        return driver;
    }

    /**
     * Quits the WebDriver and removes it from the current thread.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                log.debug("Driver quit successfully for thread: {}",
                          Thread.currentThread()
                                .getName());
            } catch (Exception e) {
                log.warn("Error while quitting driver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Checks if a driver is active for the current thread.
     */
    public static boolean isDriverActive() {
        return driverThreadLocal.get() != null;
    }
}
