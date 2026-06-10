package com.parabank.automation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ParabankTestFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParabankTestFrameworkApplication.class, args);
    }
}
