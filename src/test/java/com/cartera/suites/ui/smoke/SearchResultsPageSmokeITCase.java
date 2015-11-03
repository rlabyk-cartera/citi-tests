package com.cartera.suites.ui.smoke;

import com.cartera.citi.bo.SearchResultsBO;
import com.cartera.citi.framework.launcher.BaseTestClass;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Roman_Labyk on 10/20/2015.
 */
public class SearchResultsPageSmokeITCase extends BaseTestClass {

    private SearchResultsBO searchResultsBO;

    @BeforeClass
    public void setUp() {
        searchResultsBO = new SearchResultsBO(getDriver());
    }

    @Test(timeOut = 300000)
    public void verifyContHeaderMessage() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkContentHeaderMessage();
        Logger.logStep("Verification passed.");
    }


    @Test(timeOut = 300000)
    public void verifyNavigationToSearchResultsPage() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkNavigationToSearchResultPage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifySearchItems() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkSearchItems();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyProductSearchList() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkProductSearchList();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyFilterNames() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkFiltersNames();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyTrackBtn() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkTrackItemBtn();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyPageView() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkPageView();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyPagination() {
        searchResultsBO.loginAndNavigateToSearchResultPage();
        searchResultsBO.checkPagination();
        Logger.logStep("Verification passed.");
    }

    @AfterClass
    public void tearDown() {
        Context.getTestSession().clearCookies();
    }
}