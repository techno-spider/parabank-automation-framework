package com.parabank.automation.core.exception;

/**
 * Thrown when framework configuration is invalid or missing.
 */
public class ConfigurationException extends FrameworkException {
    public ConfigurationException(String property, String expected, String actual) {
        super(String.format("Configuration error for '%s': expected '%s' but got '%s'", property, expected, actual));
    }
}
