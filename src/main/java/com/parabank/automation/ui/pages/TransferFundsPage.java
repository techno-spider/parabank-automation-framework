package com.parabank.automation.ui.pages;

import com.parabank.automation.config.ParabankProperties;
import com.parabank.automation.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TransferFundsPage extends BasePage {

    private final By amountInput = By.id("amount");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By toAccountDropdown = By.id("toAccountId");
    private final By transferButton = By.xpath("//input[@value='Transfer']");
    private final By successMessage = By.xpath("//h1[contains(text(),'Transfer Complete')]");
    private final By errorMessage = By.xpath("//span[@class='error']");

    public TransferFundsPage(ParabankProperties props) {
        super(props.getTimeout());
    }

    public void enterAmount(String amount) {
        type(amountInput, amount, "Amount Field");
    }

    public void selectFromAccount(String accountId) {
        selectFromDropdown(fromAccountDropdown, accountId);
    }

    public void selectToAccount(String accountId) {
        selectFromDropdown(toAccountDropdown, accountId);
    }

    public void clickTransfer() {
        click(transferButton, "Transfer button");
    }

    public boolean isTransferComplete() {
        /*return isDisplayed(successMessage, "Transfer complete message");*/
        try {
            getWaitUtil().waitForVisibility(successMessage);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String getErrorMessage() {
        return getText(errorMessage, "Error message");
    }

    private void selectFromDropdown(By dropdown, String value) {
        DriverManager.getDriver()
                     .findElement(dropdown)
                     .sendKeys(value);
    }
}
