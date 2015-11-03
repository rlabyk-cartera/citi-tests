package com.cartera.citi.pages;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.logger.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import com.cartera.citi.framework.elements.Button;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[contains(@value,'Single')]")
    private Button singleLoginBtn;

    @FindBy(xpath = "//input[contains(@value,'Multiple')]")
    private Button multiLoginBtn;


    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    private String getBackDoorRelativeUrl() {
        return "/demo/citi_prw_sso.php";
    }

    public void open() {
        super.open(getBackDoorRelativeUrl());
    }

    public Button getSingleLoginBtn() {
        return singleLoginBtn;
    }

    public Button getMultiLoginBtn() {
        return multiLoginBtn;
    }

    public HomePage singleLogin() {
        Logger.logHuman(Logger.Level.INFO, "Clicking 'Single Login Btn'", true);
        clickSingleLoginBtn();
        return new HomePage(driver);
    }

    public HomePage multiLogin() {
       /* if (userIsLogged()) {
            Logger.logStep("User is already logged.");
            (new HomePage(driver)).logout();
        }*/
        Logger.logHuman(Logger.Level.INFO, "Start filling fields", true);
        clickMultiLoginBtn();
        //here we should select card
        return new HomePage(driver);
    }

    public void clickSingleLoginBtn() {
        getSingleLoginBtn().waitForElement();
        getSingleLoginBtn().click();
    }

    public void clickMultiLoginBtn() {
        getMultiLoginBtn().waitForElement();
        getMultiLoginBtn().click();
    }

}
