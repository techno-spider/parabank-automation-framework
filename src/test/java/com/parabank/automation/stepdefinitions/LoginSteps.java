package com.parabank.automation.stepdefinitions;

import com.parabank.automation.config.ConfigReader;
import com.parabank.automation.config.ParabankProperties;
import com.parabank.automation.ui.pages.LoginPage;
import com.parabank.automation.utils.LoggingUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;

@Feature("ParaBank Login")
public class LoginSteps {

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());
    private final LoginPage loginPage;
    private final ParabankProperties props;
    private final ConfigReader configReader;

    public LoginSteps(LoginPage loginPage, ParabankProperties props, ConfigReader configReader) {
        this.loginPage = loginPage;
        this.props = props;
        this.configReader = configReader;
    }

    @Given("the ParaBank application is running")
    public void openApp() {
        loginPage.open(props.getUi()
                            .getUrl());
    }

    @Story("Valid Login")
    @When("I enter valid credentials")
    public void enterValidCredentials() {
        String username = configReader.get("credentials.username");
        String password = configReader.get("credentials.password");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @Story("Invalid Login")
    @When("I enter invalid credentials")
    public void enterInvalidCredentials() {
        String username = configReader.get("credentials.invalid.username");
        String password = configReader.get("credentials.invalid.password");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void clickLogin() {
        loginPage.clickLogin();
    }

    @Story("Valid Login")
    @Then("I should be redirected to the account overview page")
    public void verifySuccessfulLogin() {
        log.info("Current URL after login: " + loginPage.getCurrentUrl());
        Assert.assertTrue(loginPage.getCurrentUrl()
                                   .contains("overview"), "Expected to be on accounts overview page");
    }

    @Story("Invalid Login")
    @Then("I should see an error message {string}")
    public void verifyErrorMessage(String expectedMessage) {
        log.info("Error message should be displayed");
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage);
    }
}