package com.cartera.citi.framework.exceptions;

/**
 * Created by Roman_Labyk on 9/30/2015.
 */
public class TestsConfigurationException extends RuntimeException {

    public TestsConfigurationException(String message) {
        super(message);
    }

    public TestsConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
