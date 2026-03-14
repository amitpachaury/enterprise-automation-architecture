package com.qa.stepdefs;

import com.qa.pages.DashboardPage;
import com.qa.pages.LoginPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class LoginSteps {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private final ScenarioContext context;


    public LoginSteps(ScenarioContext context) {
        this.context = context;
    }


    @Given("user is on the login page")
    public void user_is_on_the_login_page() {
        loginPage  = new LoginPage();
        loginPage.navigateTo("https://www.saucedemo.com");
    }

    @Given("user is logged in")
    public void user_is_logged_in() {
        loginPage = new LoginPage();
        loginPage.navigateTo("https://www.saucedemo.com");
        context.dashboardPage = loginPage.login("standard_user", "secret_sauce");
    }
    @When("user enters a valid username")
    public void user_enters_a_valid_username() {
        loginPage.enterUsername("standard_user");
    }
    @When("user enters a valid username for locked user")
    public void user_enters_username_for_locked_user() {
        loginPage.enterUsername("locked_out_user");
    }
    @When("user enters a empty username")
    public void user_enters_empty_username() {
        loginPage.enterUsername("");
    }
    @When("user enters a valid password")
    public void user_enters_a_valid_password() {
        loginPage.enterPassword("secret_sauce");
    }
    @When("user clicks on Login button")
    public void user_clicks_on_login_button() {
         dashboardPage = loginPage.clickLoginButton();
    }
    @Then("dashboard page is displayed")
    public void dashboard_page_is_displayed() {
        Assert.assertTrue(dashboardPage.isLoaded());
    }
    @Then("error message is displayed")
    public void errorMessageIsDisplayed() {
        String error = loginPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(), "Error message should be displayed");
    }
    @Then("error message contains {string}")
    public void errorMessageContains(String expectedMessage) {
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedMessage));
    }

    @When("user enters a invalid {string}")
    public void user_enters_invalid_username(String username) {
        loginPage.enterUsername(username);
    }

    @When("user enters a invalid password {string}")
    public void user_enters_invalid_password(String password) {
        loginPage.enterPassword(password);
    }

    @Then("error message should contain {string}")
    public void error_message_is_displayed(String expectedError) {
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains(expectedError));
    }
}
