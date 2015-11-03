package com.cartera.citi.framework.elements;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseElement implements Element{

    protected static final int WAIT_FOR_ELEMENT_IN_SECONDS = 30;
    protected WebElement webElement;
    protected WebDriver driver;
    protected String selector;

    public BaseElement(WebElement webElement) {
        this.webElement = webElement;
        this.selector = CustomFieldDecorator.getSelector();
        driver = Context.getTestSession().getDriver();
    }

    @Override
    public boolean click() {
        webElement.click();
        return true;
    }

    @Override
    public boolean isDisplayed() {
        return webElement.isDisplayed();
    }

    @Override
    public boolean isSelected() {
        return webElement.isSelected();
    }

    @Override
    public void setText(String text) {
        Logger.logStep("Writing text \'" + text + "\'");
        webElement.sendKeys(text);
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public void clear() {
        webElement.clear();
    }

    @Override
    public void clearAndWriteText(String text) {
        Logger.logStep("Write text \'" + text + "\'");
        click();
        clear();
        setText(text);
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public Point getPoint() {
        WebElement element = this.getWebElement();
        if(element == null){
            Logger.logHuman(Logger.Level.ERROR, "Unable to locate non-existent element ", true);
            return null;

        }
        return element.getLocation();
    }

    public void scroll() {
        ((JavascriptExecutor) driver).executeScript("scroll" + getPoint().toString() + "");
    }

    public void highlightWebElement(WebElement webElement) {
        for (int i = 0; i < 4; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "color: solid red; border: 4px solid green;");
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", webElement, "");
        }

    }

    public void waitForElement() {
        Logger.logTechnical("Start waiting for " + selector);
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_IN_SECONDS);
        wait.withMessage("Wait for was completed with no success").until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                return BaseElement.this.getWebElement();
            }
        });
    }

    public void waitForElementPresent() {
        Logger.logTechnical("Start waiting for " + selector);
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_IN_SECONDS);
        wait.withMessage("Wait for element presence on page was completed with no success").until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return BaseElement.this.getWebElement().isDisplayed();
            }
        });
    }

    public void waitForElementNotExist() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_IN_SECONDS);
        Logger.logTechnical("Start waiting for " + selector + " not in DOM");
        wait.withMessage("Wait for " + selector + " disappearance was completed with no success").until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return BaseElement.this.getWebElement() == null;
            }
        });
    }

    public void waitForElementEnabled() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_ELEMENT_IN_SECONDS);
        Logger.logTechnical("Start waiting for " + selector + " gets enabled");
        wait.withMessage("Wait for " + selector + " element gets enabled was completed with no success").until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return BaseElement.this.getWebElement().isEnabled();
            }
        });
    }

    public void doubleClick(){
        Actions action = new Actions(driver);
        action.moveToElement(webElement).doubleClick().perform();
    }
}