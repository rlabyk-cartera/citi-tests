package com.cartera.citi.framework.testdata;

import com.cartera.citi.framework.launcher.EnvironmentArguments;
import com.cartera.citi.framework.logger.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class TestData {

    private Properties properties = new Properties();
    private InputStream inputStream;
    //Thread currentThread = Thread.currentThread();
    //ClassLoader contextClassLoader = currentThread.getContextClassLoader();

    public String getData(String propName) {

        try {
            String env = EnvironmentArguments.getSelectedConfig();
            String name = "/"+env+".properties";
            //String name = "qa.properties";
            //InputStream propertiesStream = contextClassLoader.getResourceAsStream("resource.properties");
            inputStream = this.getClass().getResourceAsStream(name);
            //inputStream = contextClassLoader.getResourceAsStream(name);
            if (inputStream !=null){
                properties.load(inputStream);
            }
            return properties.getProperty(propName);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logStep(e.getMessage().toString());
        }

        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<String> getList(String propName) {
        List<String> dataList = new LinkedList<String>();
        String[] lines = getData(propName).split(";");

        for (String line : lines) {
            dataList.add(line);
        }
        return dataList;
    }

    public String isTestDisabled(String propName) {
        try {
            String suiteName = EnvironmentArguments.getSelectedSuite();
            String name = "/suite-"+suiteName+".properties";
            inputStream = this.getClass().getResourceAsStream(name);
            properties.load(inputStream);
            return properties.getProperty(propName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}