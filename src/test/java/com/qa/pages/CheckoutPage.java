package com.qa.pages;

import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {
    public CheckoutPage(){
        super();
    }
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");

    private final By finishButton = By.id("finish");
    private final By confirmationHeader = By.cssSelector(".complete-header");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By itemTotal = By.cssSelector(".summary_subtotal_label");
    private final By orderTotal = By.cssSelector(".summary_total_label");

    public CheckoutPage enterFirstName(String firstName){
        typeToElement(firstNameField, firstName);
        return this;
    }
    public CheckoutPage enterLastName(String lastName){
        typeToElement(lastNameField, lastName);
        return this;
    }
    public CheckoutPage enterPostalCode(String code){
        typeToElement(postalCodeField, code);
        return this;
    }
    public CheckoutPage fillShippingInfo(String firstName, String lastName, String code){
        enterFirstName(firstName).enterLastName(lastName).enterPostalCode(code);
        return this;
    }
    public CheckoutPage clickContinue(){
        clickElement(continueButton);
        return this;
    }
    public CheckoutPage clickFinish(){
        clickElement(finishButton);
        return this;
    }

    public CheckoutPage placeOrder(String firstName, String lastName, String code){
        fillShippingInfo(firstName, lastName, code).clickContinue().clickFinish();
        return this;
    }
    public String getConfirmationHeader(){
        return getText(confirmationHeader);
    }
    public boolean isOrderConfirmed(){
        return isDisplayed(confirmationHeader);
    }
    public String getErrorMessage(){
        return getText(errorMessage);
    }
    public String getOrderTotal(){
        return getText(orderTotal);
    }


}
