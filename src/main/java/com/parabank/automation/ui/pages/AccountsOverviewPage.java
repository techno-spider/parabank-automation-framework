package com.parabank.automation.ui.pages;

import com.parabank.automation.config.ParabankProperties;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AccountsOverviewPage extends BasePage {

    private final By accountNumber = By.xpath("//table[@id='accountTable']//tbody/tr[1]/td[1]/a");
    private final By balance = By.xpath("//table[@id='accountTable']//tbody/tr[1]/td[2]");
    private final By totalBalance = By.xpath("//b[contains(text(),'Total')]");
    private final By transferFundsLink = By.linkText("Transfer Funds");


    public AccountsOverviewPage(ParabankProperties props) {
        super(props.getTimeout());
    }

    public String getFirstAccountNumber() {
        return getText(accountNumber, "First account number");
    }

    public String getFirstAccountBalance() {
        return getText(balance, "First account balance");
    }

    public void clickTransferFunds() {
        click(transferFundsLink, "Transfer Funds link");
    }
}
