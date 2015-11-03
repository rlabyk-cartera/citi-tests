package com.cartera.citi.framework.decorator;

import com.cartera.citi.framework.elements.Element;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.*;
import java.util.List;

public class CustomFieldDecorator extends DefaultFieldDecorator {

    public static String s;

    public CustomFieldDecorator(SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Class<Element> decoratableClass = decoratableClass(field);
        if (decoratableClass != null) {
            ElementLocator locator = factory.createLocator(field);
            s = getSelectorFromFindBy(field.getAnnotation(FindBy.class));
            if (locator == null) {
                return null;
            }
            if (List.class.isAssignableFrom(field.getType())) {
                return createList(loader, locator, decoratableClass);
            }
            return createElement(loader, locator, decoratableClass);
        }
        return super.decorate(loader, field);
    }

    @SuppressWarnings("unchecked")
    private Class<Element> decoratableClass(Field field) {

        Class<?> clazz = field.getType();
        if (List.class.isAssignableFrom(clazz)) {

            if (field.getAnnotation(FindBy.class) == null &&
                    field.getAnnotation(FindBys.class) == null) {
                return null;
            }
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) {
                return null;
            }
            clazz = (Class<?>) ((ParameterizedType) genericType).
                    getActualTypeArguments()[0];
        }

        if (Element.class.isAssignableFrom(clazz)) {
            return (Class<Element>) clazz;
        } else {
            return null;
        }
    }

    protected Element createElement(ClassLoader loader,
                                    ElementLocator locator,
                                    Class<Element> clazz) {
        WebElement proxy = proxyForLocator(loader, locator);
        return WrapperFactory.createInstance(clazz, proxy);
    }

    @SuppressWarnings("unchecked")
    protected List<Element> createList(ClassLoader loader,
                                       ElementLocator locator,
                                       Class<Element> clazz) {

        InvocationHandler handler =
                new LocatingCustomElementListHandler(locator, clazz);
        List<Element> elements =
                (List<Element>) Proxy.newProxyInstance(
                        loader, new Class[]{List.class}, handler);
        return elements;
    }


    public String getSelectorFromFindBy(FindBy findByAnnotation) {
        String value;
        value = findByAnnotation.className();
        if (!value.isEmpty()) {
            return "className= \'" + value + "\'";
        }
        value = findByAnnotation.css();
        if (!value.isEmpty()) {
            return "css= \'" + value + "\'";
        }
        value = findByAnnotation.id();
        if (!value.isEmpty()) {
            return "id= \'" + value + "\'";
        }
        value = findByAnnotation.linkText();
        if (!value.isEmpty()) {
            return "linkText= \'" + value + "\'";
        }
        value = findByAnnotation.partialLinkText();
        if (!value.isEmpty()) {
            return "partialLinkText= \'" + value + "\'";
        }
        value = findByAnnotation.tagName();
        if (!value.isEmpty()) {
            return "tagName= \'" + value + "\'";
        }
        value = findByAnnotation.using();
        if (!value.isEmpty()) {
            return "using= \'" + value + "\'";
        }
        value = findByAnnotation.xpath();
        if (!value.isEmpty()) {
            return "xpath= \'" + value + "\'";
        }
        return null;
    }

    public static String getSelector() {
        return s;
    }
}
