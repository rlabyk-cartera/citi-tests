package com.cartera.citi.bo;

import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import com.cartera.citi.pages.*;
import com.cartera.citi.sections.FooterSection;
import com.cartera.citi.sections.HeroSection;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by Roman_Labyk on 10/16/2015.
 */
public class HIWBO {

    private WebDriver driver;
    private TestData testData;

    private HomePage homePage;
    private LoginPage loginPage;
    private HowItWorksPage hiwPage;
    private SearchResultsPage searchPage;

    public HIWBO(WebDriver driver) {
        this.driver = driver;
        testData = Context.getTestData();
        loginPage = new LoginPage(driver);
    }

    public void login() {
        Logger.logStep("Login");
        loginPage.open();
        homePage = loginPage.singleLogin();
    }

    public void loginAndNavigateToHIWPage() {
        login();
        hiwPage = homePage.navigateToHIWPage();
    }

    public void checkNavigationToHIWPage() {
        Logger.logStep("Checking navigation to 'HIW' page.");
        String curURL = Context.getCurrentWindowURL();
        Assert.assertEquals(curURL, hiwPage.getUrl(), "Navigation failed, urls differs, current: " + curURL + "," + " expected: " + hiwPage.getUrl());
    }

    public void checkHeroMessage() {
        Logger.logStep("Checking 'Hero' message is available and correct.");
        Assert.assertEquals(hiwPage.getMessage(), testData.getData("hiw_hero_msg"), "Hero message differs!");
    }

    public void checkHeroImages() {
        Logger.logStep("Checking 'Hero' images.");
        Assert.assertTrue(hiwPage.getHeroImagesURL().size() > 0, "No hero images!");

    }

    public void checkHIWSteps() {
        Logger.logStep("Checking 'HIW Steps'.");
        Assert.assertTrue(hiwPage.getHIWStepsCount() > 0, "No Steps found!");
    }

    public void checkNavigationToSearchResultPage() {
        Logger.logStep("Checking navigation to 'Search Result' page.");
        searchPage = hiwPage.productSearch("ipod");
        Assert.assertTrue(searchPage.isPageOpened(), "Search page isn't available!");
    }

    public void chekHowItWorksBtn() {
        Logger.logStep("Clicking 'SeeHowItWorks' button.");
        hiwPage.clickHowItWorksBtn();
        Assert.assertTrue(hiwPage.isPlayerAvailable(), "Player is absent!");
    }

    public void checkLogout() {
        Logger.logStep("Clicking 'Logout' button.");
        hiwPage.logout();
        Assert.assertTrue(homePage.isPageOpened(), "Home page wasn't opened!");
    }
}