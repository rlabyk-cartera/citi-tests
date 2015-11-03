package com.cartera.citi.framework.launcher;

import com.cartera.citi.framework.testdata.TestData;

public class TestSuiteConfiguration {

    public static boolean isTestMethodDisabled(String testClassName, String methodName) {

        TestData testData = new TestData();

        final String isTestDisabledPropertyName = "test." + testClassName + "." + methodName + ".disabled";

        final String isClassDisabledPropertyName = "test." + testClassName + ".disabled";

        final String isTestDisabled = testData.isTestDisabled(isTestDisabledPropertyName);

        final String isTestClassDisabled = testData.isTestDisabled(isClassDisabledPropertyName);
        return
                ("yes".equalsIgnoreCase(isTestDisabled) || "yes".equalsIgnoreCase(isTestClassDisabled))
                        ||
                        (!("no".equalsIgnoreCase(isTestDisabled) || "no".equalsIgnoreCase(isTestClassDisabled)));
    }

}
