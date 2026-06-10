package com.parabank.automation.core.exception;

/**
 * Base exception for all framework-specific errors.
 * Extends RuntimeException so tests fail fast without checked exception boilerplate.
 */
public class FrameworkException extends RuntimeException {
    public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
