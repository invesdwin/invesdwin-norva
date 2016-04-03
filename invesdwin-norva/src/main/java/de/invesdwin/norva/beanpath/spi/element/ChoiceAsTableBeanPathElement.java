package de.invesdwin.norva.beanpath.spi.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.utility.ChoiceBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ColumnOrderBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public final class ChoiceAsTableBeanPathElement extends ATableBeanPathElement {

    private final AChoiceBeanPathElement originalElement;

    private final TableContainerColumnBeanPathElement containerColumn;
    private final List<ITableColumnBeanPathElement> columns;

    private ChoiceAsTableBeanPathElement(final AChoiceBeanPathElement originalElement) {
        super(originalElement.getSimplePropertyElement());
        this.originalElement = originalElement;

        this.containerColumn = new TableContainerColumnBeanPathElement(this);

        this.columns = new ArrayList<ITableColumnBeanPathElement>();
        this.columns.add(containerColumn);

        if (getTableRemoveFromButtonColumn() != null) {
            columns.add(getTableRemoveFromButtonColumn());
        }
    }

    public AChoiceBeanPathElement getOriginalElement() {
        return originalElement;
    }

    @Override
    protected void beforeFirstAccept() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TableTextColumnBeanPathElement> getTextColumns() {
        //only used in real tables
        return Collections.emptyList();
    }

    @Override
    public List<TableButtonColumnBeanPathElement> getButtonColumns() {
        //only used in real tables
        return Collections.emptyList();
    }

    @Override
    public ColumnOrderBeanPathElement getColumnOrderElement() {
        //only used in real tables
        return null;
    }

    @Override
    public List<ITableColumnBeanPathElement> getRawColumns() {
        return Collections.unmodifiableList(columns);
    }

    @Override
    public List<ITableColumnBeanPathElement> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    @Override
    public TableContainerColumnBeanPathElement getContainerColumn() {
        return containerColumn;
    }

    public static ATableBeanPathElement maybeWrap(final AChoiceBeanPathElement originalElement) {
        if (originalElement instanceof ATableBeanPathElement) {
            return (ATableBeanPathElement) originalElement;
        } else {
            return new ChoiceAsTableBeanPathElement(originalElement);
        }
    }

    @Override
    public IBeanPathPropertyModifier<Object> getModifier() {
        return originalElement.getModifier();
    }

    /** redirected methods from AChoiceBeanPathElement **/

    /**
     * This modifier supports multi-select. For single select the list is only allowed to have 1 item in it.
     */
    @Override
    public IBeanPathPropertyModifier<List<?>> getSelectionModifier() {
        return originalElement.getSelectionModifier();
    }

    @Override
    public TableRemoveFromButtonColumnBeanPathElement getTableRemoveFromButtonColumn() {
        return originalElement.getTableRemoveFromButtonColumn();
    }

    @Override
    public boolean isChoiceOnly() {
        return originalElement.isChoiceOnly();
    }

    @Override
    public ChoiceBeanPathElement getChoiceElement() {
        return originalElement.getChoiceElement();
    }

    @Override
    public IBeanPathPropertyModifier<List<?>> getChoiceModifier() {
        return originalElement.getChoiceModifier();
    }

}
