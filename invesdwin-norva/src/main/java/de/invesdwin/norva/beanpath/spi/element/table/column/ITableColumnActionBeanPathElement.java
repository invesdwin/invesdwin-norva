package de.invesdwin.norva.beanpath.spi.element.table.column;

import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;

public interface ITableColumnActionBeanPathElement extends ITableColumnBeanPathElement {

    IBeanPathActionInvoker getInvoker(int index);

}
