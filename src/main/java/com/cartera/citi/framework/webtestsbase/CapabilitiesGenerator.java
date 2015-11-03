package com.cartera.citi.framework.webtestsbase;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilitiesGenerator {
    /**
     * getting {@link org.openqa.selenium.remote.DesiredCapabilities} object based on browser
     * ATTENTION: you should specify the path to chrome driver executable file to run tests on it(@see <a href="https://sites.google.com/a/chromium.org/chromedriver/getting-started">here for more info</a>)
     * @param browser {@link Browser} object
     * @return capabilites needed for some browsers start
     */
    public static DesiredCapabilities getDefaultCapabilities(Browser browser) {
        switch (browser) {
            case FIREFOX:
                return DesiredCapabilities.firefox();
            case CHROME:
                if (System.getProperty("webdriver.chrome.driver") == null) {
                    throw new IllegalStateException("System variable 'webdriver.chrome.driver' should be set to path for executable driver");
                }
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("test-type");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setCapability("acceptSslCerts", "true");
                return capabilities;
            case IE10:
                if (System.getProperty("webdriver.ie.driver") == null) {
                    throw new IllegalStateException("System variable 'webdriver.ie.driver' should be set to path for executable driver");
                }
                DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
                caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                caps.setJavascriptEnabled(true);
                caps.setVersion("10");
                return caps;
            default:
                throw new IllegalStateException("Browser is not supported");
        }
    }
}