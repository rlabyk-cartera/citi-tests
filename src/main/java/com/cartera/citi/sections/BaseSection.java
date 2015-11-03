package com.cartera.citi.sections;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.BaseElement;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Roman_Labyk on 10/1/2015.
 */
public abstract class BaseSection {

    protected static final int WAIT_FOR_SECTION_IN_SECONDS = 30;

    protected WebDriver driver;
    private WebElement webElement;
    private String selector;

    public BaseSection() {
    }

    public BaseSection(WebElement webElement) {
        this.webElement = webElement;
        this.driver = Context.getTestSession().getDriver();
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void waitForElement() {
        Logger.logTechnical("Start waiting for section..");
        WebDriverWait wait = new WebDriverWait(driver, WAIT_FOR_SECTION_IN_SECONDS);
        wait.withMessage("Wait for was completed with no success").until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                return getWebElement();
            }
        });
    }

    public abstract String getSectionName();
    public abstract Boolean isAvailable();
}