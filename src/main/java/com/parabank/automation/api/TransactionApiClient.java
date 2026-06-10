package com.parabank.automation.api;

import com.parabank.automation.utils.LoggingUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TransactionApiClient {

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());

    /**
     * Transfers money between two accounts.
     */
    public Response transfer(String fromAccountId, String toAccountId, double amount) {
        log.info("Transferring ${} from account {} to account {}", amount, fromAccountId, toAccountId);

        Response response = given().queryParam("fromAccountId", fromAccountId)
                                   .queryParam("toAccountId", toAccountId)
                                   .queryParam("amount", amount)
                                   .when()
                                   .post("/transfer");

        log.info("Transfer response status: {}", response.statusCode());
        return response;
    }

    /**
     * Fetches transactions for a given account.
     */
    public Response getTransactions(String accountId) {
        log.info("Fetching transactions for account ID: {}", accountId);

        Response response = given().when()
                                   .get("/accounts/{accountId}/transactions", accountId);

        log.info("Transactions response status: {}", response.statusCode());
        return response;
    }
}
