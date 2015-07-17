package de.invesdwin.norva.beanpath.spi.element.simple;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal.PropertyGetterInvoker;

/**
 * This one calls the getter of a property element as an action.
 * 
 */
@NotThreadSafe
public class PropertyGetterActionBeanPathElement extends SimpleActionBeanPathElement {

    private final SimplePropertyBeanPathElement simplePropertyElement;
    private PropertyGetterInvoker invoker;

    public PropertyGetterActionBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement.getContext(), simplePropertyElement.getContainer(),
                simplePropertyElement.getAccessor());
        this.simplePropertyElement = simplePropertyElement;
    }

    public SimplePropertyBeanPathElement getSimplePropertyElement() {
        return simplePropertyElement;
    }

    @Override
    public IBeanPathActionInvoker getInvoker() {
        if (invoker == null) {
            invoker = new PropertyGetterInvoker(simplePropertyElement.getModifier());
        }
        return invoker;
    }

}
