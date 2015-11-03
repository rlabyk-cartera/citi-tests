package com.cartera.citi.bo;

import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import com.cartera.citi.pages.HomePage;
import com.cartera.citi.pages.LoginPage;
import com.cartera.citi.pages.SearchResultsPage;
import com.cartera.citi.sections.FooterSection;
import com.cartera.citi.sections.HeroSection;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public class HomeBO {
    private WebDriver driver;
    private TestData testData;

    private LoginPage loginPage;
    private HomePage homePage;
    private HeroSection heroSection;
    private FooterSection footerSection;
    private SearchResultsPage searchResPage;

    public HomeBO(WebDriver driver) {
        this.driver = driver;
        testData = Context.getTestData();
        loginPage = new LoginPage(driver);
        searchResPage = new SearchResultsPage(driver);
        heroSection = new HeroSection();
        footerSection = new FooterSection();
    }


    public void loginToHomePage() {
        Logger.logStep("Login");
        loginPage.open();
        homePage = loginPage.singleLogin();
        if (homePage.isPageOpened() && homePage.userIsLogged()) {
            Logger.logHuman(Logger.Level.INFO, "User is logged", true);
        } else {
            Logger.logHuman(Logger.Level.WARNING, "Login failed!", true);
        }
    }

    public void checkUserIsLogged() {
        Assert.assertTrue(homePage.isPageOpened() && homePage.userIsLogged(), "User isn't logged in!");
    }

   public void checkHeroSection() {
        Assert.assertTrue(heroSection.isAvailable(), "Hero section isn't available");
    }

    public void checkProductSearch(String product_name) {
        Logger.logStep("Start search for 'ipod' product");
        searchResPage = homePage.productSearch(product_name);
        Assert.assertTrue(searchResPage.isPageOpened(), "'Search Page' failed to open.");
    }

    //here just size, also should add concrete names:
    public void checkHeroImages() {
        Logger.logStep("Check 'Hero' images are available.");
        Assert.assertTrue(homePage.heroImageURLs().size() > 0, "Hero images error!");
    }

    public void checkSearchTipsLnk() {
        Logger.logStep("Clicking on 'Search Tips' link.");
        homePage.clickSearchTipsLnk();
        Assert.assertTrue(driver.getCurrentUrl().contains("/faq#search-faq"), "Faq page wasn't loaded!");
    }

    public void checkWhatsCoveredLnk() {
        Logger.logStep("Clicking on 'What's Covered' link.");
        homePage.clickWhatsCoveredLnk();
        Assert.assertTrue(driver.getCurrentUrl().contains("/guide"), "Guide page wasn't loaded!");
    }

    public void checkWelcomeMsg() {
        Logger.logStep("Checking welcome message.");
        Assert.assertTrue(homePage.welcomeMsg().equalsIgnoreCase(testData.getData("home_welcome_msg")), "Welcome message differs!");
    }

    //here just size, also should add concrete names:
    public void checkFooterSection() {
        Logger.logStep("Checking footer links.");
        Assert.assertTrue(homePage.footerLinksNames().size() >= 8, "There are less links than expected!");
    }

    //here we can check concrete names (compare with property file)
    public void checkFooterLinkNames() {
        Logger.logHuman("Check 'Footer' links names.");
        Logger.logStep("Checking footer links names.");
        List<String> actLinkNames = homePage.footerLinksNames();
        List<String> expLinkNames = testData.getList("footer_section_link_names");
        Assert.assertTrue(actLinkNames.containsAll(expLinkNames), "Actual List of Links names doesn't match:" + "\nExpected:" + expLinkNames + "\nActual:" + actLinkNames);
    }

    public void Logout() {
        Logger.logStep("Click 'Sign Off' link");
        homePage.logout();
    }
}
