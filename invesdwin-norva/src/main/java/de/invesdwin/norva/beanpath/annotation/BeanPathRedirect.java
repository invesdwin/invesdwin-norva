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
 * The name of this element matters, so for it to be introduced properly as a utility element, you have to name it like
 * you would in the redirected target. E.g. the target property is "getSomeBean().getXyz()", then you name the method
 * disableXyz() and annotate it with @BeanPathRedirect("somebean.xyz") to add a hide utility method.
 *
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanPathRedirect {

    /**
     * The path that should be intercepted to be redirected into this element. The bean path is specified relative from
     * the current element. It is denoted as the target, where this element *should* be positioned in the tree.
     */
    String value();

}
