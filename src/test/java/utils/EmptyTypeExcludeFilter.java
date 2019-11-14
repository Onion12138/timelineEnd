package utils;

import org.springframework.boot.test.autoconfigure.filter.AnnotationCustomizableTypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.util.Collections;
import java.util.Set;

/**
 * 不扫描任何类的filter
 */
public class EmptyTypeExcludeFilter extends AnnotationCustomizableTypeExcludeFilter {
    private final EmptyTest annotation;

    public EmptyTypeExcludeFilter(Class<?> testClass) {
        this.annotation = AnnotatedElementUtils.getMergedAnnotation(testClass, EmptyTest.class);
    }

    @Override
    protected boolean hasAnnotation() {
        return this.annotation != null;
    }

    @Override
    protected ComponentScan.Filter[] getFilters(FilterType type) {
        switch (type) {
            case INCLUDE:
                return this.annotation.includeFilters();
            case EXCLUDE:
                return this.annotation.excludeFilters();
            default:
                throw new IllegalStateException("Unsupported type " + type);
        }
    }

    @Override
    protected boolean isUseDefaultFilters() {
        return this.annotation.useDefaultFilters();
    }

    @Override
    protected Set<Class<?>> getDefaultIncludes() {
        return Collections.emptySet();
    }

    @Override
    protected Set<Class<?>> getComponentIncludes() {
        return Collections.emptySet();
    }
}
