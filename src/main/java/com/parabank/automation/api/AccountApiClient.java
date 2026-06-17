package com.parabank.automation.api;

import com.parabank.automation.config.ConfigReader;
import com.parabank.automation.utils.LoggingUtil;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class AccountApiClient {

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());
    private final ConfigReader configReader;

    public AccountApiClient(ConfigReader configReader) {this.configReader = configReader;}

    /**
     * Fetches all accounts for a given customer.
     */
    public Response getCustomerAccounts(String customerId) {
        String baseUrl = configReader.get("api.baseurl");
        log.info("Fetching accounts for customer ID: {}", customerId);

        Response response = given().baseUri(baseUrl)
                                   .when()
                                   .get("/customers/{customerId}/accounts", customerId);

        log.info("Accounts response status: {}", response.statusCode());
        return response;
    }

    /**
     * Fetches a specific account by ID.
     */
    public Response getAccountById(String accountId) {
        String baseUrl = configReader.get("api.baseurl");
        log.info("Fetching account with ID: {}", accountId);

        Response response = given().baseUri(baseUrl)
                                   .when()
                                   .get("/accounts/{accountId}", accountId);

        log.info("Account response status: {}", response.statusCode());
        return response;
    }

    /**
     * Gets the balance of a specific account.
     */
    public double getBalance(String accountId) {
        Response response = getAccountById(accountId);
        /*return response.jsonPath()
                       .getDouble("balance");*/
        return Double.parseDouble(response.xmlPath()
                                          .getString("account.balance"));
    }
}
