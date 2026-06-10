package com.parabank.automation.stepdefinitions;

import com.parabank.automation.config.ConfigReader;
import com.parabank.automation.config.WireMockStubs;
import com.parabank.automation.utils.LoggingUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class WireMockSteps {

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());
    private final WireMockStubs wireMockStubs;
    private final ConfigReader configReader;

    private Response response;

    public WireMockSteps(WireMockStubs wireMockStubs, ConfigReader config) {
        this.wireMockStubs = wireMockStubs;
        this.configReader = config;
    }

    @Given("the external KYC service is available")
    public void setupKycStub() {
        String customerId = configReader.get("credentials.username");
        wireMockStubs.stubKycVerificationSuccess(customerId);
        log.info("KYC stub set up for customer: " + customerId);
    }

    @When("I call the KYC verification service")
    public void callKycService() {
        response = given().baseUri("http://localhost:8089")
                          .when()
                          .get("/kyc/verify/" + configReader.get("credentials.username"));
        log.info("KYC service called. Status: " + response.statusCode());
    }

    @Then("the KYC response status should be {int}")
    public void verifyKycStatus(int expectedStatus) {
        assertEquals(response.statusCode(), expectedStatus);
    }

    @Then("the KYC response should contain {string}")
    public void verifyKycResponse(String expectedText) {
        String responseBody = response.getBody()
                                      .asString();
        assertEquals(response.jsonPath()
                             .getString("status"), expectedText);
    }
}
