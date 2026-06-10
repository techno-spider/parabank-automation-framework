package com.parabank.automation.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RestAssuredConfig {

    private final ParabankProperties properties;

    public RestAssuredConfig(ParabankProperties properties) {
        this.properties = properties;
        log.info("RestAssured base URL configured: {}",
                 properties.getApi()
                           .getBaseurl());
    }

    @Bean
    public RequestSpecification requestSpecification() {
        String baseUrl = properties.getApi()
                                   .getBaseurl();
        log.info("Configuring RestAssured with base URL: {}", baseUrl);

        RequestSpecification spec = new RequestSpecBuilder().setBaseUri(baseUrl)
                                                            .addFilter(new RequestLoggingFilter())
                                                            .addFilter(new ResponseLoggingFilter())
                                                            .build();

        RestAssured.requestSpecification = spec;
        return spec;
    }
}
