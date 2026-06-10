package com.parabank.automation.core.exception;

import lombok.Getter;

/**
 * Thrown when API calls fail unexpectedly.
 */
@Getter
public class ApiException extends FrameworkException {

    private final int statusCode;

    public ApiException(String message, int statusCode) {
        super("API error [HTTP " + statusCode + "]: " + message);
        this.statusCode = statusCode;
    }

    public ApiException(String message, int statusCode, Throwable cause) {
        super("API error [HTTP " + statusCode + "]: " + message, cause);
        this.statusCode = statusCode;
    }
}
