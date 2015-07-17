package de.invesdwin.norva.beanpath.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines an order for columns in a table. Both text columns and button columns can be mixed freely.
 * 
 * Columns that are not listed here, should become hidden in generated UIs.
 * 
 * The values for each column represent their bean path fragment relative to the bean that defines this annotation.
 * 
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnOrder {

    /**
     * The bean path fragments relative to the bean that defines this annotation which defines the order of the columns.
     */
    String[] value();

}
