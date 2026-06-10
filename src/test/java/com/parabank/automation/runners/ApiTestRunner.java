package com.parabank.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", glue = "com.parabank.automation",
                 plugin = {"pretty", "html:target/cucumber-reports/api/cucumber.html",
                           "json:target/cucumber-reports/api/cucumber.json",
                           "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}, tags = "@api")
public class ApiTestRunner extends AbstractTestNGCucumberTests {
}
