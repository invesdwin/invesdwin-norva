package de.invesdwin.norva.beanpath.spi.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TableBeanPathElement extends ATableBeanPathElement {

    private final List<TableTextColumnBeanPathElement> textColumns;
    private final List<TableButtonColumnBeanPathElement> buttonColumns;
    private List<ITableColumnBeanPathElement> rawColumns;

    public TableBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement,
            final List<TableTextColumnBeanPathElement> textColumns,
            final List<TableButtonColumnBeanPathElement> buttonColumns) {
        super(simplePropertyElement);
        this.textColumns = textColumns;
        this.buttonColumns = buttonColumns;
    }

    @Override
    public List<TableTextColumnBeanPathElement> getTextColumns() {
        return Collections.unmodifiableList(textColumns);
    }

    @Override
    public List<TableButtonColumnBeanPathElement> getButtonColumns() {
        return Collections.unmodifiableList(buttonColumns);
    }

    @Override
    public TableContainerColumnBeanPathElement getContainerColumn() {
        //not use in real tables
        return null;
    }

    @Override
    protected void beforeFirstAccept() {
        super.beforeFirstAccept();

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
    }

    @Override
    public List<ITableColumnBeanPathElement> getRawColumns() {
        if (rawColumns == null) {
            rawColumns = new ArrayList<ITableColumnBeanPathElement>();
            if (getSelectionButtonColumn() != null) {
                rawColumns.add(getSelectionButtonColumn());
            }
            //use alphabetically sorted order with text first, then buttons
            for (final TableTextColumnBeanPathElement textColumn : getTextColumns()) {
                rawColumns.add(textColumn);
            }
            for (final TableButtonColumnBeanPathElement buttonColumn : getButtonColumns()) {
                rawColumns.add(buttonColumn);
            }
            if (getRemoveFromButtonColumn() != null) {
                rawColumns.add(getRemoveFromButtonColumn());
            }
        }
        return Collections.unmodifiableList(rawColumns);
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitTable(this);
    }

}