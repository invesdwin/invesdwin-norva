package de.invesdwin.norva.beanpath.spi.element;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ColumnOrderBeanPathElement;

@NotThreadSafe
public abstract class ATableBeanPathElement extends AChoiceBeanPathElement {

    public ATableBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    @Override
    protected boolean isStaticallyDisabled() {
        return false;
    }

    public abstract TableContainerColumnBeanPathElement getContainerColumn();

    public abstract List<TableTextColumnBeanPathElement> getTextColumns();

    public abstract List<TableButtonColumnBeanPathElement> getButtonColumns();

    public abstract ColumnOrderBeanPathElement getColumnOrderElement();

    public abstract List<ITableColumnBeanPathElement> getRawColumns();

    public abstract List<ITableColumnBeanPathElement> getColumns();

}
