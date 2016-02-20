package de.invesdwin.norva.beanpath.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used to statically disable an element and all of its sub elements.
 * 
 * Note that you can also use a disableXyz() utility method to dynamically disable your desired element.
 *
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Disabled {

    /**
     * This text will appear as a tooltip on the disabled element.
     */
    String value() default "";

}
