package de.invesdwin.norva.beanpath.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used to change the tree structure via a redirect in the bean path. Quite useful when you want to modify
 * elements of a subtype but cannot add annotations to it (e.g. since it is a database class you want to reuse from a
 * different module that has no dependency to invesdwin-nowicket). One example is to add the Hidden annotation to an
 * element of it or add a disable or validate utility method from the outside.
 * 
 * @author subes
 *
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanPathRedirect {

    /**
     * The path that should be intercepted to be redirected into this element. The bean path is specified relative from
     * the current element.
     */
    String value();

}
