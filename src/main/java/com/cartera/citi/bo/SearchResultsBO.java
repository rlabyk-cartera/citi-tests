package com.cartera.citi.bo;

import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import com.cartera.citi.pages.HomePage;
import com.cartera.citi.pages.LoginPage;
import com.cartera.citi.pages.MyPriceRewindPage;
import com.cartera.citi.pages.SearchResultsPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by Roman_Labyk on 10/20/2015.
 */
public class SearchResultsBO {

    private WebDriver driver;
    private TestData testData;

    private final String default_page_view = "10 per page";

    private HomePage homePage;
    private LoginPage loginPage;
    private SearchResultsPage searchPage;

    public SearchResultsBO(WebDriver driver) {
        this.driver = driver;
        this.testData = Context.getTestData();
        loginPage = new LoginPage(driver);
    }

    public void login() {
        Logger.logStep("Login");
        loginPage.open();
        homePage = loginPage.singleLogin();
    }

    public void loginAndNavigateToSearchResultPage() {
        login();
        searchPage = homePage.productSearch(testData.getData("search_text"));
    }

    //looks like search isn't possible when not logged in
    public void navigateToSearchResultPage() {
        Logger.logStep("Navigation to 'Search Results' page without login.");
        searchPage = new SearchResultsPage(driver);
        searchPage.open();
    }

    public void checkNavigationToSearchResultPage() {
        Logger.logStep("Check navigation to 'Search Results' page.");
        String curURL = Context.getCurrentWindowURL();
        String expURL = searchPage.getUrl()+testData.getData("search_text");
        Assert.assertEquals(curURL, expURL, "Navigation failed, urls differs, current: " + curURL + "," + " expected: " + expURL);
    }

    public void checkContentHeaderMessage() {
        Logger.logStep("Checking 'Header' message.");
        Assert.assertEquals(searchPage.getContentHeaderMessage(), testData.getData("search_res_page_header_msg"), "Content Header message differs!");
    }

    public void checkSearchItems() {
        Logger.logStep("Checking 'Search Items' quantity.");
        Assert.assertTrue(searchPage.getSearchItemsFound() > 0, "There are no items in DB or Search is broken!");
    }

    public void checkProductSearchList() {
        Logger.logStep("Checking 'Product List' isn't empty.");
        Assert.assertTrue(searchPage.getSearchProductList() > 0, "No products found!");
    }

    public void checkTrackItemBtn() {
        Logger.logStep("Checking 'Track This Item' button is working.");
        searchPage.clickTrackItemByIndex(1);
        Assert.assertTrue(Context.getCurrentWindowURL().contains("start-price-rewind?product_fk="));
    }

    public void checkFiltersNames() {
        Logger.logStep("Checking 'Left Filters Names'");
        Assert.assertEquals(searchPage.getLeftFiltersNames(), testData.getList("search_res_page_filter_names"), "Category Names differs!");
    }

    public void checkPageView() {
        Logger.logStep("Checking 'Default Page View' option");
        Assert.assertEquals(searchPage.getPerPageView(), default_page_view, "Error in page view options!");
    }

    public void checkPagination() {
        Logger.logStep("Checking 'Pagination' is working");
        Logger.logStep("Checking 'Current Page'.");
        Assert.assertTrue(searchPage.getCurPageNum() == 1, "Current page error!");
        Logger.logStep("Checking 'Next Page'.");
        searchPage.clickNextPage();
        Assert.assertTrue(searchPage.getCurPageNum() == 2, "Next page number error, should be: '2'");
        Logger.logStep("Checking 'Last Page'.");
        final Integer lastPageNum = searchPage.getLastPageNum();
        searchPage.clickLastPage();
        //final Integer curPageNum = searchPage.getCurPageNum();
        Assert.assertTrue((searchPage.getCurPageNum().equals(lastPageNum)), "Last page number error, should be: " + lastPageNum + " but got: " + searchPage.getCurPageNum());
    }
}