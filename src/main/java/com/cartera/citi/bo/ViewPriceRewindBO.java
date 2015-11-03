package com.cartera.citi.bo;

import com.cartera.citi.framework.elements.Section;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import com.cartera.citi.pages.HomePage;
import com.cartera.citi.pages.LoginPage;
import com.cartera.citi.pages.SearchResultsPage;
import com.cartera.citi.pages.ViewPriceRewindPage;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Roman_Labyk on 10/27/2015.
 */
public class ViewPriceRewindBO {

    private WebDriver driver;
    private TestData testData;

    private HomePage homePage;
    private LoginPage loginPage;
    private ViewPriceRewindPage viewPrwPage;


    public ViewPriceRewindBO(WebDriver driver) {
        this.driver = driver;
        testData = Context.getTestData();
        loginPage = new LoginPage(driver);
    }

    public void login() {
        Logger.logStep("Login");
        loginPage.open();
        homePage = loginPage.singleLogin();
    }

    public void loginAndNavigateToViewPriceRewingPage() {
        login();
        viewPrwPage = homePage.naigateToViewPrwPage(0);
    }

    //implement all checkers:
    public void checkNavigationToViewPriceRewindPage() {
        Logger.logStep("Check navigation to 'View Price Rewind' page.");
        String curURL = Context.getCurrentWindowURL();
        String expURL = viewPrwPage.getUrl();
        Assert.assertTrue(curURL.contains(expURL), "Navigation failed, urls differs, current: " + curURL + "," + " expected: " + expURL);
    }

    public void checkSections() {
        Logger.logStep("Check all Sections are available on the page.");
        Boolean isPresent = false;
        //for (Section section : viewPrwPage.getSections()) {
        for (Map.Entry<String, Section> sectionEntry : viewPrwPage.getSectionsMap().entrySet()) {
            Logger.logStep("Start checking: " + sectionEntry.getKey());
            //rebuild for try catch:
            if (sectionEntry.getValue().isDisplayed()) {
                isPresent = true;
            } else {
                Logger.logToOutput(sectionEntry.getKey() + " is absent!");
                break;
            }
        }
        Assert.assertTrue(isPresent, "Not all sections are present!");
    }

    public void checkProductImage() {
        Logger.logStep("Check product image isn't broken.");
        String prodUrl = viewPrwPage.getProductImageURL().toString();
        HttpResponse response = null;
        try {
            response = new DefaultHttpClient().execute(new HttpGet(prodUrl));
        } catch (IOException e) {
            Logger.logTechnical("Image with url = " + prodUrl + "is broken");
            e.printStackTrace();
        }
        Assert.assertTrue(response.getStatusLine().getStatusCode() == 200, "Image is broken!");
    }

    public void checkSteps() {
        Logger.logStep("Check all Steps are available on the page.");
        Assert.assertTrue(viewPrwPage.getAllSteps().equals(testData.getList("view_price_rewind_all_steps")), "Some steps are missed!");
    }

    public void checkReceipts() {
        Logger.logStep("Check Receipts size.");
        Assert.assertTrue(viewPrwPage.getReceiptNum() > 0, "There is no receipts available!");
    }
}