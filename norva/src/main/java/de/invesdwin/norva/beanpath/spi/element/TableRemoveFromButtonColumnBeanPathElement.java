package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal.RemoveIndexedBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal.RemoveNoObjectBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.utility.RemoveFromBeanPathElement;

@NotThreadSafe
public class TableRemoveFromButtonColumnBeanPathElement extends TableButtonColumnBeanPathElement {

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
