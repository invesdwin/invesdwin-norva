package de.invesdwin.norva.beanpath.spi.element.table;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.AChoiceBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.internal.ATableColumnOrderHelper;

@NotThreadSafe
public abstract class ATableBeanPathElement extends AChoiceBeanPathElement {

    private ATableColumnOrderHelper<ITableColumnBeanPathElement> columns;

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

    public abstract List<ITableColumnBeanPathElement> getRawColumns();

    /**
     * These columns are ordered and filtered according to @ColumnOrder or a columnOrder() method.
     */
    public List<ITableColumnBeanPathElement> getColumns() {
        if (columns == null) {
            columns = new ATableColumnOrderHelper<ITableColumnBeanPathElement>(this, getColumnOrderElement(),
                    ITableColumnBeanPathElement.class) {
                @Override
                protected Collection<? extends ITableColumnBeanPathElement> getRawColumns() {
                    return ATableBeanPathElement.this.getRawColumns();
                }

                @Override
                protected IBeanPathElement getAssociatedHolderFromColumn(
                        final ITableColumnBeanPathElement columnElement) {
                    return columnElement.getTableElement();
                }

                @Override
                protected IBeanPathElement getRemoveFromButtonColumn() {
                    return ATableBeanPathElement.this.getRemoveFromButtonColumn();
                }

                @Override
                protected IBeanPathElement getSelectionButtonColumn() {
                    return ATableBeanPathElement.this.getSelectionButtonColumn();
                }
            };
        }
        return Collections.unmodifiableList(columns.getOrderedColumns());
    }

}
