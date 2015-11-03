package com.cartera.citi.framework.launcher;

import com.cartera.citi.framework.actions.Action;
import com.cartera.citi.framework.actions.ConditionWait;
import com.cartera.citi.framework.testdata.TestData;
import com.google.common.base.Function;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Context {

    public static TestSession testSession;
    public static TestData testData;


    public static TestSession getTestSession() {
        return testSession;
    }

    public static TestData getTestData() {
        return testData;
    }

    public static Object ajaxWait(final Action runnable) {
        RemoteWebDriver driver = ((RemoteWebDriver) getTestSession().getDriver());
        final boolean waitRequested = runnable.run();
        final WebDriverWait driverWait = new WebDriverWait(driver, 30);
        final JavascriptExecutor js = driver;
        final Object result = driverWait.until(new Function<WebDriver, Object>() {
            @Override
            public Object apply(final WebDriver webDriver) {
                return !waitRequested || (Boolean) js.executeScript("return jQuery.active == 0");
            }
        });
        return result;
    }

    public static String getCurrentWindowURL() {
        return testSession.getDriver().getCurrentUrl();
    }

    public static Boolean waitForCondition(ExpectedCondition<Boolean> condition, String errorMessage) {
        ConditionWait<Boolean> conditionWait = new ConditionWait<Boolean>();
        return conditionWait.waitForCondition(getTestSession().getDriver(), condition, errorMessage);
    }

    public static void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.assertFalse(true, "Timeout waiting for Page Load Request to complete.");
        }
    }
}
