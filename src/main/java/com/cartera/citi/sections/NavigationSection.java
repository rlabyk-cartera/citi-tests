package com.cartera.citi.sections;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.Link;
import com.cartera.citi.framework.launcher.Context;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman_Labyk on 10/13/2015.
 */
public class NavigationSection extends BaseSection {

    private WebElement webElement;

    @FindBy(id = "#cpr_nav")
    @CacheLookup
    private WebElement navContainer;

    @FindBy(css = ".cpr_navList.cpr_clearfix>li a")
    private List<Link> navItems;

    @FindBy(xpath = "//*[@id='cpr_nav']//a[contains(@href,'how-it-works')]")
    private Link howItWorks;

    @FindBy(xpath = "//*[@id='cpr_nav']//a[contains(@href,'my-price-rewinds')]")
    private Link myPRW;

    @FindBy(css = "#cpr_nav ul:nth-child(1) li a")
    private Link home;


    @Override
    public String getSectionName() {
        return "Navigation";
    }

    public NavigationSection() {
        this.driver = Context.getTestSession().getDriver();
        this.webElement = navContainer;
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    @Override
    public WebElement getWebElement() {
        return webElement;
    }

    @Override
    public Boolean isAvailable() {
        return navContainer.isDisplayed();
    }

    public List<String> getNavItNames() {
        List<String> linkNames = new ArrayList<String>();
        for (Link link : navItems) {
            if (!link.getText().isEmpty()) {
                linkNames.add(link.getText());
            }
        }
        return linkNames;
    }

    public void clickHome() {
        home.isDisplayed();
        home.click();
    }

    public void clickHIW() {
        howItWorks.waitForElement();
        howItWorks.click();
    }

    public void clickMyPRW() {
        myPRW.waitForElement();
        myPRW.click();
    }
}