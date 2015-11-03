package com.cartera.citi.framework.launcher;

import com.cartera.citi.framework.logger.Logger;
import com.cartera.citi.framework.testdata.TestData;
import com.cartera.citi.framework.webtestsbase.WebDriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestListener implements ISuiteListener, ITestListener, IMethodInterceptor {

    @Override
    public void onStart(ISuite iSuite) {

        try {
            Context.testSession = new TestSession(new WebDriverFactory().getDriver(true));
            Context.testSession.getDriver().manage().window().maximize();
            Context.testData = new TestData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ISuite iSuite) {
        try {
            Context.testSession.close();
        } catch (Exception e) {
        } finally {
            Context.testSession.close();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = getTestName(result);
        String testSimpleName = result.getTestClass().getRealClass().getSimpleName();
        Logger.startTest(testName, result, testSimpleName, result.getMethod().getMethodName());
        Reporter.log("\n" + testName + " starting ...", true);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log(getTestName(result) + " [OK] ", true);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log(getTestName(result) + " [FAILED] ", true);
        Logger.logHuman(Logger.Level.ERROR, result.getThrowable().getMessage(), true);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Reporter.log(getTestName(result) + " [SKIPPED] ", true);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
        Logger.setTestContext(context);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
    }

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        List<IMethodInstance> methodsList = new ArrayList<IMethodInstance>(methods.size());

        for (IMethodInstance m : methods) {
            if (!TestSuiteConfiguration.isTestMethodDisabled(m.getMethod().getTestClass().getRealClass().getSimpleName(), m.getMethod().getMethodName())) {
                methodsList.add(m);
            }

        }
        return methodsList;
    }

    private void makeScreenshot() {
        ScreenCapturer capturer = new ScreenCapturer();
        Thread t = new Thread(capturer);
        t.start();
        long startTime = System.currentTimeMillis();
        while (true) {
            if (!t.isAlive())
                break;
            if (System.currentTimeMillis() - startTime > 5000) {
                t.interrupt();
                break;
            }
        }
    }

    private class ScreenCapturer implements Runnable {
        @Override
        public void run() {
            captureScreenshot();
        }
        private void captureScreenshot() {
            File scrFile = null;
            try {
                scrFile = ((TakesScreenshot) Context.getTestSession().getDriver()).getScreenshotAs(OutputType.FILE);
            } catch (org.openqa.selenium.WebDriverException ex) {
                Logger.logTechnical("[FAILURE] Failed to capture screenshot.");
            }
            try {
                File newFile = new File(Logger.getLogDirectory().replace("\\AllTests","") + ".png");
                FileUtils.copyFile(scrFile, newFile);
                scrFile.delete();
                Reporter.setCurrentTestResult(null);
            } catch (Exception e) {
                Logger.logTechnical(e.getMessage());
            }
        }
    }
}