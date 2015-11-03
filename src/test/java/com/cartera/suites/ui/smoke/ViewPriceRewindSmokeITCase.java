package com.cartera.suites.ui.smoke;

import com.cartera.citi.bo.ViewPriceRewindBO;
import com.cartera.citi.framework.launcher.BaseTestClass;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Roman_Labyk on 10/27/2015.
 */
public class ViewPriceRewindSmokeITCase extends BaseTestClass {

    private ViewPriceRewindBO viewPriceRewindBO;

    @BeforeClass
    public void setUp() {
        viewPriceRewindBO = new ViewPriceRewindBO(getDriver());
    }

    @Test(timeOut = 300000)
    public void verifyNavigationToViewPriceRewindPage() {
        viewPriceRewindBO.loginAndNavigateToViewPriceRewingPage();
        viewPriceRewindBO.checkNavigationToViewPriceRewindPage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifySections() {
        viewPriceRewindBO.loginAndNavigateToViewPriceRewingPage();
        viewPriceRewindBO.checkSections();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyProductImage() {
        viewPriceRewindBO.loginAndNavigateToViewPriceRewingPage();
        viewPriceRewindBO.checkProductImage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyAllSteps() {
        viewPriceRewindBO.loginAndNavigateToViewPriceRewingPage();
        viewPriceRewindBO.checkSteps();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyReceipts() {
        viewPriceRewindBO.loginAndNavigateToViewPriceRewingPage();
        viewPriceRewindBO.checkReceipts();
        Logger.logStep("Verification passed.");
    }

    @AfterClass
    public void tearDown() {
        Context.getTestSession().clearCookies();
    }
}