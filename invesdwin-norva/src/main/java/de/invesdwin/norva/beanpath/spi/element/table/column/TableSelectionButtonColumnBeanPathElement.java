package de.invesdwin.norva.beanpath.spi.element.table.column;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.ABeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.SelectionBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.table.ATableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

/**
 * Represents a toString() of a table row.
 */
@NotThreadSafe
public class TableSelectionButtonColumnBeanPathElement extends ABeanPathElement
        implements ITableColumnBeanPathElement, IActionBeanPathElement, IPropertyBeanPathElement {

    public static final String COLUMN_ID = "selection";

    private final ATableBeanPathElement tableElement;

    public TableSelectionButtonColumnBeanPathElement(final ATableBeanPathElement tableElement) {
        super(tableElement.getContext(), tableElement.getContainer(), tableElement.getAccessor());
        this.tableElement = tableElement;
    }

    @Override
    public ATableBeanPathElement getTableElement() {
        return tableElement;
    }

    @Override
    public String getFormatString() {
        return tableElement.getFormatString();
    }

    @Override
    public boolean isEager() {
        return tableElement.isEager();
    }

    @Override
    public boolean isVisible() {
        //selection cannot be filtered
        return true;
    }

    @Override
    public boolean isProperty() {
        return true;
    }

    @Override
    public boolean isAction() {
        return true;
    }

    @Override
    public boolean isModalCloser() {
        return false;
    }

    @Override
    public boolean isModalOpener() {
        return false;
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    public SelectionBeanPathPropertyModifier getSelectionModifier() {
        return (SelectionBeanPathPropertyModifier) tableElement.getSelectionModifier();
    }

    @Override
    public String getColumnId() {
        return COLUMN_ID;
    }

    @Override
    public IBeanPathActionInvoker getInvoker() {
        return getSelectionModifier().getToggleSelectionInvoker();
    }

    @Override
    public boolean isInvokerAvailable() {
        //can be used both as invoker, aswell as property
        return true;
    }

    @Override
    public IBeanPathPropertyModifier<Object> getModifier() {
        return tableElement.getModifier();
    }

    @Override
    public boolean isModifierAvailable() {
        //can be used both as invoker, aswell as property
        return true;
    }

}
