package com.cartera.citi.framework.launcher;

import org.openqa.selenium.WebDriver;

public class TestSession {

    private WebDriver driver;

    public TestSession(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void close() {
        driver.quit();
    }

    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }
}