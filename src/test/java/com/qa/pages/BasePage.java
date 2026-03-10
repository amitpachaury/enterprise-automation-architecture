package com.qa.pages;

import com.qa.config.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected Logger log = LogManager.getLogger(this.getClass());
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage() {
        driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    protected void clickElement(By locator) {
        try {
            waitForClickable(locator).click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted, trying JS click");
            jsClick(locator);
        }
    }

    protected void typeToElement(By locator, String text){
        WebElement element  = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);

    }

    protected String getText(By locator){
        return  waitForVisible(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected WebElement waitForVisible(By locator){
          return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    }
    protected WebElement waitForClickable(By locator){
        return wait.until(ExpectedConditions.elementToBeClickable(locator));

    }
    protected void waitForInvisible(By locator){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

    }

    private void jsClick(By locator){
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    protected void scrollIntoView(By locator){
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'})", element);
    }

    public String getPageTitle(){
        return driver.getTitle();
    }
    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

}
