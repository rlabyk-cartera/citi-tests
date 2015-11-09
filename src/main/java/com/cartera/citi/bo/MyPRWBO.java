package com.cartera.citi.bo;

import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import com.cartera.citi.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

/**
 * Created by Roman_Labyk on 10/16/2015.
 */
public class MyPRWBO {

    private WebDriver driver;
    private TestData testData;

    private HomePage homePage;
    private LoginPage loginPage;
    private MyPriceRewindPage myPRWPage;
    private SearchResultsPage searchPage;

    public MyPRWBO(WebDriver driver) {
        this.driver = driver;
        testData = Context.getTestData();
        loginPage = new LoginPage(driver);
    }

    public void login() {
        Logger.logStep("Login");
        loginPage.open();
        homePage = loginPage.singleLogin();
    }

    public void loginAndNavigateToMyPRWPage() {
        login();
        myPRWPage = homePage.navigateToMyPRWPage();
    }

    public void checkNavigationToMyPRWPage() {
        Logger.logStep("Checking navigation to 'MyPRW' page.");
        String curURL = Context.getCurrentWindowURL();
        Assert.assertEquals(curURL, myPRWPage.getUrl(), "Navigation failed, urls differs, current: " + curURL + "," + " expected: " + myPRWPage.getUrl());
    }

    public void checkHeaderMessage() {
        Logger.logStep("Checking 'Header' message.");
        Assert.assertEquals(myPRWPage.getHeaderMessage(), testData.getData("my_prw_hero_msg"), "Hero message differs!");
    }

    public void checkFilterOptions() {
        Logger.logStep("Checking 'Filter by' options.");
        List<String> optionList = myPRWPage.getAllFilterByOptions();
        Assert.assertTrue(optionList.containsAll(testData.getList("my_prw_filter_by_options")));
    }

    public void checkDefaultFilterOption() {
        Logger.logStep("Checking 'Filter by' default option is correct.");
        String currentOption = myPRWPage.getDefaultFilterOption();
        String expectedOption = testData.getData("my_prw_filter_by_default_option");
        Assert.assertEquals(currentOption, expectedOption, "Option differs, current: " + currentOption + "," + " expected: " + expectedOption);
    }

    public void checkPerPageOptions() {
        Logger.logStep("Checking 'Per Page View' options.");
        List<String> optionList = myPRWPage.getAllPerPageOptions();
        Assert.assertTrue(optionList.containsAll(testData.getList("my_prw_per_page_view_options")));
    }

    public void checkDefaultPerPageOption() {
        Logger.logStep("Checking 'Per Page View' default option is correct.");
        String currentOption = myPRWPage.getDefaultPerPageView();
        String expectedOption = testData.getData("my_prw_per_page_view_default_option");
        Assert.assertEquals(currentOption, expectedOption, "Option differs, current: " + currentOption + "," + " expected: " + expectedOption);
    }

    public void checkTracksCount() {
        Logger.logStep("Checking 'Tracks' count on one page with default filter options.");
        Assert.assertTrue(myPRWPage.getTracksCount() == 9, "Track count differs, current: " + myPRWPage.getTracksCount() + "," + " expected: 9");
    }

    public void checkPagination() {
        Logger.logStep("Checking 'Pagination' is working.");
        Logger.logStep("Checking 'Current Page'.");
        Assert.assertTrue(myPRWPage.getCurPage() == 1, "Current page error!");
        Logger.logStep("Checking 'Next Page'.");
        myPRWPage.clickNextPage();
        Assert.assertTrue(myPRWPage.getCurPage() == 2, "Next page number error, should be: '2'");
        Logger.logStep("Checking 'Last Page'.");
        Integer lastPageNum = myPRWPage.getLastPage();
        myPRWPage.clickLastPage();
        Assert.assertTrue(myPRWPage.getCurPage().equals(lastPageNum), "Last page number error, should be: " + lastPageNum + " Got: " + myPRWPage.getCurPage());
    }

    public void checkSearch() {
        Logger.logStep("Checking search via search box.");
        Logger.logStep("Not implemented yet.");
    }

    public void setFilterAndCheck() {
        List<String> expStatusLst = testData.getList("track_statuses");
        Boolean result = false;
        for (String itemStatus : expStatusLst) {
            if (!(checkTrackStatus(itemStatus))){
                break;
            }
            result = true;
        }
        Assert.assertTrue(result, "Track Status Error!");
    }

    public void setPerPageViewAndCheck() {
        Logger.logStep("Set 'Per Page' filter.");
        List<String> expPerPageLst = testData.getList("my_prw_per_page_view_options");
        Boolean result = false;
        for (String itemsPerPage : expPerPageLst) {
            if (!(checkPageView(itemsPerPage))){
                break;
            }
            result = true;
        }
        Assert.assertTrue(result, "Per Page View Error!");
    }

    private Boolean checkTrackStatus(String status) {
        Logger.logStep("Set 'Filter by:' " + status);
        Boolean result = false;
        myPRWPage.selectTrackStatus(status);
        Logger.logStep("Start checking status: " + "'" + status + "'");
        List<String> curStatusLst = myPRWPage.getTrackStatuses();
        //workaround, because 'Request Refund' includes 'Refund Request Pending'
        for (String itemStatus : curStatusLst) {
            if (status.contains("Request Refund")) {
                if (!(itemStatus.contains("Refund Request Pending") || itemStatus.contains(status))) {
                    Logger.logToOutput("Expected: 'Refund Request' " + ", Got: " + "'" + itemStatus + "'");
                    break;
                }
                else{
                    result = true;
                }
            } else {
                if (!(itemStatus.contains(status))) {
                    Logger.logToOutput("Expected: " + "'" + status + "'" + ", Got: " + "'" + itemStatus + "'");
                    break;
                } else {
                    result = itemStatus.contains(status);
                }
            }
        }
        Logger.logStep("Finished checking " + "'" + status + "'" + "status.");
        return result;
    }

    private Boolean checkPageView(String itemsPerPage) {
        Logger.logStep("Set 'View' = " + itemsPerPage);
        Boolean result = false;
        myPRWPage.selectPerPageView(itemsPerPage);
        Logger.logStep("Start checking 'Per Page View: '" + itemsPerPage);
        Integer curTracksNum = myPRWPage.getTracksCount();
        Integer expTracksNum = Integer.parseInt(itemsPerPage.replaceAll("\\D", ""));
        result = curTracksNum == expTracksNum;
        if (!result){
            Logger.logToOutput("Expected: 'Tracks count:' " + expTracksNum + ", Got: " + "'" + curTracksNum + "'");
        }
        Logger.logStep("Finished checking tracks count when View is set: " + "'" + itemsPerPage + "'");
        return result;
    }
}