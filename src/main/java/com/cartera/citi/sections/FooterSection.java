package com.cartera.citi.sections;

import com.cartera.citi.framework.decorator.CustomFieldDecorator;
import com.cartera.citi.framework.elements.Link;
import com.cartera.citi.framework.launcher.Context;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public class FooterSection extends BaseSection {

    private WebElement webElement;

    @FindBy(id = "#cpr_footer")
    private WebElement footerContainer;

    @FindBy(css = "#cpr_footer a")
    private List<Link> allFooterLinks;

    @FindBy(css = ".cpr_grid>a>img")
    private Link citiLogo;

    public FooterSection() {
        this.webElement = this.footerContainer;
        this.driver = Context.getTestSession().getDriver();
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
    }

    @Override
    public String getSectionName() {
        return "Footer";
    }

    @Override
    public Boolean isAvailable() {
        return footerContainer.isDisplayed();
    }

    public List<String> getLinkNames() {
        List<String> linkNames = new ArrayList<String>();
        for (Link link : allFooterLinks) {
            if (!link.getText().isEmpty()) {
                linkNames.add(link.getText());
            } else {
                if (link.getWebElement().getAttribute("href").contains("citi")) {
                    linkNames.add("CITI");
                }
            }
        }
        return linkNames;
    }
}