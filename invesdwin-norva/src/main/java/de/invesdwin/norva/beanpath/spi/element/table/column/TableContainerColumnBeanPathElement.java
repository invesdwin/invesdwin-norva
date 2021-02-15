package de.invesdwin.norva.beanpath.spi.element.table.column;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.APropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.ChoiceAsTableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IndexedBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.NoObjectBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

/**
 * Represents a toString() of a table row.
 */
@NotThreadSafe
public class TableContainerColumnBeanPathElement extends APropertyBeanPathElement
        implements ITableColumnPropertyBeanPathElement {

    public static final String COLUMN_ID = "container";

    private final ChoiceAsTableBeanPathElement tableElement;
    private NoObjectBeanPathPropertyModifier modifier;

    public TableContainerColumnBeanPathElement(final ChoiceAsTableBeanPathElement tableElement) {
        super(tableElement.getSimplePropertyElement());
        this.tableElement = tableElement;
    }

    @Override
    public ChoiceAsTableBeanPathElement getTableElement() {
        return tableElement;
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
    public String getColumnId() {
        return COLUMN_ID;
    }

}
