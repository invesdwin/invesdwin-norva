package de.invesdwin.norva.beanpath.spi.element.table.column.custom;

import java.util.List;

import de.invesdwin.norva.beanpath.spi.element.table.ATableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.ITableColumnBeanPathElement;

public interface ICustomTableColumnProvider {

    List<ITableColumnBeanPathElement> customize(ATableBeanPathElement tableElement,
            List<ITableColumnBeanPathElement> orderedColumnsUnmodifiable);

}
