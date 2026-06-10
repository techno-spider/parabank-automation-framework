package com.parabank.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", glue = "com.parabank.automation",
                 plugin = {"pretty", "html:target/cucumber-reports/cucumber.html",
                           "json:target/cucumber-reports/cucumber.json",
                           "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})
public class TestRunner extends AbstractTestNGCucumberTests {
}
