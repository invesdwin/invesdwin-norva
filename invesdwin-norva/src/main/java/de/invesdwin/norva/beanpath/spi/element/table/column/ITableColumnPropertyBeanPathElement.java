package de.invesdwin.norva.beanpath.spi.element.table.column;

import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

public interface ITableColumnPropertyBeanPathElement extends ITableColumnBeanPathElement {

    IBeanPathPropertyModifier<Object> getModifier(int index);

}
