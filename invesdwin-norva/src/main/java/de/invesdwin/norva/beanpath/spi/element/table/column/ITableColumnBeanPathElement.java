package de.invesdwin.norva.beanpath.spi.element.table.column;

import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.ATableBeanPathElement;

public interface ITableColumnBeanPathElement extends IBeanPathElement {

    ATableBeanPathElement getTableElement();

    boolean isVisible();

    String getColumnId();

}
