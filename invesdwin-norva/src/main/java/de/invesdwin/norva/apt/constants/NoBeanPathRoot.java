package de.invesdwin.norva.apt.constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be used to filter out this type for constants generation, when the super type might have included it with
 * BeanPathRoot annotation.
 * 
 * @author subes
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoBeanPathRoot {

}
