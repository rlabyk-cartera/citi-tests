package com.cartera.citi.bo;

import com.cartera.citi.framework.elements.Button;
import com.cartera.citi.framework.elements.Section;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import com.cartera.citi.pages.HomePage;
import com.cartera.citi.pages.LoginPage;
import com.cartera.citi.pages.RequestRefundPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by Roman_Labyk on 10/28/2015.
 */
public class RequestRefundBO {
    private WebDriver driver;
    private TestData testData;

    private HomePage homePage;
    private LoginPage loginPage;
    private RequestRefundPage requestRefundPage;


    public RequestRefundBO(WebDriver driver) {
        this.driver = driver;
        testData = Context.getTestData();
        loginPage = new LoginPage(driver);
        requestRefundPage = new RequestRefundPage(driver);
    }

    public void login() {
        Logger.logStep("Login");
        loginPage.open();
        homePage = loginPage.singleLogin();
        Context.waitForPageLoaded(driver);
    }

    public void loginAndNavigateToRequestRefundPage() {
        login();
        requestRefundPage.open();
    }

    public void checkNavigationToToRequestRefundPage() {
        Logger.logStep("Check navigation to 'View Price Rewind' page.");
        String curURL = Context.getCurrentWindowURL();
        String expURL = requestRefundPage.getUrl();
        Assert.assertTrue(curURL.contains(expURL), "Navigation failed, urls differs, current: " + curURL + "," + " expected: " + expURL);
    }

    public void checkHeaderMsg(){
        Logger.logStep("Checking 'Header' message.");
        Assert.assertEquals(requestRefundPage.getHeaderMessage(), testData.getData("request_refund_page_header_msg"), "Header message differs!");
    }

    public void checkSections() {
        Logger.logStep("Check all Sections are available on the page.");
        Boolean isPresent = false;
        for (Section section : requestRefundPage.getSections()) {
            if (section.isDisplayed()) {
                isPresent = true;
            } else {
                isPresent = false;
                break;
            }
        }
        Assert.assertTrue(isPresent, "Not all sections are present!");
    }

    public void checkButtons() {
        Logger.logStep("Check all Buttons are available on the page.");
        Boolean isPresent = false;
        for (Button button : requestRefundPage.getButtons()) {
            button.waitForElement();
            if (button.isDisplayed()) {
                isPresent = true;
            } else {
                break;
            }
        }
        Assert.assertTrue(isPresent, "Not all buttons are present!");
    }
}
