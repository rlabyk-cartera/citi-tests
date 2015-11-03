package com.cartera.citi.framework.webtestsbase;

import com.cartera.citi.framework.configuration.TestsConfig;
import com.cartera.citi.framework.launcher.EnvironmentArguments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Roman_Labyk on 9/30/2015.
 * Returns web driver {@link org.openqa.selenium.WebDriver} instance, used in all tests.
 * All communications with driver should be done through this class
 */

public class WebDriverFactory {
    private static final long IMPLICIT_WAIT_TIMEOUT = 5;
    //private static WebDriver driver;
    private RemoteWebDriver driver;


    /**
     * Main method of class - it initialize driver and starts browser.
     *
     * @param isLocal - is tests will be started local or not
     */

    public WebDriver getDriver(boolean isLocal) {
        if (driver == null) {
            Browser browser = Browser.getByName(EnvironmentArguments.getSelectedBrowser());
            if (!isLocal) {
                driver = new RemoteWebDriver(CapabilitiesGenerator.getDefaultCapabilities(browser));
            } else {
                switch (browser) {
                    case FIREFOX:
                        driver = new FirefoxDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.FIREFOX));
                        break;
                    case CHROME:
                        //driver = new ChromeDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.CHROME));
                        driver = getChromeDriver();
                        break;
                    case IE10:
                        //driver = new InternetExplorerDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.IE10));
                        driver = getIEDriver();
                        break;
                    default:
                        throw new IllegalStateException("Unsupported browser type");
                }
            }
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
        } else {
            throw new IllegalStateException("Driver has already been initialized. Quit it before using this method");
        }
        return driver;
    }

    private static ChromeDriver getChromeDriver() {
        String driver_name = "/chromedriver.exe";
        File driver_file = new File("driver");
        driver_file.setExecutable(true);
        System.setProperty("webdriver.chrome.driver", driver_file.getPath() + "/" + driver_name);
        ChromeDriver driver = new ChromeDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.CHROME));
        return driver;
    }

    private static InternetExplorerDriver getIEDriver() {
        //System.setProperty("webdriver.ie.driver", "C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe\\");
        InternetExplorerDriver driver = new InternetExplorerDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.IE10));
        return driver;
    }
}