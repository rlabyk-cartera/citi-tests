package com.cartera.citi.framework.configuration.properties;

import com.cartera.citi.framework.configuration.TestsConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Roman_Labyk on 9/30/2015.
 * Annotates field of {@link TestsConfig} fields for setting to this field
 * value from properties - system or file(about file you could find information in {@link PropertyFile} class javadoc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {
    String value() default "";
}
