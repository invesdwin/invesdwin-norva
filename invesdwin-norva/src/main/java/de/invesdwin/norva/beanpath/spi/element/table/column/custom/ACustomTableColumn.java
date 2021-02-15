package de.invesdwin.norva.beanpath.spi.element.table.column.custom;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathObjects;
import de.invesdwin.norva.beanpath.spi.element.APropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.table.ATableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.ITableColumnPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.custom.internal.ACustomTableColumnModifier;
import de.invesdwin.norva.beanpath.spi.element.utility.TitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public abstract class ACustomTableColumn<M, V> extends APropertyBeanPathElement
        implements ITableColumnPropertyBeanPathElement {

    private final ATableBeanPathElement tableElement;
    private ACustomTableColumnModifier<V> modifier;

    public ACustomTableColumn(final ATableBeanPathElement tableElement) {
        super(tableElement.getSimplePropertyElement());
        this.tableElement = tableElement;
    }

    @Override
    public final ATableBeanPathElement getTableElement() {
        return tableElement;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final ACustomTableColumnModifier<Object> getModifier() {
        if (modifier == null) {
            modifier = new ACustomTableColumnModifier<V>() {
                @Override
                public V getValueFromTarget(final Object target) {
                    return ACustomTableColumn.this.getValueFromTarget((M) target);
                }

                @Override
                public void setValueFromTarget(final Object target, final V value) {
                    ACustomTableColumn.this.setValueFromTarget((M) target, value);
                }
            };
        }
        return (ACustomTableColumnModifier<Object>) modifier;
    }

    @Override
    public final IBeanPathPropertyModifier<Object> getModifier(final int index) {
        return new de.invesdwin.norva.beanpath.spi.element.simple.modifier.IndexedBeanPathPropertyModifier(
                getTableElement().getChoiceModifier(), index, getModifier());
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected final boolean shouldAttachToContainerTitleElement() {
        return false;
    }

    @Deprecated
    @Override
    public final boolean isVisible(final Object target) {
        return isVisible();
    }

    /**
     * A column is visible if it is in the filtered columnOrder.
     */
    @Override
    public final boolean isVisible() {
        return getTableElement().getColumns().contains(this);
    }

    @Override
    public final TitleBeanPathElement getTitleElement() {
        return null;
    }

    @Override
    public final String getTitle() {
        return getVisibleName();
    }

    @Override
    public final String getVisibleName() {
        return BeanPathObjects.toVisibleName(getColumnId());
    }

    @Override
    public final String getTitle(final Object target) {
        return getTitle();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof ACustomTableColumn) {
            final ACustomTableColumn cObj = (ACustomTableColumn) obj;
            return java.util.Objects.equals(getColumnId(), cObj.getColumnId());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(ACustomTableColumn.class, getColumnId());
    }

    // **************************** Overridable

    protected abstract V getValueFromTarget(M target);

    protected abstract void setValueFromTarget(M target, V value);

    @Override
    public abstract String getColumnId();

    @Override
    public String getFormatString() {
        return null;
    }

}