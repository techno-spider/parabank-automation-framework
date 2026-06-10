package com.parabank.automation.listeners;

import com.parabank.automation.utils.LoggingUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount >= MAX_RETRY_COUNT) {
            return false;
        }

        // Don't retry assertion errors
        if (iTestResult.getThrowable() instanceof AssertionError) {
            LoggingUtil.getLogger(this.getClass())
                       .info("Skipping retry for assertion error: " + iTestResult.getName());
            return false;
        }

        retryCount++;

        String testName = iTestResult.getName();
        Object[] parameters = iTestResult.getParameters();
        if (parameters != null && parameters.length > 0) {
            testName = parameters[0].toString();
        }

        LoggingUtil.getLogger(this.getClass())
                   .info("Retrying test: " + testName + " | Attempts: " + retryCount + " | Status: Failed");
        return true;
    }
}
