package com.cartera.citi.framework.launcher;

import com.cartera.citi.framework.testdata.TestData;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import com.cartera.citi.framework.launcher.Context;


@Listeners({TestListener.class})
public abstract class BaseTestClass {

    public WebDriver getDriver(){
        return Context.getTestSession().getDriver();
    }
}