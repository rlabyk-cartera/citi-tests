package com.cartera.citi.framework.elements;

import com.cartera.citi.framework.actions.Action;
import com.cartera.citi.framework.launcher.Context;
import com.cartera.citi.framework.logger.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectBox extends BaseElement {

    public SelectBox(WebElement webElement) {
        super(webElement);
    }

    public void select(final String valueToSelect) {
        final Select selectableElement = new Select(webElement);
        Context.ajaxWait(new Action() {
            @Override
            public boolean run() {
                selectableElement.selectByValue(valueToSelect);
                return true;
            }
        });
        Logger.logStep("Selected element with value " + valueToSelect);
    }
}
