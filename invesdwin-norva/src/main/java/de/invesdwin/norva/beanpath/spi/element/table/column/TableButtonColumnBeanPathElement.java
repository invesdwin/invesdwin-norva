package de.invesdwin.norva.beanpath.spi.element.table.column;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.spi.element.AActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IndexedBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.NoObjectBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.table.ATableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TableButtonColumnBeanPathElement extends AActionBeanPathElement
        implements ITableColumnActionBeanPathElement {

    private ATableBeanPathElement tableElement;
    private NoObjectBeanPathActionInvoker invoker;

    public TableButtonColumnBeanPathElement(final SimpleActionBeanPathElement simpleActionElement) {
        super(simpleActionElement);
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
    public IBeanPathActionInvoker getInvoker() {
        if (invoker == null) {
            invoker = new NoObjectBeanPathActionInvoker(getAccessor());
        }
        return invoker;
    }

    @Override
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

    @Override
    public boolean isVisible() {
        return getTableElement().getColumns().contains(this);
    }

    @Override
    public boolean isVisibleFromRoot(final Object root) {
        return getTableElement().getColumnsFromRoot(root).contains(this);
    }

    @Override
    public boolean isVisibleFromTarget(final Object root, final Object target) {
        return getTableElement().getColumnsFromRoot(root).contains(this);
    }

    @Override
    public String getColumnId() {
        return getAccessor().getBeanPathFragment();
    }

}