package de.invesdwin.norva.beanpath.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Hides an element in the visitor, to not treat it as a property or action as it normally would be.
 * 
 * Note that you can also dynamically hide elements by adding a hideXyz() utility method for your desired element.
 *
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Hidden {

    /**
     * Use skip=true to prevent the processor from even visiting this element as a hidden element. Thus skipping it
     * entirely and pretending it does not exist at all (useful for framework internal elements of base classes).
     */
    boolean skip() default false;

}
