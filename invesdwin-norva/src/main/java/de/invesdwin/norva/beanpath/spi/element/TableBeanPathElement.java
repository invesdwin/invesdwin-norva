package de.invesdwin.norva.beanpath.spi.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.internal.AColumnOrderHelper;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ColumnOrderBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.RemoveFromBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TableBeanPathElement extends AChoiceBeanPathElement {

    private final List<TableTextColumnBeanPathElement> textColumns;
    private final List<TableButtonColumnBeanPathElement> buttonColumns;
    private TableRemoveFromButtonColumnBeanPathElement removeFromButtonColumn;
    private List<ITableColumnBeanPathElement> rawColumns;
    private List<ITableColumnBeanPathElement> columns;
    private ColumnOrderBeanPathElement columnOrderElement;

    public TableBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement,
            final List<TableTextColumnBeanPathElement> textColumns,
            final List<TableButtonColumnBeanPathElement> buttonColumns) {
        super(simplePropertyElement);
        this.textColumns = textColumns;
        this.buttonColumns = buttonColumns;
    }

    public List<TableTextColumnBeanPathElement> getTextColumns() {
        return Collections.unmodifiableList(textColumns);
    }

    public List<TableButtonColumnBeanPathElement> getButtonColumns() {
        return Collections.unmodifiableList(buttonColumns);
    }

    public TableRemoveFromButtonColumnBeanPathElement getRemoveFromButtonColumn() {
        return removeFromButtonColumn;
    }

    public ColumnOrderBeanPathElement getColumnOrderElement() {
        return columnOrderElement;
    }

    @Override
    protected void beforeFirstAccept() {
        super.beforeFirstAccept();

        this.columnOrderElement = getContext().getElementRegistry().getColumnOrderUtilityElementFor(this);

        final RemoveFromBeanPathElement removeFromElement = getContext().getElementRegistry()
                .getRemoveFromUtilityElementFor(this);
        if (removeFromElement != null) {
            this.removeFromButtonColumn = new TableRemoveFromButtonColumnBeanPathElement(removeFromElement);
        }

        //filter columns and add them to the registry before letting visitors visit this table element
        final List<TableTextColumnBeanPathElement> filteredTextColumns = new ArrayList<TableTextColumnBeanPathElement>();
        for (final TableTextColumnBeanPathElement textColumn : textColumns) {
            if (getContext().getElementRegistry().getElement(textColumn.getBeanPath()) == null && textColumn.accept()) {
                textColumn.setTableElement(this);
                filteredTextColumns.add(textColumn);
            }
        }
        textColumns.clear();
        textColumns.addAll(filteredTextColumns);
        Collections.sort(textColumns, ABeanPathElement.COMPARATOR);
        final List<TableButtonColumnBeanPathElement> filteredButtonColumns = new ArrayList<TableButtonColumnBeanPathElement>();
        for (final TableButtonColumnBeanPathElement buttonColumn : buttonColumns) {
            if (getContext().getElementRegistry().getElement(buttonColumn.getBeanPath()) == null
                    && buttonColumn.accept()) {
                buttonColumn.setTableElement(this);
                filteredButtonColumns.add(buttonColumn);
            }
        }
        buttonColumns.clear();
        buttonColumns.addAll(filteredButtonColumns);
        Collections.sort(buttonColumns, ABeanPathElement.COMPARATOR);
        if (removeFromButtonColumn != null) {
            if (removeFromButtonColumn.accept()) {
                removeFromButtonColumn.setTableElement(this);
            } else {
                removeFromButtonColumn = null;
            }
        }
    }

    public List<ITableColumnBeanPathElement> getRawColumns() {
        if (rawColumns == null) {
            rawColumns = new ArrayList<ITableColumnBeanPathElement>();
            //use alphabetically sorted order with text first, then buttons
            for (final TableTextColumnBeanPathElement textColumn : getTextColumns()) {
                rawColumns.add(textColumn);
            }
            for (final TableButtonColumnBeanPathElement buttonColumn : getButtonColumns()) {
                rawColumns.add(buttonColumn);
            }
            if (removeFromButtonColumn != null) {
                rawColumns.add(removeFromButtonColumn);
            }
        }
        return rawColumns;
    }

    /**
     * These columns are ordered and filtered according to @ColumnOrder order a columnOrder() method.
     */
    public List<ITableColumnBeanPathElement> getColumns() {
        if (columns == null) {
            columns = new AColumnOrderHelper<ITableColumnBeanPathElement>(this, getColumnOrderElement(),
                    ITableColumnBeanPathElement.class) {
                @Override
                protected Collection<? extends ITableColumnBeanPathElement> getRawColumns() {
                    return TableBeanPathElement.this.getRawColumns();
                }

                @Override
                protected IBeanPathElement getAssociatedHolderFromColumn(
                        final ITableColumnBeanPathElement columnElement) {
                    return columnElement.getTableElement();
                }
            }.getOrderedColumns();
        }
        return Collections.unmodifiableList(columns);
    }

    @Override
    protected boolean isStaticallyDisabled() {
        return false;
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitTable(this);
    }

}