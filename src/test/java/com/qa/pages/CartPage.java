package com.qa.pages;

import org.openqa.selenium.By;

public class CartPage extends BasePage {
    public CartPage(){
        super();
    }

    private final By pageTitle = By.cssSelector((".title"));
    private final By cartItems = By.cssSelector(".cart_item");
    private final By cartItemNames = By.cssSelector((".inventory_item_name"));
    private final By removeButtons = By.cssSelector(("[data-test^='remove']"));
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");

    public int getCartItemCount(){
        return driver.findElements(cartItems).size();

    }

    public String getFirstItemName(){
        return getText(cartItemNames);
    }

    public CartPage removeFirstItem(){
        clickElement(removeButtons);
        return this;
    }

    public CheckoutPage proceedToCheckout(){
        clickElement(checkoutButton);
        return new CheckoutPage();
    }
    public DashboardPage continueShopping(){
        clickElement(continueShoppingButton);
        return new DashboardPage();
    }
    public boolean isLoaded(){
        return isDisplayed(pageTitle);
    }
}
