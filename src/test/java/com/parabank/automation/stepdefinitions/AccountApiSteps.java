package com.parabank.automation.stepdefinitions;

import com.parabank.automation.api.AccountApiClient;
import com.parabank.automation.api.AuthApiClient;
import com.parabank.automation.config.ConfigReader;
import com.parabank.automation.utils.LoggingUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

@Feature("ParaBank Account API")
public class AccountApiSteps {

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());
    private final AuthApiClient authApiClient;
    private final AccountApiClient accountApiClient;
    private final ConfigReader configReader;

    private String customerId;
    private Response response;

    public AccountApiSteps(AuthApiClient authApiClient, AccountApiClient accountApiClient, ConfigReader configReader) {
        this.authApiClient = authApiClient;
        this.accountApiClient = accountApiClient;
        this.configReader = configReader;
    }

    @Given("I am authenticated via API")
    public void authenticateViaApi() {
        RestAssured.baseURI = configReader.get("api.baseurl");
        String username = configReader.get("credentials.username");
        String password = configReader.get("credentials.password");
        customerId = authApiClient.login(username, password);
        log.info("API authenticated. Customer ID: " + customerId);
    }

    @When("I fetch my account details via API")
    public void fetchAccountDetails() {
        response = accountApiClient.getCustomerAccounts(customerId);
        log.info("Account API response status: " + response.statusCode());
    }

    @Then("the API response status should be {int}")
    public void verifyResponseStatus(int expectedStatus) {
        Assert.assertEquals(response.statusCode(),
                            expectedStatus,
                            "Expected status " + expectedStatus + " but got " + response.statusCode());
    }

    @Then("the response should contain account information")
    public void verifyAccountInformation() {
        String responseBody = response.getBody()
                                      .asString();
        Assert.assertTrue(responseBody.contains("account"), "Response should contain account information");
        log.info("Account information verified in response");
    }
}
