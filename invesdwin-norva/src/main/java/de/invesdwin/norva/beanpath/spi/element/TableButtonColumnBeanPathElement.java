package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal.IndexedBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal.NoObjectBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TableButtonColumnBeanPathElement extends AActionBeanPathElement implements ITableColumnBeanPathElement {

    private ATableBeanPathElement tableElement;
    private NoObjectBeanPathActionInvoker invoker;

    public TableButtonColumnBeanPathElement(final SimpleActionBeanPathElement simpleActionElement) {
        super(simpleActionElement);
    }

    @Override
    public ATableBeanPathElement getTableElement() {
        return tableElement;
    }

    void setTableElement(final ATableBeanPathElement tableElement) {
        com.google.common.base.Preconditions.checkState(this.tableElement == null);
        this.tableElement = tableElement;
    }

    @Override
    public IBeanPathActionInvoker getInvoker() {
        if (invoker == null) {
            invoker = new NoObjectBeanPathActionInvoker(getAccessor());
        }
        return invoker;
    }

    public IBeanPathActionInvoker getInvoker(final int index) {
        return new IndexedBeanPathActionInvoker(getTableElement().getChoiceModifier(), index, invoker);
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

    @Override
    public boolean isVisible() {
        return getTableElement().getColumns().contains(this);
    }

    @Override
    public String getColumnId() {
        return getAccessor().getBeanPathFragment();
    }

}