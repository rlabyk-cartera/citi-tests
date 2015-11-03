package com.cartera.citi.framework.logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */

public class Logger {

    private static StringBuilder testSteps = new StringBuilder();
    private static StringBuilder human = new StringBuilder();
    private static StringBuilder technical = new StringBuilder();
    private static StringBuilder xml = new StringBuilder();
    private static String customName = new String();
    private static IXmlLogger customLogger = null;

    private static String fullTestName = new String();
    private static ITestResult testResult = null;
    private static ITestContext testContext = null;
    private static String outputDirectory = new String();
    private static String testClassName = new String();
    private static String testName = new String();

    public enum Level {
        ERROR("[ERROR] "),
        FAILURE("[FAILURE] "),
        INFO("[INFO] "),
        WARNING("[WARNING] "),
        STEP("[STEP] ");

        private String level;

        private Level(String level) {
            this.level = level;
        }

        public String getLevel() {
            return this.level;
        }

    }

    public static void setTestContext(final ITestContext context) {
        testContext = context;
        outputDirectory = context.getOutputDirectory();
        Reporter.log(String.format("Starting logging in '%s'", outputDirectory), true);
    }

    public static void startTest(final String fullTestName, final ITestResult testResult, final String testClassName, final String testName) {
        Logger.fullTestName = fullTestName;
        Logger.testResult = testResult;

        Logger.setDetailedName(testClassName, testName);

    }

    private static void setDetailedName(final String testClassName, final String testName) {
        Logger.testClassName = testClassName;
        Logger.testName = testName;
    }

    public static void endTest() {
        writeLogFile(human, "human", "log");
        writeLogFile(technical, "technical", "log");
        writeLogFile(testSteps, "steps", "log");

        human = null;
        technical = null;
        testSteps = null;
    }

    private static void writeLogFile(final StringBuilder source, final String name, final String extension) {
        if (source != null) {
            final File logBrandDirectory = new File(outputDirectory);
            final File logClassDirectory = new File(logBrandDirectory, testClassName);
            final File logDirectory = new File(logClassDirectory, testName);
            logDirectory.mkdirs();
            final File logFile = new File(logDirectory, name + "." + extension);
            try {
                final FileWriter writer = new FileWriter(logFile);
                writer.write(source.toString());
                writer.close();
            } catch (IOException exception) {
                Reporter.log(String.format("Cannot write log to file: '%s'", logFile.toString()), true);
            }
        }
    }

    public static void logHuman(final String message) {
        ensureHuman();
        ensureTechnical();
        appendLn(human, message);
        appendLn(technical, message);
    }

    public static void logHuman(Level level, final String message, boolean logToOutput) {
        logHuman(level.getLevel() + message, logToOutput);
    }

    public static void logHuman(final String message, boolean logToOutput) {
        logHuman(message);
        if (logToOutput)
            logToOutput(message);
    }

    public static void logToOutput(final String message) {
        Reporter.log(getCurrentTime() + message, true);
    }

    public static void logStep(final String message) {
        ensureSteps();
        appendLn(testSteps, Level.STEP.getLevel() + message);
        logHuman(Level.STEP.getLevel() + message, true);
    }

    public static void logTechnical(final String message) {
        ensureTechnical();
        appendLn(technical, message);
    }

    private static String format(final String formatString, final Object[] args) {
        return String.format(formatString, args);
    }

    public static void logHuman(final String message, final Object... args) {
        final String formattedMessage = format(message, args);
        logHuman(formattedMessage);
    }

    public static void logTechnical(final String message, final Object... args) {
        final String formattedMessage = format(message, args);
        logTechnical(formattedMessage);
    }

    private static void appendLn(final StringBuilder log, final String message) {
        final StringBuilder builder = log;
        builder.append(getCurrentTime() + message);
        builder.append("\n");
    }

    private static void ensureTechnical() {
        if (technical == null) {
            technical = new StringBuilder();
        }
    }

    private static void ensureHuman() {
        if (human == null) {
            human = new StringBuilder();
        }
    }

    private static void ensureSteps() {
        if (testSteps == null) {
            testSteps = new StringBuilder();
        }
    }

    public static void initializeCustom(final String name) {
        xml = new StringBuilder();
        customName = name;
        customLogger = new CustomXmlLogger();
    }

    public static IXmlLogger custom() {
        return customLogger;
    }

    public static void flushCustom() {
        writeLogFile(xml, customName, "xml");
    }

    public static String getLogDirectory() {
        return outputDirectory + "//" + testClassName + "//" + testName;
    }

    public interface IXmlLogger {
        void begin(final String name);

        void end();

        void item(String name, String content);
    }

    private static class CustomXmlLogger implements IXmlLogger {

        private final Stack<String> elements = new Stack<String>();

        @Override
        public void begin(String name) {
            appendLn(xml, "<" + name + ">\n");
            elements.push(name);
        }

        @Override
        public void end() {
            final String elementName = elements.pop();
            appendLn(xml, "</" + elementName + ">\n");
        }

        @Override
        public void item(final String name, final String content) {
            appendLn(xml, "<" + name + ">" + content + "</" + name + ">");
        }
    }

    private static String getCurrentTime() {
        long millisec = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM HH:mm:ss");

        Date date = new Date(millisec);

        return "[" + sdf.format(date) + "]";

    }

    public static void captureScreenshot(WebDriver driver) {
        File scrFile = null;
        try {
            scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch (org.openqa.selenium.WebDriverException ex) {
            Logger.logTechnical("[FAILURE] Failed to capture screenshot.");

        }
        try {
            FileUtils.copyFile(scrFile, new File(Logger.getLogDirectory() + "//" + System.currentTimeMillis() + ".png"));
        } catch (Exception e) {
            Logger.logTechnical(e.getMessage());
        }
    }

}
