package com.parabank.automation.stepdefinitions;

import com.parabank.automation.config.ParabankProperties;
import com.parabank.automation.ui.pages.LoginPage;
import com.parabank.automation.utils.LoggingUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class ScreenshotSteps {

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());
    private final LoginPage loginPage;
    private final ParabankProperties props;

    public ScreenshotSteps(LoginPage loginPage, ParabankProperties props) {
        this.loginPage = loginPage;
        this.props = props;
    }

    @Given("the ParaBank application is running for screenshot test")
    public void openApp() {
        loginPage.open(props.getUi()
                            .getUrl());
        log.info("Logged in.............");
    }

    @Then("I force a test failure to capture screenshot")
    public void forceFailureForScreenshot() {
        log.info("This test is designed to fail — verifying screenshot capture");
        Assert.fail("Intentionally failing to capture screenshot in Allure report");
    }
}
