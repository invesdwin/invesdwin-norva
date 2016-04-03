package de.invesdwin.norva.beanpath.spi.element;

public interface ITableColumnBeanPathElement extends IBeanPathElement {

    ATableBeanPathElement getTableElement();

    boolean isVisible();

    String getColumnId();

}
