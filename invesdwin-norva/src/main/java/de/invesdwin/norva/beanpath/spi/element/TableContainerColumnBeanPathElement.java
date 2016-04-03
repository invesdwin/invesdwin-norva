package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.IndexedBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.NoObjectBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

/**
 * Represents a toString() of a table row.
 */
@NotThreadSafe
public class TableContainerColumnBeanPathElement extends APropertyBeanPathElement
        implements ITableColumnBeanPathElement {

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

    @Override
    public boolean isVisible() {
        //toString can not be filtered
        return true;
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

}
