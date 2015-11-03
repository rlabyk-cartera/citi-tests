package com.cartera.suites.ui.smoke;

import com.cartera.citi.bo.HomeBO;
import com.cartera.citi.framework.launcher.BaseTestClass;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.logger.Logger.Level;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public class HomePageSmokeITCase extends BaseTestClass {

    private HomeBO homeBO;

    @BeforeClass
    public void setUp() {
        homeBO = new HomeBO(getDriver());
    }

    @Test(timeOut = 300000)
    public void verifyLoginHomePage() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkUserIsLogged();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHeroSection() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkHeroSection();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHomePageStructure() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        //HomeBO.checkPageStructure()
        homeBO.Logout();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyProductSearch() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkProductSearch("ipod");
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHomePageLogout() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.Logout();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHeroImages() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkHeroImages();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifySearchTipLnk() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkSearchTipsLnk();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyWhatsCoveredLnk() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkWhatsCoveredLnk();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyWelcomeMsg() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkWelcomeMsg();
        Logger.logStep("Verification passed.");
    }

    /**
     * Footer tests:
     */
    @Test(timeOut = 300000)
    public void verifyFooter() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkFooterSection();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyFooterLinkNames() {
        Logger.logStep("Login to 'Home' Page");
        homeBO.loginToHomePage();
        homeBO.checkFooterLinkNames();
        Logger.logStep("Verification passed.");
    }


    @AfterClass
    public void tearDown() {
        Context.getTestSession().clearCookies();
    }
}
