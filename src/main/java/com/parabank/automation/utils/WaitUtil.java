package com.parabank.automation.utils;

import com.parabank.automation.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

/**
 * Centralized wait utility wrapping Selenium's WebDriverWait.
 * All waits flow through here for consistency.
 */
public final class WaitUtil {
    private final WebDriverWait wait;

    public WaitUtil(int explicitWaitSeconds) {
        this.wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(explicitWaitSeconds));
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    public boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public void waitForPageLoad() {
        wait.until(webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript(
                "return document.readyState"), "complete"));
    }
}
