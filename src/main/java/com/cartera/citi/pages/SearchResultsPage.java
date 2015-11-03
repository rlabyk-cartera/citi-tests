package com.cartera.citi.pages;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.*;
import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roman_Labyk on 10/5/2015.
 */
public class SearchResultsPage extends BasePage {

    private final String default_search_text = "ipod";

    @FindBy(id = "searchResults")
    private Html html;

    @FindBy(xpath = "//*[@name='q']")
    private TextBox searchBox;

    //for checking quantity of items
    @FindBy(css = ".cpr_jsHTML>h2")
    private Message searchMsg;

    //check message.iquals
    @FindBy(css = ".cpr_jsHTML>h3")
    private Message contHeaderMsg;

    @FindBy(css = ".cpr_searchResultsList li")
    private List<WebElement> searchResultItems;

    //@FindBy(xpath = "//*[@class='cpr_searchResultsList']//li[%s]//div[@class='cpr_textBox']")
    @FindBy(css =".cpr_searchResultsList a.cpr_trackBtn")
    private List<Button> trackItemBtnLst;

    //here we can check list:
    // - if greater 0
    //- names
    //left Filters
    @FindBy(css = ".cpr_facet")
    private List<WebElement> filterList;

    @FindBy(css = ".cpr_facet>h3")
    private List<WebElement> filterNames;

    //PerPageView:
    //get active and check items quantity
    @FindBy(css = ".cpr_select.cpr_limit")
    private SelectBox viewItemsPerPage;

    @FindBy(css = "ul.cpr_limitOptions li a")
    private List<WebElement> perPageOptions;

    @FindBy(css = ".cpr_select.cpr_limit .cpr_inner")
    private List<Button> perPageFilterBtnLst;

    //pagination
    @FindBy(css = ".cpr_currentPage")
    private Link curPage;

    @FindBy(css = ".cpr_lastLink")
    private Link lastPage;

    @FindBy(css = ".cpr_nextPage")
    private Link nextPage;

    public SearchResultsPage(WebDriver driver) {
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

    private String getSearchResultsPageRelativeURL() {
        return testData.getData("search_results_page_relative_path");
    }

    public void open() {
        Logger.logStep("Opening 'Search Results Page' with search text: " + default_search_text);
        open(getSearchResultsPageRelativeURL() + default_search_text);
    }

    public String getUrl() {
        return getBaseUrl() + getSearchResultsPageRelativeURL();
    }

    public String getContentHeaderMessage() {
        contHeaderMsg.waitForElement();
        return contHeaderMsg.getText().replaceAll("\n", " ").trim();
    }

    public Integer getSearchItemsFound() {
        searchMsg.waitForElement();
        Integer itemCount = 0;
        try {
            itemCount = Integer.parseInt(searchMsg.getText().replaceAll("[\\D]", ""));
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        return itemCount;
    }

    public Integer getSearchProductList(){
        return searchResultItems.size();
    }

    public void clickTrackItemByIndex(int index) {
        Logger.logStep("Clicking button for item with index: " + index);
        trackItemBtnLst.get(index).click();
    }

    public List<String> getLeftFiltersNames() {
        List<String> filterNamesLst = new LinkedList<String>();
        for (WebElement filterName : filterNames) {
            if (!(filterName.getText().isEmpty())) {
                filterNamesLst.add(filterName.getText());
            }
        }
        return filterNamesLst;
    }


    public String getPerPageView() {
        String option = null;
        for (WebElement webElement : perPageOptions) {
            if (webElement.getAttribute("class").contains("cpr_active")) {
                option = webElement.getAttribute("innerHTML");
                break;
            }
        }
        return option;
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

    public Integer getCurPageNum() {
        return Integer.parseInt(curPage.getWebElement().getAttribute("innerHTML"));
    }

    public Integer getLastPageNum() {
        return Integer.parseInt(lastPage.getWebElement().getAttribute("innerHTML"));
    }
}