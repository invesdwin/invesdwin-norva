package de.invesdwin.norva.beanpath.spi.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.internal.AColumnOrderHelper;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ColumnOrderBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TabbedBeanPathElement extends APropertyBeanPathElement {

    private final List<TabbedColumnBeanPathElement> rawColumns;
    private final List<HiddenBeanPathElement> invalidColumns;
    private ColumnOrderBeanPathElement columnOrderElement;
    private List<TabbedColumnBeanPathElement> columns;

    public TabbedBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement,
            final List<TabbedColumnBeanPathElement> rawColumns, final List<HiddenBeanPathElement> invalidColumns) {
        super(simplePropertyElement);
        this.rawColumns = rawColumns;
        this.invalidColumns = invalidColumns;
    }

    public ColumnOrderBeanPathElement getColumnOrderElement() {
        return columnOrderElement;
    }

    @Override
    protected void beforeFirstAccept() {
        super.beforeFirstAccept();

        this.columnOrderElement = getContext().getElementRegistry().getColumnOrderUtilityElementFor(this);

        //filter columns and add them to the registry before letting visitors visit this table element
        final List<TabbedColumnBeanPathElement> filteredRawColumns = new ArrayList<TabbedColumnBeanPathElement>();
        for (final TabbedColumnBeanPathElement rawColumn : rawColumns) {
            if (getContext().getElementRegistry().getElement(rawColumn.getBeanPath()) == null && rawColumn.accept()) {
                rawColumn.setTabbedElement(this);
                filteredRawColumns.add(rawColumn);
            }
        }
        rawColumns.clear();
        rawColumns.addAll(filteredRawColumns);
        Collections.sort(rawColumns, ABeanPathElement.COMPARATOR);
    }

    @Override
    protected void afterFirstAccept(final IBeanPathVisitor... visitors) {
        super.afterFirstAccept();
        for (final HiddenBeanPathElement invalidColumn : invalidColumns) {
            invalidColumn.accept(visitors);
        }
        invalidColumns.clear();
    }

    public List<TabbedColumnBeanPathElement> getRawColumns() {
        return Collections.unmodifiableList(rawColumns);
    }

    /**
     * These columns are ordered and filtered according to @ColumnOrder order a columnOrder() method.
     */
    public List<TabbedColumnBeanPathElement> getColumns() {
        if (columns == null) {
            columns = new AColumnOrderHelper<TabbedColumnBeanPathElement>(this, getColumnOrderElement(),
                    TabbedColumnBeanPathElement.class) {
                @Override
                protected Collection<? extends TabbedColumnBeanPathElement> getRawColumns() {
                    return TabbedBeanPathElement.this.getRawColumns();
                }

                @Override
                protected IBeanPathElement getAssociatedHolderFromColumn(
                        final TabbedColumnBeanPathElement columnElement) {
                    return columnElement.getTabbedElement();
                }

                @Override
                protected IBeanPathElement getRemoveFromButtonColumn() {
                    //not supported
                    return null;
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
    protected void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitTabbed(this);
    }

}
