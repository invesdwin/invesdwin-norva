package de.invesdwin.norva.beanpath.spi.element;

public interface ITableColumnBeanPathElement extends IBeanPathElement {

    TableBeanPathElement getTableElement();

    boolean isVisible();

}
