package com.cartera.citi.framework.launcher;

/**
 * Created by Roman_Labyk on 9/30/2015.
 * Encapsulates retrieving configuration
 * values from external environment.
 */

public class EnvironmentArguments {

    public static String getSelectedBrowser() {
        String selectedBrowser = System.getenv("BROWSER");
        if (selectedBrowser == null || selectedBrowser.trim().isEmpty()){
            selectedBrowser ="firefox";
        }
        return selectedBrowser.trim();
    }

    public static String getSelectedConfig() {
        String selectedConfig = System.getenv("CONFIG");
        return (selectedConfig == null || selectedConfig.trim().isEmpty()) ? null : selectedConfig;
    }

    public static String getSelectedSuite() {
        String selectedSuite = System.getenv("SUITE");
        if (selectedSuite == null || selectedSuite.equals("null") || selectedSuite.trim().isEmpty()) {
            selectedSuite = "default";
        }
        return selectedSuite;
    }
}