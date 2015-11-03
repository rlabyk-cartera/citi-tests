package com.cartera.citi.sections;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.*;
import com.cartera.citi.framework.launcher.BaseTestClass;
import com.cartera.citi.framework.launcher.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roman_Labyk on 10/5/2015.
 */
public class HeroSection extends BaseSection {

    private WebElement webElement;

    @FindBy(css = ".cpr_hero")
    private WebElement heroContainer;

    @FindBy(xpath = "//*[@name='q']")
    private TextBox searchBox;

    @FindBy(className = "cpr_searchSubmit")
    private Button submit;

    @FindBy(css = "div.cpr_visuals.cpr_xs-hide img")
    private List<WebElement> heroImages;

    @FindBy(css = "div.cpr_helpText>p>a")
    private Link searchTips;

    @FindBy(css = ".cpr_guideLink")
    private Link whatsCovered;

    @FindBy(css = ".cpr_copy.cpr_sm-10>h1")
    private Message welcomeMsg;

    public HeroSection() {
        this.driver = Context.getTestSession().getDriver();
        this.webElement = heroContainer;
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    @Override
    public String getSectionName() {
        return "Hero";
    }

    @Override
    public Boolean isAvailable() {
        return heroContainer.isDisplayed();
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

    public List<WebElement> getHeroImages() {
        List<WebElement> curHeroImages = new ArrayList<WebElement>();
        for (WebElement webElement : heroImages) {
            curHeroImages.add(webElement);
        }
        return curHeroImages;
    }

    public void clickSearchTipsLnk() {
        searchTips.waitForElement();
        searchTips.click();
    }

    public void clickWhatsCoveredLnk() {
        whatsCovered.waitForElement();
        whatsCovered.click();
    }

    public String getWelcomeMessage() {
        welcomeMsg.waitForElement();
        return welcomeMsg.getText().trim();
    }

    public List<URL> getHeroImagesURL() {
        List<URL> urls = new LinkedList<URL>();
        for (WebElement webElement : heroImages) {
            try {
                urls.add(new URL((webElement.getAttribute("src"))));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}