package com.parabank.automation.runners;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = com.parabank.automation.ParabankTestFrameworkApplication.class)
public class CucumberSpringConfiguration {
}
