package com.cartera.suites.ui.smoke;

import com.cartera.citi.bo.MyPRWBO;
import com.cartera.citi.framework.launcher.BaseTestClass;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Roman_Labyk on 10/16/2015.
 */
public class MyPRWPageSmokeITCase extends BaseTestClass {

    private MyPRWBO myPRWBO;

    @BeforeClass
    public void setUp() {
        myPRWBO = new MyPRWBO(getDriver());
    }

    @Test(timeOut = 300000)
    public void verifyHeaderMessage() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkHeaderMessage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyNavigationToMyPRWPage() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkNavigationToMyPRWPage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyDefaultFilterOption() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkDefaultFilterOption();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyAllFilterOptions() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkFilterOptions();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyPerPageDefaultOption() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkDefaultPerPageOption();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyPerPageOptions() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkPerPageOptions();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyTracks() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkTracksCount();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyPagination() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.checkPagination();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000, enabled = false)
    public void verifySearch() {

    }

    @Test(timeOut = 300000, enabled = true)
    public void verifyFilterByTrackStatus() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.setFilterAndCheck();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000, enabled = true)
    public void verifyFilterPerPage() {
        myPRWBO.loginAndNavigateToMyPRWPage();
        myPRWBO.setPerPageViewAndCheck();
        Logger.logStep("Verification passed.");
    }

    @AfterClass
    public void tearDown() {
        Context.getTestSession().clearCookies();
    }
}