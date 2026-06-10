package com.parabank.automation.api;

import com.parabank.automation.config.ConfigReader;
import com.parabank.automation.core.exception.ApiException;
import com.parabank.automation.utils.LoggingUtil;
import io.restassured.response.Response;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

@Component
@Getter
public class AuthApiClient {

    private static final Pattern CUSTOMER_ID_PATTERN = Pattern.compile("<id>(\\d+)</id>");

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());
    private final ConfigReader configReader;
    private String sessionToken;

    public AuthApiClient(ConfigReader configReader) {this.configReader = configReader;}


    /**
     * Logs in and retrieves the numeric customer ID.
     * ParaBank returns an XML response on successful login, e.g.:
     * {@code <customer><id>12212</id>...}</customer>
     * This method extracts and returns just the numeric ID.
     */
    public String login(String username, String password) {
        String baseUrl = configReader.get("api.baseurl");
        log.info("Logging in via API with username: {}", username);
        Response response = given().baseUri(baseUrl)
                                   .when()
                                   .get("/login/{username}/{password}", username, password);
        if (response.statusCode() == 200) {
            String responseBody = response.getBody()
                                          .asString();
            String customerId = extractCustomerId(responseBody);
            sessionToken = customerId;
            log.info("Login successful. Customer ID: {}", customerId);
            return customerId;
        } else {
            log.error("Login failed. Status: {}, Body: {}",
                      response.statusCode(),
                      response.getBody()
                              .asString());
            throw new ApiException("Login failed with status: ", response.statusCode());
        }
    }

    /**
     * Extracts the numeric customer ID from the ParaBank XML response.
     */
    private String extractCustomerId(String xmlResponse) {
        Matcher matcher = CUSTOMER_ID_PATTERN.matcher(xmlResponse);
        if (matcher.find()) {
            return matcher.group(1);
        }
        log.warn("Could not extract customer ID from response: {}", xmlResponse);
        throw new ApiException("Failed to parse customer ID from login response", 200);
    }
}

