package com.qa.stepdefs;

import com.qa.api.endpoints.UserApiEndpoints;
import com.qa.models.User;
import com.qa.pages.DashboardPage;
import com.qa.pages.LoginPage;
import com.qa.utils.FixtureLoader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class HybridSteps {
    private static final Logger log = LogManager.getLogger(HybridSteps.class);
    private final ScenarioContext context;
    private DashboardPage dashboardPage;
    private Response response;

    public HybridSteps(ScenarioContext context) {
        this.context = context;

    }

    @Given("a new user is created via API")
    public void aNewUserIsCreatedViaAPI() {
        User user = FixtureLoader.loadAs("users/create-user.json", User.class);
        response = UserApiEndpoints.createUser(user);
        Assert.assertEquals(response.getStatusCode(), 201);
        context.createdUserId = response.jsonPath().getString("id");
        log.info("Created user with id " + context.createdUserId);
    }

    @When("I login to the application with valid credentials")
    public void iLoginToTheApplicationWithValidCredentials() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigateTo("https://www.saucedemo.com");
        context.dashboardPage = loginPage.login("standard_user", "secret_sauce");

    }

    @Then("the dashboard should be displayed successfully")
    public void theDashboardShouldBeDisplayedSuccessfully() {
        Assert.assertTrue(context.dashboardPage.isLoaded());
        Assert.assertEquals(context.dashboardPage.getPageTitle(), "Products");
        log.info("Dashboard page is displayed successfully");
    }

    @And("I add a product to cart via UI")
    public void iAddAProductToCartViaUI() {
        context.dashboardPage.addFirstProductToCart();
    }

    @Then("cart count should show {int} via UI")
    public void cartCountShouldShowViaUI(int count) {
        Assert.assertEquals(context.dashboardPage.getCartCount(), count);
    }

    @And("user data should be retrievable via API")
    public void userDataShouldBeRetrievableViaAPI() {
        int id = Integer.parseInt(context.createdUserId);
        Response userResponse = UserApiEndpoints.getUser(2);
        Assert.assertEquals(userResponse.getStatusCode(), 200);
        log.info("User {} successfully retrieved via API", id);
    }
}
