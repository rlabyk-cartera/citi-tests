package com.cartera.suites.ui.smoke;

import com.cartera.citi.bo.HIWBO;
import com.cartera.citi.framework.launcher.BaseTestClass;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Roman_Labyk on 10/16/2015.
 */
public class HIWPageSmokeITCase extends BaseTestClass {

    private HIWBO hiwBO;

    @BeforeClass
    public void setUp() {
        hiwBO = new HIWBO(getDriver());
    }

    @Test(timeOut = 300000)
    public void verifyHeroMessage() {
        hiwBO.loginAndNavigateToHIWPage();
        hiwBO.checkHeroMessage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHowItWorksBtn() {
        hiwBO.loginAndNavigateToHIWPage();
        hiwBO.chekHowItWorksBtn();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyNavigationToHIWPage() {
        hiwBO.loginAndNavigateToHIWPage();
        hiwBO.checkNavigationToHIWPage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHeroImages() {
        hiwBO.loginAndNavigateToHIWPage();
        hiwBO.checkHeroImages();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHIWSteps() {
        hiwBO.loginAndNavigateToHIWPage();
        hiwBO.checkHIWSteps();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyNavigationToSearchPage() {
        hiwBO.loginAndNavigateToHIWPage();
        hiwBO.checkNavigationToSearchResultPage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyLogout() {
        hiwBO.loginAndNavigateToHIWPage();
        hiwBO.checkLogout();
        Logger.logStep("Verification passed.");
    }

    @AfterClass
    public void tearDown() {
        Context.getTestSession().clearCookies();
    }
}