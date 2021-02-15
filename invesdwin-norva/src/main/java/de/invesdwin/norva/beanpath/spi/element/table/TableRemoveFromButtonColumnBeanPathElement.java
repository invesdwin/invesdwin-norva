package de.invesdwin.norva.beanpath.spi.element.table;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.RemoveIndexedBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.RemoveNoObjectBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.utility.RemoveFromBeanPathElement;

@NotThreadSafe
public class TableRemoveFromButtonColumnBeanPathElement extends TableButtonColumnBeanPathElement {

    public static final String COLUMN_ID = RemoveFromBeanPathElement.REMOVE_FROM_PREFIX;

    private RemoveNoObjectBeanPathActionInvoker invoker;
    private final RemoveFromBeanPathElement removeFromElement;

    public TableRemoveFromButtonColumnBeanPathElement(final RemoveFromBeanPathElement removeFromElement) {
        super(removeFromElement.getSimpleActionElement());
        this.removeFromElement = removeFromElement;
    }

    /**
     * This can be called as if the method resides inside the row model itself.
     */
    @Override
    public IBeanPathActionInvoker getInvoker() {
        if (invoker == null) {
            invoker = new RemoveNoObjectBeanPathActionInvoker(getAccessor());
        }
        return invoker;
    }

    public RemoveFromBeanPathElement getRemoveFromElement() {
        return removeFromElement;
    }

    /**
     * This can be called as if the method resides inside the row model itself.
     */
    @Override
    public IBeanPathActionInvoker getInvoker(final int index) {
        return new RemoveIndexedBeanPathActionInvoker(getTableElement().getChoiceModifier(), index, invoker);
    }

    @Override
    protected boolean shouldAttachToContainerTitleElement() {
        return false;
    }

}
