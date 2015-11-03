package com.cartera.citi.framework.elements;

public interface Element {

    boolean click();

    boolean isDisplayed();

    void setText(String text);

    String getText();

    void clear();

    void clearAndWriteText(String text);

    boolean isSelected();
}
