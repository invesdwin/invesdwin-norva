package de.invesdwin.norva.beanpath.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation represents the visible name of a property, button or container.
 * 
 * This value is used to override the default generated title which is written into property files for
 * internationalization. After that, the properties files have a higher priority.
 * 
 * Only title() or xyzTitle() methods have higher priority than properties.
 * 
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Title {

    String value();

}
