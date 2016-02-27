package de.invesdwin.norva.beanpath.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a property as a tabbed panel. Its properties that are no bean path endpoints become the tabs (called tabbed
 * columns). The order of those tabs can be specified via @ColumnOrder or a columnOrder() utility method inside the
 * container of the tabs.
 * 
 * You can annotate a property or a type with this annotation.
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Tabbed {

}
