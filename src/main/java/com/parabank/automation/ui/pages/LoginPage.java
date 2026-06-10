package com.parabank.automation.ui.pages;

import com.parabank.automation.config.ParabankProperties;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LoginPage extends BasePage {

    // Locators
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.xpath("//input[@value='Log In']");
    private final By errorMessage = By.xpath("//p[@class='error']");

    public LoginPage(ParabankProperties props) {
        super(props.getTimeout());
    }

    public void open(String url) {
        navigateTo(url);
    }

    public void enterUsername(String username) {
        type(usernameInput, username, "Username field");
    }

    public void enterPassword(String password) {
        type(passwordInput, password, "Password field");
    }

    public void clickLogin() {
        click(loginButton, "Login button");
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage, "Error message");
    }

    public String getErrorMessage() {
        return getText(errorMessage, "Error message");
    }
}
