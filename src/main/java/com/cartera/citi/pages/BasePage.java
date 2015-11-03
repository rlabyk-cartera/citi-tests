package com.cartera.citi.pages;

import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.launcher.EnvironmentArguments;
import com.cartera.citi.framework.testdata.TestData;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected TestData testData;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        testData = Context.getTestData();
    }

    public void open(String relative_url) {
        String url = getBaseUrl() + relative_url;
        navigateTo(url);
    }

    public String getBaseUrl() {
        String protocol = testData.getData("https").equals("yes") ? "https" : "http";
        String baseDomain = testData.getData("base_domain");
        String baseUrl = String.format("%s://%s", protocol, getPrefixForUrl() + baseDomain);
        return baseUrl;
    }

    public String getPrefixForUrl() {
        String prefix = getPrefix();
        if (prefix != null && !prefix.equals("") && !prefix.equals("null")) {
            return prefix.replace(";", "").trim() + ".";
        }
        return "";
    }


    public String getPrefix() {
        String config = EnvironmentArguments.getSelectedConfig();
        if (!config.equalsIgnoreCase("dev")) {
            return "";
        } else {
            String prefix = System.getenv("PREFIX");
            if (prefix != null && !prefix.equals("") && !prefix.equals("null")) {
                return prefix.replace(";", "").trim();
            }
            return "green";
        }
    }

    public void navigateTo(String url) {
        driver.get(url);
        Context.waitForPageLoaded(driver);
    }
}