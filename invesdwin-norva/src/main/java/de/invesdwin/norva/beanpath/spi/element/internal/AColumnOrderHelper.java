package de.invesdwin.norva.beanpath.spi.element.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.annotation.ColumnOrder;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TableRemoveFromButtonColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.TableSelectionButtonColumnBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ColumnOrderBeanPathElement;

@NotThreadSafe
public abstract class AColumnOrderHelper<E> {

    private final IBeanPathElement element;
    private final ColumnOrderBeanPathElement columnOrderElement;
    private final Class<E> columnType;
    private List<E> unchangeableOrderedColumns;

    public AColumnOrderHelper(final IBeanPathElement element, final ColumnOrderBeanPathElement columnOrderElement,
            final Class<E> columnType) {
        this.element = element;
        this.columnOrderElement = columnOrderElement;
        this.columnType = columnType;
    }

    @SuppressWarnings("unchecked")
    public List<E> getOrderedColumns() {
        if (unchangeableOrderedColumns != null) {
            return unchangeableOrderedColumns;
        }
        final List<E> columns = new ArrayList<E>();
        final ColumnOrder annotation = element.getAccessor().getType().getAnnotation(ColumnOrder.class);
        if (annotation == null && columnOrderElement == null) {
            columns.addAll(getRawColumns());
        } else {
            final List<String> beanPathFragments;
            if (columnOrderElement != null) {
                beanPathFragments = columnOrderElement.getColumnOrder();
            } else if (annotation != null) {
                beanPathFragments = Arrays.asList(annotation.value());
            } else {
                throw new IllegalStateException("Should have used unfiltered columns instead of going here!");
            }
            //validate column order and get the appropriate column elements
            for (final String beanPathFragment : beanPathFragments) {
                final String beanPath = element.getBeanPath() + BeanPathUtil.BEAN_PATH_SEPARATOR + beanPathFragment;
                final IBeanPathElement columnElement;
                if (TableRemoveFromButtonColumnBeanPathElement.COLUMN_ID.equals(beanPathFragment)) {
                    columnElement = getRemoveFromButtonColumn();
                } else if (TableSelectionButtonColumnBeanPathElement.COLUMN_ID.equals(beanPathFragment)) {
                    columnElement = getSelectionButtonColumn();
                } else {
                    columnElement = element.getContext().getElementRegistry().getElement(beanPath);
                }
                BeanPathAssertions.checkNotNull(columnElement,
                        "%s in [%s] specifies a column that does not exist: %s", ColumnOrder.class.getSimpleName(),
                        element.getBeanPath(), beanPath);
                BeanPathAssertions.checkState(columnType.isAssignableFrom(columnElement.getClass()),
                        "%s in [%s] specifies a column that is in fact not a column [%s]: %s",
                        ColumnOrder.class.getSimpleName(), element.getBeanPath(), beanPath);
                final E castColumnElement = (E) columnElement;
                BeanPathAssertions.checkState(getAssociatedHolderFromColumn(castColumnElement) == element,
                        "%s in [%s] specifies a column from a different table: %s", ColumnOrder.class.getSimpleName(),
                        element.getBeanPath(), beanPath);
                columns.add(castColumnElement);
            }
            if (columnOrderElement == null) {
                unchangeableOrderedColumns = columns;
            }
        }
        return columns;
    }

    protected abstract IBeanPathElement getRemoveFromButtonColumn();

    protected abstract IBeanPathElement getSelectionButtonColumn();

    protected abstract Collection<? extends E> getRawColumns();

    protected abstract IBeanPathElement getAssociatedHolderFromColumn(E columnElement);

}
