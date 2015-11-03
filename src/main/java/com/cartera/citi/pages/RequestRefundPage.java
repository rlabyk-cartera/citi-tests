package com.cartera.citi.pages;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.Button;
import com.cartera.citi.framework.elements.Html;
import com.cartera.citi.framework.elements.Message;
import com.cartera.citi.framework.elements.Section;
import com.cartera.citi.framework.logger.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Roman_Labyk on 10/13/2015.
 */
public class RequestRefundPage extends BasePage {

    private final Integer default_track_id = Integer.parseInt(testData.getData("default_track_id"));

    @FindBy(id = "requestRefund")
    private Html html;

    @FindBy(css = ".cpr_headers.cpr_grid>h2")
    private Message headerMsg;

    @FindBy(css = ".cpr_productInfo.cpr_grid")
    private Section productInfoSection;

    @FindBy(css = ".cpr_legend.cpr_grid")
    private Section legendSection;

    //@FindBy(xpath = "//*[@id='cpr_content']//a[contains(text(),'Start Online')]")
    @FindBy(xpath = "//*[@id='cpr_content']//div[1]/a")
    private Button startOnlineBtn;

    //@FindBy(xpath = "//*[@id='cpr_content']//a[contains(text(),'Generate Form')]")
    @FindBy(xpath = "//*[@id='cpr_content']//div[2]/a")
    private Button generateFormBtn;


    public RequestRefundPage(WebDriver driver) {
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

    private String getRequestRefundPageRelativeURL() {
        return testData.getData("request_refund_page_relative_path");
    }

    public void open() {
        Logger.logStep("Opening 'Request Refund Page' with track id: " + default_track_id);
        open(getRequestRefundPageRelativeURL() + default_track_id);
    }

    public void open(Integer track_id) {
        Logger.logStep("Opening 'Request Refund Page' with track id: " + track_id);
        open(getRequestRefundPageRelativeURL() + track_id);
    }

    public String getUrl() {
        return getBaseUrl() + getRequestRefundPageRelativeURL();
    }

    public String getHeaderMessage() {
        headerMsg.waitForElement();
        return headerMsg.getText().trim();
    }

    public List<Section> getSections() {
        List<Section> sectionList = new LinkedList<Section>();
        sectionList.add(productInfoSection);
        sectionList.add(legendSection);
        return sectionList;
    }

    public List<Button> getButtons() {
        List<Button> buttonList = new LinkedList<Button>();
        buttonList.add(startOnlineBtn);
        buttonList.add(generateFormBtn);
        return buttonList;
    }

    public void clickStartOnline() {
        if (!startOnlineBtn.isDisplayed()) {
            startOnlineBtn.waitForElement();
            startOnlineBtn.click();
        } else {
            startOnlineBtn.click();
        }
    }

    public void clickGenerateForm() {
        if (!generateFormBtn.isDisplayed()) {
            generateFormBtn.waitForElement();
        }
        generateFormBtn.click();
    }
}
