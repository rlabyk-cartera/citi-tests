package com.cartera.citi.pages;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.*;
import com.cartera.citi.framework.logger.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roman_Labyk on 10/15/2015.
 */
public class HowItWorksPage extends BasePage {

    @FindBy(id = "howItWorks")
    private Html html;

    @FindBy(css = ".cpr_btn.cpr_movieLink")
    private Button seeHIWBtn;

    //@FindBy(css = ".cpr_copy.cpr_md-10.cpr_sm-8>h1")
    @FindBy(xpath = "//*[@id='cpr_content']//h1")
    private Message heroMsg;

    @FindBy(css = ".cpr_visuals.cpr_xs-hide img")
    private List<Link> heroImages;

    @FindBy(css = ".cpr_HWsteps>img")
    private List<Link> hiwSteps;

    @FindBy(css = "div#cpr_header a.cpr_logInOutLink")
    private Button logoutBtn;

    @FindBy(xpath = "//*[@name='q']")
    private TextBox searchBox;

    @FindBy(className = "cpr_searchSubmit")
    private Button submit;

    @FindBy(css = "#cpr_movieWrap")
    private Section movieWrapper;

    @FindBy(css = "#cpr_movie")
    private Section movie;

    @FindBy(css = ".cpr_closeIcon")
    private Button closeMovieBtn;


    public HowItWorksPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    public Boolean isPageOpened() {
        try {
            return html.isDisplayed();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getHIWPageRelativeURL() {
        return testData.getData("hiw_page_relative_path");
    }

    public void open() {
        open(getHIWPageRelativeURL());
    }

    public void logout() {
        logoutBtn.waitForElement();
        logoutBtn.click();
    }

    public String getUrl() {
        return getBaseUrl() + getHIWPageRelativeURL();
    }

    public void clickHowItWorksBtn() {
        seeHIWBtn.waitForElement();
        seeHIWBtn.click();
    }

    public String getMessage() {
        heroMsg.waitForElement();
        return heroMsg.getText().replaceAll("\n", " ").trim();
    }

    public List<URL> getHeroImagesURL() {
        List<URL> urls = new LinkedList<URL>();
        for (Link link : heroImages) {
            try {
                urls.add(new URL((link.getWebElement().getAttribute("src"))));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    public Integer getHIWStepsCount() {
        if (!hiwSteps.isEmpty()) {
            return hiwSteps.size();
        } else {
            return 0;
        }
    }

    public void setTextAndSearch(String searchText) {
        searchBox.waitForElement();
        searchBox.clear();
        searchBox.setText(searchText);
        clickSubmitBtn();
    }

    public void clickSubmitBtn() {
        submit.waitForElement();
        submit.click();
    }

    //navigation
    public SearchResultsPage productSearch(String productName) {
        Logger.logHuman(Logger.Level.INFO, "Clicking 'How It Works Lnk'", true);
        setTextAndSearch(productName);
        return new SearchResultsPage(driver);
    }

    //player logic:
    public Boolean isPlayerAvailable() {
        Boolean result;
        movieWrapper.waitForElement();
        result = movieWrapper.getWebElement().getCssValue("display").contains("block");
        closePlayer();
        return result;
    }

    public void closePlayer() {
        closeMovieBtn.waitForElement();
        closeMovieBtn.click();
    }
}
