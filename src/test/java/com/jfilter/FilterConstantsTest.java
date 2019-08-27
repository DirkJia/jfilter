package com.jfilter;

import org.junit.Test;
import java.lang.reflect.InvocationTargetException;

public class FilterConstantsTest {

    @Test(expected = InvocationTargetException.class)
    public void constructorTest() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        TestUtils.instantiateObject(FilterConstants.class);
    }
}
