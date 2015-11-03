package com.cartera.citi.pages;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.Button;
import com.cartera.citi.framework.elements.Html;
import com.cartera.citi.framework.elements.Link;
import com.cartera.citi.framework.elements.Section;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.sections.FooterSection;
import com.cartera.citi.sections.HeroSection;
import com.cartera.citi.sections.NavigationSection;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.URL;
import java.util.List;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public class HomePage extends BasePage {

    @FindBy(id = "home")
    private Html html;

    @FindBy(xpath = "//*[@id='cpr_header']//a[contains(text(),'Sign Off')]")
    private Link signOffLnk;

    @FindBy(xpath = "//*[@id='cpr_header']//a[contains(text(),'Sign On')]")
    private Link signOnLnk;

    @FindBy(id = "cpr_header")
    private Section header;

    @FindBy(id = "cpr_nav")
    private Section navigation;

    @FindBy(id = "cpr_content")
    private Section content;

    @FindBy(id = "cpr_footer")
    private Section footer;

    @FindBy(xpath = "div.cpr_recentTracks div.cpr_trackBox div.cpr_trackContent")
    private List<Section> trackItems;

    @FindBy(css = ".cpr_btn")
    private Button viewAllPRBtn;

    @FindBy(xpath = "//*[@id='cpr_content']//div[contains(@class, 'cpr_messageTile_')]")
    private List<Section> messageItems;

    @FindBy(xpath = "//*[@id='cpr_content']//div[contains(@class, 'cpr_statTile_')]")
    private List<Section> statsItems;

    @FindBy(xpath = "//div[contains(@class, 'cpr_step')]")
    private List<Section> overviewItems;

    @FindBy(css = ".cpr_btn")
    private Button learnMoreBtn;

    private HeroSection heroSection = new HeroSection();
    private FooterSection footerSection = new FooterSection();
    private NavigationSection navSection = new NavigationSection();

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    public Boolean isPageOpened() {
        try {
            return html.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Boolean userIsLogged() {
        signOffLnk.waitForElement();
        signOffLnk.highlightWebElement(signOffLnk.getWebElement());
        return signOffLnk.isDisplayed();
    }

    //Search results page:
    public SearchResultsPage productSearch(String item) {
        if (!(heroSection.isAvailable())) {
            heroSection.waitForElement();
        }
        heroSection.setTextAndSearch(item);
        return new SearchResultsPage(driver);
    }

    //Hiw Page:
    public HowItWorksPage navigateToHIWPage() {
        Logger.logHuman(Logger.Level.INFO, "Clicking 'How It Works' link.", true);
        navSection.waitForElement();
        navSection.clickHIW();
        return new HowItWorksPage(driver);
    }

    //MyPRW Page:
    public MyPriceRewindPage navigateToMyPRWPage() {
        Logger.logHuman(Logger.Level.INFO, "Clicking 'My Price Rewinds' link.", true);
        navSection.waitForElement();
        navSection.clickMyPRW();
        return new MyPriceRewindPage(driver);
    }

    //ViewPRWPage:
    public ViewPriceRewindPage naigateToViewPrwPage(Integer index) {
        Logger.logHuman(Logger.Level.INFO, "Clicking 'My Price Rewinds' link.", true);
        navSection.waitForElement();
        navSection.clickMyPRW();
        MyPriceRewindPage myPRWPage = new MyPriceRewindPage(driver);
        Logger.logStep("Clicking on first product.");
        myPRWPage.clickRefundRequestPending(index);
        return new ViewPriceRewindPage(driver);
    }

    public List<URL> heroImageURLs() {
        heroSection.waitForElement();
        return heroSection.getHeroImagesURL();
    }

    public void clickSearchTipsLnk() {
        heroSection.waitForElement();
        heroSection.clickSearchTipsLnk();
    }

    public void clickWhatsCoveredLnk() {
        heroSection.waitForElement();
        heroSection.clickWhatsCoveredLnk();
    }

    public String welcomeMsg() {
        heroSection.waitForElement();
        return heroSection.getWelcomeMessage().replaceAll("\n", " ").trim();
    }

    public List<String> footerLinksNames() {
        footerSection.waitForElement();
        return footerSection.getLinkNames();
    }

    //begin navigators:
  /*  public void navigateToHIWPage() {
        navSection.waitForElement();
        navSection.clickHIW();
    }*/

    public void naigateToMyPRWPage() {
        navSection.waitForElement();
        navSection.clickMyPRW();
    }
    //end navigators

    public Integer getRecentTracksCount() {
        if (!trackItems.isEmpty()) {
            return trackItems.size();
        } else {
            return 0;
        }
    }

    public void clickViewAllPRBtn() {
        viewAllPRBtn.click();
    }

    public Integer getMessageCount() {
        if (!messageItems.isEmpty()) {
            return messageItems.size();
        } else {
            return 0;
        }
    }

    public Integer getStatsItemsCount() {
        if (!statsItems.isEmpty()) {
            return statsItems.size();
        } else {
            return 0;
        }

    }

    public Integer getOverviewItemsCount() {
        if (!overviewItems.isEmpty()) {
            return overviewItems.size();
        } else {
            return 0;
        }
    }

    public void clickLearnMoreBtn() {
        learnMoreBtn.waitForElement();
        learnMoreBtn.click();
    }

    public void logout() {
        signOffLnk.waitForElement();
        signOffLnk.click();
    }
}