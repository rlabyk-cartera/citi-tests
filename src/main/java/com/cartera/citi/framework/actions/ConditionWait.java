package com.cartera.citi.framework.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ConditionWait<T> {

    public T waitForCondition(WebDriver driver, ExpectedCondition<T> condition, String errorMessage) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.withMessage(errorMessage).until(condition);
    }
}
