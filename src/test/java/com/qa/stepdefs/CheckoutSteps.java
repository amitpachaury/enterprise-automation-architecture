package com.qa.stepdefs;

import com.github.javafaker.Faker;
import com.qa.pages.CartPage;
import com.qa.pages.CheckoutPage;
import com.qa.pages.DashboardPage;
import com.qa.pages.LoginPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

public class CheckoutSteps {

    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private final ScenarioContext context;

    public CheckoutSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("user adds first product to cart")
    public void userAddsFirstProductToCart() {
        context.dashboardPage.addFirstProductToCart();

    }

    @And("user goes to cart")
    public void userGoesToCart() {
        cartPage = context.dashboardPage.goToCart();
    }

    @And("user proceeds to checkout")
    public void userProceedsToCheckout() {
        checkoutPage = cartPage.proceedToCheckout();
    }

    @And("user fills shipping info with random data")
    public void userFillsShippingInfoWithRandomData() {
        Faker faker = new Faker();
        checkoutPage.fillShippingInfo(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().zipCode()
        );
    }

    @And("user places the order")
    public void userPlacesTheOrder() {
        checkoutPage.clickContinue().clickFinish();
    }

    @Then("order confirmation is displayed")
    public void orderConfirmationIsDisplayed() {
        Assert.assertTrue(checkoutPage.isOrderConfirmed());
        Assert.assertEquals(checkoutPage.getConfirmationHeader(), "Thank you for your order!");
    }

    @And("user submits shipping info with empty firstname")
    public void userSubmitsShippingInfoWithEmptyFirstname() {
        checkoutPage.fillShippingInfo("", "Pachaury", "1233").clickContinue();
    }

    @Then("error message is displayed on checkout page")
    public void errorMessageIsDisplayedOnCheckoutPage() {
        Assert.assertFalse(checkoutPage.getErrorMessage().isEmpty(), "Error message is displayed on checkout page");

    }

    @Then("cart count should be {int}")
    public void cartCountShouldBe(int count) {
        Assert.assertEquals(context.dashboardPage.getCartCount(), count);
    }

    @And("user fills shipping info with {string} {string} and {string}")
    public void userFillsShippingInfoWithAnd(String firstName, String lastName, String postalCode) {
        checkoutPage.fillShippingInfo(
                firstName,
                lastName,
                postalCode).clickContinue();
        context.lastErrorMessage = checkoutPage.getErrorMessage();  // store it


    }

    @Then("checkout error message should contain {string}")
    public void error_message_should_contain(String expectedError) {
        Assert.assertTrue(
                checkoutPage.getErrorMessage().contains(expectedError),
                "Expected: " + expectedError
        );
    }

}
