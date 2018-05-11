package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.IndexedBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.NoObjectBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TableTextColumnBeanPathElement extends APropertyBeanPathElement implements ITableColumnBeanPathElement {

    private TableBeanPathElement tableElement;
    private NoObjectBeanPathPropertyModifier modifier;

    public TableTextColumnBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    @Override
    public TableBeanPathElement getTableElement() {
        return tableElement;
    }

    void setTableElement(final TableBeanPathElement tableElement) {
        com.google.common.base.Preconditions.checkState(this.tableElement == null);
        this.tableElement = tableElement;
    }

    @Override
    public NoObjectBeanPathPropertyModifier getModifier() {
        if (modifier == null) {
            modifier = new NoObjectBeanPathPropertyModifier(getAccessor());
        }
        return modifier;
    }

    public IBeanPathPropertyModifier<Object> getModifier(final int index) {
        return new IndexedBeanPathPropertyModifier(getTableElement().getChoiceModifier(), index, getModifier());
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean shouldAttachToContainerTitleElement() {
        return false;
    }

    @Deprecated
    @Override
    public boolean isVisible(final Object target) {
        return isVisible();
    }

    /**
     * A column is visible if it is in the filtered columnOrder.
     */
    @Override
    public boolean isVisible() {
        return getTableElement().getColumns().contains(this);
    }

    @Override
    public String getColumnId() {
        return getAccessor().getBeanPathFragment();
    }

}