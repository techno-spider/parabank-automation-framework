package com.parabank.automation.stepdefinitions;

import com.parabank.automation.api.AccountApiClient;
import com.parabank.automation.config.ConfigReader;
import com.parabank.automation.ui.pages.AccountsOverviewPage;
import com.parabank.automation.ui.pages.LoginPage;
import com.parabank.automation.ui.pages.TransferFundsPage;
import com.parabank.automation.utils.LoggingUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class TransferSteps {

    private final LoggingUtil log = LoggingUtil.getLogger(this.getClass());
    private final LoginPage loginPage;
    private final AccountsOverviewPage accountsPage;
    private final TransferFundsPage transferPage;
    private final AccountApiClient accountApiClient;
    private final ConfigReader configReader;
    private final TestContext testContext;

    private String fromAccountId;
    private String toAccountId;
    private double balanceBefore;

    public TransferSteps(LoginPage loginPage,
                         AccountsOverviewPage accountsPage,
                         TransferFundsPage transferPage,
                         AccountApiClient accountApiClient,
                         ConfigReader configReader,
                         TestContext testContext) {
        this.loginPage = loginPage;
        this.accountsPage = accountsPage;
        this.transferPage = transferPage;
        this.accountApiClient = accountApiClient;
        this.configReader = configReader;
        this.testContext = testContext;
    }

    @Given("I am logged in to ParaBank")
    public void loginToParabank() {
        loginPage.open(configReader.get("ui.url"));
        loginPage.enterUsername(configReader.get("credentials.username"));
        loginPage.enterPassword(configReader.get("credentials.password"));
        loginPage.clickLogin();
        log.info("Logged in successfully");
    }

    @When("I note the balance of my first account")
    public void noteBalanceBeforeTransfer() {
        /*fromAccountId = accountsPage.getFirstAccountNumber();
        balanceBefore = accountApiClient.getBalance(fromAccountId);
        log.info("Account: " + fromAccountId + " | Balance before: " + balanceBefore);*/
        long threadId = Thread.currentThread()
                              .threadId();
        String customerId = testContext.getCustomerId();

        // Each thread picks a different account
        List<String> accountIds = accountApiClient.getCustomerAccounts(customerId)
                                                  .xmlPath()
                                                  .getList("accounts.account.id");

        int index = (int) (threadId % accountIds.size());
        fromAccountId = accountIds.get(index);
        toAccountId = accountIds.get((index + 1) % accountIds.size());

        balanceBefore = accountApiClient.getBalance(fromAccountId);
        log.info("Thread: "
                 + threadId
                 + " | From: "
                 + fromAccountId
                 + " | To: "
                 + toAccountId
                 + " | Balance: "
                 + balanceBefore);
    }

    @When("I transfer {string} to another account")
    public void transferToAnotherAccount(String amount) {
        accountsPage.clickTransferFunds();
        toAccountId = getSecondAccountId();
        transferPage.enterAmount(amount);
        transferPage.selectFromAccount(fromAccountId);
        transferPage.selectToAccount(toAccountId);
        transferPage.clickTransfer();
        log.info("Transferring $" + amount + " from " + fromAccountId + " to " + toAccountId);
    }

    @Then("the transfer should be successful")
    public void verifyTransferSuccessful() {
        log.info("Current URL: " + transferPage.getCurrentUrl());
        log.info("Page title: " + transferPage.getPageTitle());
        Assert.assertTrue(transferPage.isTransferComplete(), "Transfer should be complete");
    }

    @Then("my account balance should be reduced by {string}")
    public void verifyBalanceReduced(String amount) {
        double expectedBalance = balanceBefore - Double.parseDouble(amount);
        double actualBalance = accountApiClient.getBalance(fromAccountId);
        log.info("Expected balance: " + expectedBalance + " | Actual: " + actualBalance);
        Assert.assertEquals(actualBalance, expectedBalance, 0.01, "Balance should be reduced by $" + amount);
    }

    private String getSecondAccountId() {
        String firstAccount = fromAccountId;
        String customerId = testContext.getCustomerId();

        return accountApiClient.getCustomerAccounts(customerId)
                               .xmlPath()
                               .getList("accounts.account.id")
                               .stream()
                               .map(Object::toString)
                               .filter(id -> !id.equals(firstAccount))
                               .findFirst()
                               .orElse("12345");
    }
}
