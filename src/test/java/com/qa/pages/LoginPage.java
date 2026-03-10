package com.qa.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    public LoginPage(){
        super();
    }

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage enterUsername(String username){
        log.info("Entering Username");
        typeToElement(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password){
        log.info("Entering Password");
        typeToElement(passwordField, password);
        return this;
    }

    public DashboardPage clickLoginButton(){
        clickElement(loginButton);
        return new DashboardPage();
    }

    public DashboardPage login(String username, String password){
        return enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();

    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }

    public boolean isLoginPageDisplayed(){
        return isDisplayed(loginButton);

    }


}
