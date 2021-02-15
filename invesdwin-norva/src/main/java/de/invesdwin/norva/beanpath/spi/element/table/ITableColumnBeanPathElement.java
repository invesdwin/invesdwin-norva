package de.invesdwin.norva.beanpath.spi.element.table;

import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;

public interface ITableColumnBeanPathElement extends IBeanPathElement {

    ATableBeanPathElement getTableElement();

    boolean isVisible();

    String getColumnId();

    void setTableElement(ATableBeanPathElement tableElement);

}
