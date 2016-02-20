package de.invesdwin.norva.beanpath.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used to change the tree structure via a redirect in the bean path.
 * 
 * @author subes
 *
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanPathRedirect {

    /**
     * The path that should be intercepted to be redirected into this element.
     */
    String value();

}
