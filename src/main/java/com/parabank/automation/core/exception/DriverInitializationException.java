package com.parabank.automation.core.exception;

/**
 * Thrown when WebDriver initialization fails.
 */
public class DriverInitializationException extends FrameworkException {
    public DriverInitializationException(String browser, Throwable cause) {
        super("Failed to initialize WebDriver for browser: " + browser + ". " + cause.getMessage(), cause);
    }
}
