package utils;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

/**
 * 此注解只启动spring boot但不载入任何bean
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(SpringExtension.class)
@OverrideAutoConfiguration(enabled = false)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@TypeExcludeFilters(EmptyTypeExcludeFilter.class)
@ImportAutoConfiguration
public @interface EmptyTest {

    /**
     * Determines if default filtering should be used with
     * {@link SpringBootApplication @SpringBootApplication}. By default no beans are
     * included.
     *
     * @return if default filters should be used
     * @see #includeFilters()
     * @see #excludeFilters()
     */
    boolean useDefaultFilters() default true;

    /**
     * A set of include filters which can be used to add otherwise filtered beans to the
     * application context.
     *
     * @return include filters to apply
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * A set of exclude filters which can be used to filter beans that would otherwise be
     * added to the application context.
     *
     * @return exclude filters to apply
     */
    ComponentScan.Filter[] excludeFilters() default {};
}
