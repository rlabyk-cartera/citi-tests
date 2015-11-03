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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roman_Labyk on 10/5/2015.
 */
public class MyPriceRewindPage extends BasePage {

    @FindBy(id = "myPriceRewinds")
    private Html html;

    @FindBy(css = "div#cpr_header a.cpr_logInOutLink")
    private Button logoutBtn;

    @FindBy(css = ".cpr_grid>h1")
    private Message headerMsg;

    @FindBy(css = ".cpr_select.cpr_filter")
    private SelectBox filterByTrackStatus;

    @FindBy(css = "ul.cpr_filterOptions li a")
    private List<WebElement> filterOptions;

    @FindBy(css = ".cpr_select.cpr_filter .cpr_inner")
    private Button filterBtn;

    //PerPageView:
    @FindBy(css = ".cpr_select.cpr_limit")
    private SelectBox viewItemsPerPage;

    @FindBy(css = "ul.cpr_limitOptions li a")
    private List<WebElement> perPageOptions;

    @FindBy(css = ".cpr_select.cpr_limit .cpr_inner")
    private Button perPageFilterBtn;

    //Tracks:
    @FindBy(css = ".cpr_trackContent")
    private List<WebElement> trackList;

    @FindBy(css = ".cpr_productName")
    private List<WebElement> trackNamesLst;

    @FindBy(css = ".cpr_trackBtn")
    private List<WebElement> trackStatuses;

    //pagination
    @FindBy(css = ".cpr_currentPage")
    private Link curPage;

    @FindBy(css = ".cpr_lastLink")
    private Link lastPage;

    ////*[@class='cpr_pageLinks']//a[contains(@class, 'cpr_nextPage')]
    @FindBy(css = ".cpr_nextPage")
    private Link nextPage;


    public MyPriceRewindPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    private String getMyPRWPageRelativeURL() {
        return testData.getData("my_prw_page_relative_path");
    }

    public void open() {
        open(getMyPRWPageRelativeURL());
    }

    public Boolean isPageOpened() {
        try {
            return html.isDisplayed();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void logout() {
        logoutBtn.waitForElement();
        logoutBtn.click();
    }

    public String getUrl() {
        return getBaseUrl() + getMyPRWPageRelativeURL();
    }

    public String getHeaderMessage() {
        headerMsg.waitForElement();
        return headerMsg.getText().replaceAll("\n", " ").trim();
    }

    //FilterBy options
    public List<String> getAllFilterByOptions() {
        List<String> optionList = new LinkedList<String>();
        for (WebElement webElement : filterOptions) {
            optionList.add(webElement.getAttribute("innerHTML").replaceAll("[\\d]", ""));
        }
        return optionList;
    }

    public void selectTrackStatus(String status) {
        clickFilterByButton();
        for (WebElement webElement : filterOptions) {
            if (webElement.getAttribute("innerHTML").contains(status)) {
                webElement.click();
                break;
            }
        }
    }

    public String getDefaultFilterOption() {
        String option = null;
        for (WebElement webElement : filterOptions) {
            if (webElement.getAttribute("class").contains("cpr_active")) {
                option = webElement.getAttribute("innerHTML").replaceAll("[\\d]", "");
                break;
            }
        }
        return option;
    }

    public void clickFilterByButton() {
        filterBtn.waitForElement();
        filterBtn.click();
    }

    //PerPage:
    public List<String> getAllPerPageOptions() {
        List<String> optionList = new LinkedList<String>();
        for (WebElement webElement : perPageOptions) {
            optionList.add(webElement.getAttribute("innerHTML"));
        }
        return optionList;
    }

    public void selectPerPageView(String itemPerPage) {
        clickPerPageButton();
        for (WebElement webElement : perPageOptions) {
            webElement.isDisplayed();
            if (webElement.getAttribute("innerHTML").contains(itemPerPage)) {
                webElement.click();
                break;
            }
        }
    }

    public String getDefaultPerPageView() {
        String option = null;
        for (WebElement webElement : perPageOptions) {
            if (webElement.getAttribute("class").contains("cpr_active"))
                option = webElement.getAttribute("innerHTML");
        }
        return option;
    }

    public void clickPerPageButton() {
        perPageFilterBtn.waitForElement();
        perPageFilterBtn.click();
    }

    //Tracks:
    public List<String> getTrackNames() {
        List<String> trackNames = new LinkedList<String>();
        for (WebElement webElement : trackNamesLst) {
            trackNames.add(webElement.getAttribute("innerHTML"));
        }
        return trackNames;
    }

    //Tracks Statuses:get status for each track on the page:
    public List<String> getTrackStatuses() {
        List<String> trackStatusLst = new LinkedList<String>();
        for (WebElement webElement : trackStatuses) {
            trackStatusLst.add(webElement.getAttribute("innerHTML"));
        }
        return trackStatusLst;

    }

    public void clickRefundRequestPending(Integer index){
        trackStatuses.get(index).click();
    }

    public Integer getTracksCount() {
        if (!trackList.isEmpty()) {
            return trackList.size();
        } else {
            return 0;
        }
    }

    //Pagination:
    public void clickNextPage() {
        Logger.logStep("Clicking 'Next' page link.");
        nextPage.waitForElement();
        nextPage.click();
    }

    public void clickLastPage() {
        Logger.logStep("Clicking 'Last' page link.");
        lastPage.waitForElement();
        lastPage.click();
    }

    public Integer getCurPage() {
        return Integer.parseInt(curPage.getWebElement().getAttribute("innerHTML"));
    }

    public Integer getLastPage() {
        return Integer.parseInt(lastPage.getWebElement().getAttribute("innerHTML"));
    }
}