package com.cartera.suites.ui.smoke;

import com.cartera.citi.bo.RequestRefundBO;
import com.cartera.citi.framework.launcher.BaseTestClass;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Roman_Labyk on 10/28/2015.
 */
public class RequestRefundPageSmokeITCase extends BaseTestClass {

    private RequestRefundBO requestRefundBO;

    @BeforeClass
    public void setUp() {
        requestRefundBO = new RequestRefundBO(getDriver());
    }

    @Test(timeOut = 300000)
    public void verifyNavigationToRequestRefundPage() {
        requestRefundBO.loginAndNavigateToRequestRefundPage();
        requestRefundBO.checkNavigationToToRequestRefundPage();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyHeaderMessage() {
        requestRefundBO.loginAndNavigateToRequestRefundPage();
        requestRefundBO.checkHeaderMsg();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifySections() {
        requestRefundBO.loginAndNavigateToRequestRefundPage();
        requestRefundBO.checkSections();
        Logger.logStep("Verification passed.");
    }

    @Test(timeOut = 300000)
    public void verifyButtons() {
        requestRefundBO.loginAndNavigateToRequestRefundPage();
        requestRefundBO.checkButtons();
        Logger.logStep("Verification passed.");
    }

    @AfterClass
    public void tearDown() {
        Context.getTestSession().clearCookies();
    }
}