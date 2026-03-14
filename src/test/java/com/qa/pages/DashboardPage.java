package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class DashboardPage extends BasePage{
    public DashboardPage(){
        super();
    }

    private final By pageTitle = By.cssSelector(".title");
    private final By productItems = By.cssSelector(".inventory_item");
    private final By addToCartButton = By.cssSelector("[data-test^='add-to-cart']");
    private final By cartIcon = By.cssSelector((".shopping_cart_link"));
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By sortDropdown = By.cssSelector("[data-test='product_sort_container']");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public String getPageTitle(){
        return getText(pageTitle);

    }

    public DashboardPage addFirstProductToCart() {
        log.info("Adding first product to cart");
        clickElement(addToCartButton);
        return this;
    }

    public int getProductCount(){
        return driver.findElements(productItems).size();
    }

    public int getCartCount(){
        if(isDisplayed(cartBadge)){
            return Integer.parseInt(getText(cartBadge));
        } else{
            return 0;
        }
    }

    public DashboardPage sortBy(String option){
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText(option);
        return this;
    }

    public CartPage goToCart(){
        clickElement(cartIcon);
        return new CartPage();
    }

    public LoginPage logout(){
        clickElement(menuButton);
        waitForVisible(logoutLink).click();
        return new LoginPage();
    }

    public boolean isLoaded(){
        return isDisplayed(pageTitle);
    }

}
