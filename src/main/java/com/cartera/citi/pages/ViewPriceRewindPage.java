package com.cartera.citi.pages;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.Html;
import com.cartera.citi.framework.elements.Section;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Roman_Labyk on 10/5/2015.
 */
public class ViewPriceRewindPage extends BasePage {

    private final Integer default_track_id = 2235;

    @FindBy(id = "viewPriceRewind")
    private Html html;

    //main sections:
    @FindBy(css = ".cpr_trackStatus")
    private Section trackStatusSection;

    @FindBy(css = ".cpr_trackDetails")
    private Section trackDetailsSection;

    @FindBy(css = ".cpr_receiptDrawer")
    private Section receiptDrawerSection;

    @FindBy(css = ".cpr_trackHistory")
    private Section trackHistorySection;


    //product staff:
    @FindBy(css = ".cpr_productImage")
    private WebElement productImage;

    @FindBy(xpath = ".//*[@id='cpr_content']//div[@class='cpr_productInfo']//h2")
    private WebElement productName;

    //Steps:
    //getCurStepName
    @FindBy(css = ".cpr_step.cpr_step.cpr_current .cpr_stepText")
    private WebElement currentStepName;

    //getAllStepNames
    @FindBy(css = ".cpr_stepText")
    private List<WebElement> allStepsLst;

    //Receipt:
    //receiptList
    @FindBy(css = ".cpr_receipt.cpr_item")
    private List<WebElement> receiptLst;

    public ViewPriceRewindPage(WebDriver driver) {
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

    private String getViewPriceRewindPageRelativeURL() {
        return testData.getData("view_price_rewind_page_relative_path");
    }

    public void open() {
        open(getViewPriceRewindPageRelativeURL() + default_track_id);
    }

    public void open(Integer track_id) {
        open(getViewPriceRewindPageRelativeURL() + track_id);
    }

    public String getUrl() {
        return getBaseUrl() + getViewPriceRewindPageRelativeURL();
    }

    public List<Section> getSections() {
        List<Section> sectionList = new LinkedList<Section>();
        sectionList.add(trackStatusSection);
        sectionList.add(trackDetailsSection);
        sectionList.add(receiptDrawerSection);
        sectionList.add(trackHistorySection);
        return sectionList;
    }

    public Map<String, Section> getSectionsMap() {
        Map<String, Section> sectionMap = new HashMap<String,Section>();
        sectionMap.put("Track Status", trackStatusSection);
        sectionMap.put("Track Details", trackDetailsSection);
        sectionMap.put("Receipt", receiptDrawerSection);
        sectionMap.put("Track History", trackHistorySection);
        return sectionMap;
    }

    public URL getProductImageURL() {
        URL imgURL = null;
        try {
            imgURL = new URL(productImage.getAttribute("src"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return imgURL;
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getCurStepName() {
        return currentStepName.getText();
    }

    public List<String> getAllSteps() {
        List<String> stepsLst = new LinkedList<String>();
        for (WebElement webElement : allStepsLst) {
            stepsLst.add(webElement.getText());
        }
        return stepsLst;
    }

    public Integer getReceiptNum() {
        return receiptLst.size();
    }

}