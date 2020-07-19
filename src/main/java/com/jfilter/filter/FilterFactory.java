package com.jfilter.filter;

import org.springframework.core.MethodParameter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Filters factory
 */
public abstract class FilterFactory {
    /**
     * List of available filters
     */
    private static final Map<Class<?>, FilterEvent> filterList = initFilterList();

    /**
     * Creates a new instance of the {@link FilterFactory} class.
     *
     * <p>Method is private to prevent of inheritance
     *
     */
    private FilterFactory() {
    }

    /**
     * Filter list initialization
     *
     * @return {@link HashMap} map of filters which can process specified annotations
     */
    private static Map<Class<?>, FilterEvent> initFilterList() {
        Map<Class<?>, FilterEvent> items = new HashMap<>();
        items.put(FieldFilterSetting.class, FieldFilter::new);
        items.put(FieldFilterSettings.class, FieldFilter::new);
        items.put(SessionStrategy.class, StrategyFilter::new);
        items.put(SessionStrategies.class, StrategyFilter::new);
        items.put(FileFilterSetting.class, FileFilter::new);
        return items;
    }

    /**
     * Attempt to find equal annotation in method and in initialized filter list
     *
     * @param methodParameter {@link MethodParameter} method
     * @return {@link Annotation} supported annotation, else null
     */
    public static Annotation getFilterAnnotation(MethodParameter methodParameter) {
        //Search annotation in method
        for (Annotation annotation : methodParameter.getMethod().getDeclaredAnnotations()) {
            if (FilterFactory.filterList.containsKey(annotation.annotationType()))
                return annotation;
        }

        //Search annotation in containing class
        for (Annotation annotation : methodParameter.getContainingClass().getDeclaredAnnotations()) {
            if (FilterFactory.filterList.containsKey(annotation.annotationType()))
                return annotation;
        }
        return null;
    }


    /**
     * Retrieve filter from filter list by annotation defined in method
     *
     * @param methodParameter {@link MethodParameter} method
     * @return object instance of inherited class from {@link BaseFilter}
     */
    public static BaseFilter getFromFactory(MethodParameter methodParameter) {
        Annotation annotation = getFilterAnnotation(methodParameter);

        if (annotation != null) {
            return FilterFactory.filterList
                    .get(annotation.annotationType())
                    .build(methodParameter);
        } else
            return null;
    }

    /**
     * Check if specified annotations, which accepted in FieldFilter or StrategyFilter classes is annotated in method
     *
     * @param methodParameter {@link MethodParameter} Rest method of Rest controller
     * @return true if specified annotations is found
     */
    public static boolean isAccept(MethodParameter methodParameter) {
        //search annotations on method
        for (Annotation annotation : methodParameter.getMethod().getDeclaredAnnotations()) {
            if (FilterFactory.filterList.containsKey(annotation.annotationType())) {
                return true;
            }
        }

        //search annotations on containing class
        for (Annotation annotation : methodParameter.getContainingClass().getDeclaredAnnotations()) {
            if (FilterFactory.filterList.containsKey(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

}
