package com.parabank.automation.stepdefinitions;

import com.parabank.automation.api.AuthApiClient;
import com.parabank.automation.config.ConfigReader;
import com.parabank.automation.config.ParabankProperties;
import com.parabank.automation.core.driver.BrowserType;
import com.parabank.automation.core.driver.DriverManager;
import com.parabank.automation.utils.LoggingUtil;
import com.parabank.automation.utils.WaitUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class Hooks {

    private static final LoggingUtil log = LoggingUtil.getLogger(Hooks.class);
    private static boolean isApiAuthenticated = false;

    private final ParabankProperties props;
    private final ConfigReader configReader;
    private final AuthApiClient authApiClient;
    private final TestContext testContext;

    public Hooks(ParabankProperties props, ConfigReader configReader, AuthApiClient authApiClient, TestContext testContext) {
        this.props = props;
        this.configReader = configReader;
        this.authApiClient = authApiClient;
        this.testContext = testContext;
    }


    @Before(order = 0)
    public void setupApi() {
        if (!isApiAuthenticated) {
            try {
                log.info("=== Setting up API authentication ===");
                String customerId = authApiClient.login(configReader.get("credentials.username"),
                                                        configReader.get("credentials.password"));
                testContext.setCustomerId(customerId);
                isApiAuthenticated = true;
                log.info("API authenticated. Customer ID: " + customerId);
            } catch (Exception e) {
                log.warn("API authentication failed: " + e.getMessage());
                log.warn("Continuing without API authentication");
                isApiAuthenticated = false;
            }
        }
    }

    @Before(order = 1, value = "@ui")
    public void setupUi() {
        log.info("=== Setting up WebDriver ===");
        BrowserType browserType = BrowserType.fromString(props.getBrowser());
        DriverManager.initDriver(browserType, props.isHeadless(), props.getTimeout(), props.getTimeout());
        log.info("WebDriver initialized for UI test");
    }

    @After(order = 1, value = "@ui")
    public void tearDownUi(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("Scenario failed: {}", scenario.getName());
            try {
                WaitUtil waitUtil = new WaitUtil(30);
                waitUtil.waitForPageLoad();
                Thread.sleep(2000);
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                // Attach to Cucumber report
                //scenario.attach(screenshot, "image/png", "Failure Screenshot");
                // Attach to Allure report
                Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");
                log.info("Screenshot captured and attached. Size: " + screenshot.length + " bytes");
            } catch (Exception e) {
                log.warn("Could not capture screenshot: {}", e.getMessage());
            }
        }
        log.info("=== Quitting WebDriver ===");
        DriverManager.quitDriver();
    }
}
