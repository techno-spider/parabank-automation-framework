package com.parabank.automation.ui.pages;

import com.parabank.automation.core.driver.DriverManager;
import com.parabank.automation.utils.LoggingUtil;
import com.parabank.automation.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {

    protected final LoggingUtil log;
    private final int explicitWaitSeconds;

    public BasePage(int explicitWaitSeconds) {
        this.log = LoggingUtil.getLogger(this.getClass());
        this.explicitWaitSeconds = explicitWaitSeconds;
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected WaitUtil getWaitUtil() {
        return new WaitUtil(explicitWaitSeconds);
    }

    protected void click(By locator, String elementName) {
        log.debug("Clicking on: {}", elementName);
        getWaitUtil().waitForClickable(locator)
                     .click();
    }

    protected void type(By locator, String text, String elementName) {
        log.debug("Typing into {}: '{}'", elementName, text);
        WebElement element = getWaitUtil().waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator, String elementName) {
        log.debug("Getting text from: {}", elementName);
        String text = getWaitUtil().waitForVisibility(locator)
                                   .getText();
        log.debug("Text retrieved: {}", text);
        return text;
    }

    protected boolean isDisplayed(By locator, String elementName) {
        try {
            boolean displayed = getDriver().findElement(locator)
                                           .isDisplayed();
            log.debug("Element '{}' displayed: {}", elementName, displayed);
            return displayed;
        } catch (Exception e) {
            log.debug("Element '{}' not found on page", elementName);
            return false;
        }
    }

    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        getDriver().get(url);
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    protected String getPageTitle() {
        return getDriver().getTitle();
    }
}
