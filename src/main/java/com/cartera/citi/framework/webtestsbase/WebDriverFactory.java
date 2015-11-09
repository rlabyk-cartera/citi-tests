package com.cartera.citi.framework.webtestsbase;

import com.cartera.citi.framework.launcher.EnvironmentArguments;
import com.cartera.citi.framework.logger.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Roman_Labyk on 9/30/2015.
 * Returns web driver {@link org.openqa.selenium.WebDriver} instance, used in all tests.
 * All communications with driver should be done through this class
 */

public class WebDriverFactory {
    private static final long IMPLICIT_WAIT_TIMEOUT = 5;
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
                        driver = getFirefoxDriver();
                        break;
                    case CHROME:
                        try {
                            driver = getChromeDriver();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case IE10:
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

    private static FirefoxDriver getFirefoxDriver() {
        FirefoxProfile customProfile = new FirefoxProfile();
        customProfile.setPreference("webdriver_accept_untrusted_certs", true);
        customProfile.setPreference("privacy.popup.policy", 0);
        customProfile.setPreference("dom.disable_open_during_load", false);
        customProfile.setPreference("privacy.popups.firstTime", false);
        customProfile.setPreference("dom.disable_beforeunload", true);
        DesiredCapabilities dc = CapabilitiesGenerator.getDefaultCapabilities(Browser.FIREFOX);
        dc.setCapability(FirefoxDriver.PROFILE, customProfile);
        FirefoxDriver driver = new FirefoxDriver(dc);
        return driver;
    }

    private static ChromeDriver getChromeDriver() throws IOException {
        String driver_name = "/chromedriver.exe";
        //ClassLoader loader = ClassLoader.getSystemClassLoader();
        //URL path = loader.getResource("D:/Projects/CARTERA/citi-automation/driver/chromedriver.exe");
        //InputStream stream = WebDriverFactory.class.getClassLoader().getResourceAsStream(driver_name);
        File driver_file = new File("driver");
        driver_file.setExecutable(true);
        //unzipFile(driver_file, stream, driver_name);
        System.setProperty("webdriver.chrome.driver", driver_file.getPath() + "/" + driver_name);
        Logger.logStep("Chrome path: " + driver_file.getPath() + driver_name);
        ChromeDriver driver = new ChromeDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.CHROME));
        return driver;
    }

    private static InternetExplorerDriver getIEDriver() {
        //System.setProperty("webdriver.ie.driver", "/iexplore.exe");
        InternetExplorerDriver driver = new InternetExplorerDriver(CapabilitiesGenerator.getDefaultCapabilities(Browser.IE10));
        return driver;
    }

    public static void unzipFile(File output, InputStream zipStream, String name) throws IOException {
        File toWrite = new File(output, name);
        toWrite.setExecutable(true);

        if (!FileHandler.createDir(toWrite.getParentFile()))
            throw new IOException("Cannot create parent director for: " + name);

        OutputStream out = new BufferedOutputStream(new FileOutputStream(toWrite), 16384);

        try {
            byte[] buffer = new byte[16384];
            int read;

            while ((read = zipStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            out.close();
        }
    }
}