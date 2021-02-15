package de.invesdwin.norva.beanpath.spi.element.table.column.custom;

import java.util.List;

import de.invesdwin.norva.beanpath.spi.element.table.ATableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.ITableColumnBeanPathElement;

/**
 * Implement this interface in the class that contains a table property to customize the columns flexibly.
 */
public interface ICustomTableColumnProvider {

    /**
     * Return a new list with added instances of ACustomTableColumn or reorder/filter existing columns in the new list.
     */
    List<ITableColumnBeanPathElement> customize(ATableBeanPathElement tableElement,
            List<ITableColumnBeanPathElement> orderedColumnsUnmodifiable);

}
