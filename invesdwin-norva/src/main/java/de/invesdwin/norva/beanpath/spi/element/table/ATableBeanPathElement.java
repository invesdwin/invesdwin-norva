package de.invesdwin.norva.beanpath.spi.element.table;

import java.util.Collection;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.AChoiceBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.ITableColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.TableButtonColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.TableContainerColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.TableTextColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.table.column.custom.internal.ATableColumnOrderHelper;

@NotThreadSafe
public abstract class ATableBeanPathElement extends AChoiceBeanPathElement {

    private ATableColumnOrderHelper<ITableColumnBeanPathElement> columnsHelper;

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

    public List<ITableColumnBeanPathElement> getColumns() {
        return java.util.Collections.unmodifiableList(getColumnsHelper().getOrderedColumns());
    }

    /**
     * These columns are ordered and filtered according to @ColumnOrder or a columnOrder() method.
     */
    public List<ITableColumnBeanPathElement> getColumnsFromTarget(final Object target) {
        return java.util.Collections.unmodifiableList(getColumnsHelper().getOrderedColumnsFromTarget(target));
    }

    public List<ITableColumnBeanPathElement> getColumnsFromRoot(final Object root) {
        return java.util.Collections.unmodifiableList(getColumnsHelper().getOrderedColumnsFromRoot(root));
    }

    private ATableColumnOrderHelper<ITableColumnBeanPathElement> getColumnsHelper() {
        if (columnsHelper == null) {
            columnsHelper = new ATableColumnOrderHelper<ITableColumnBeanPathElement>(this, getColumnOrderElement(),
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
        return columnsHelper;
    }

}
