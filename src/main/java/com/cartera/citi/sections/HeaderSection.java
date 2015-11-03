package com.cartera.citi.sections;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.Element;
import com.cartera.citi.framework.elements.Link;
import com.cartera.citi.framework.elements.Message;
import com.cartera.citi.framework.launcher.Context;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public class HeaderSection extends BaseSection {

    private WebElement webElement;

    @FindBy(id = "#cpr_header")
    private WebElement headerContainer;

    @FindBy(css = "div#cpr_header a.cpr_logInOutLink")
    private Link signONOFF;

    @FindBy(css = ".cpr_logoLink>img")
    private Link logoImg;

    @FindBy(css = ".cpr_logoLink>h2")
    private Message headerText;

    @FindBy(css = "div#cpr_header a.cpr_logoLink")
    private Link headerLogo;

    public HeaderSection() {
        this.driver = Context.getTestSession().getDriver();
        this.webElement = headerContainer;
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    @Override
    public String getSectionName() {
        return "Header";
    }

    @Override
    public Boolean isAvailable() {
        return headerContainer.isDisplayed();
    }

    public void clickSignOff() {
        signONOFF.waitForElement();
        signONOFF.click();
    }

    public void clickLogoImg() {
        logoImg.waitForElement();
        logoImg.click();
    }

    public String getHeaderText() {
        headerText.waitForElement();
        return headerText.getText();
    }
}