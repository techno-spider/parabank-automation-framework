package com.parabank.automation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized logging utility for the test automation framework.
 * Wraps SLF4J to provide consistent log formatting across UI and API layers.
 */
public final class LoggingUtil {

    private final Logger logger;

    private LoggingUtil(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Factory method to get a logger for the calling class.
     */
    public static LoggingUtil getLogger(Class<?> clazz) {
        return new LoggingUtil(clazz);
    }

    // ────────────── Standard Log Levels ──────────────

    public void info(String message, Object... args) {
        logger.info(format(message), args);
    }

    public void debug(String message, Object... args) {
        logger.debug(format(message), args);
    }

    public void warn(String message, Object... args) {
        logger.warn(format(message), args);
    }

    public void error(String message, Object... args) {
        logger.error(format(message), args);
    }

    // ────────────── Framework-Specific Helpers ──────────────

    /**
     * Log step start marker.
     */
    public void step(String stepDescription) {
        logger.info(">>>> STEP: {}", stepDescription);
    }

    /**
     * Log step completion marker.
     */
    public void stepComplete(String stepDescription) {
        logger.info("<<<< COMPLETED: {}", stepDescription);
    }

    /**
     * Log a test start marker.
     */
    public void testStart(String testName) {
        logger.info("========================================");
        logger.info("🚀 TEST STARTED: {}", testName);
        logger.info("========================================");
    }

    /**
     * Log a test end marker.
     */
    public void testEnd(String testName, boolean passed) {
        String status = passed ? "✅ PASSED" : "❌ FAILED";
        logger.info("========================================");
        logger.info("{} : {}", status, testName);
        logger.info("========================================");
    }

    /**
     * Log an API request.
     */
    public void apiRequest(String method, String url) {
        logger.info("🌐 API {} → {}", method, url);
    }

    /**
     * Log an API response.
     */
    public void apiResponse(int statusCode, long timeMs) {
        logger.info("📩 Response: {} ({}ms)", statusCode, timeMs);
    }

    // ────────────── Private Helpers ──────────────

    private String format(String message) {
        return message == null ? "" : message;
    }
}
