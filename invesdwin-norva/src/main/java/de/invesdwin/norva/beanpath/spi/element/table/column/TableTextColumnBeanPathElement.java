package de.invesdwin.norva.beanpath.spi.element.table.column;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.spi.element.APropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IndexedBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.NoObjectBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.table.ATableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TableTextColumnBeanPathElement extends APropertyBeanPathElement
        implements ITableColumnPropertyBeanPathElement {

    private ATableBeanPathElement tableElement;
    private NoObjectBeanPathPropertyModifier modifier;

    public TableTextColumnBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    @Override
    public ATableBeanPathElement getTableElement() {
        return tableElement;
    }

    public void setTableElement(final ATableBeanPathElement tableElement) {
        BeanPathAssertions.checkState(this.tableElement == null);
        this.tableElement = tableElement;
    }

    @Override
    public NoObjectBeanPathPropertyModifier getModifier() {
        if (modifier == null) {
            modifier = new NoObjectBeanPathPropertyModifier(getAccessor());
        }
        return modifier;
    }

    @Override
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

    @Override
    public boolean isVisible(final Object target) {
        return getTableElement().getColumnsFromTarget(target).contains(this);
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