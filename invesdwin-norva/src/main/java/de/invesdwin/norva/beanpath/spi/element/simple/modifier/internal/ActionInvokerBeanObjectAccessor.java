package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.DelegateBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;

@NotThreadSafe
public class ActionInvokerBeanObjectAccessor extends DelegateBeanObjectAccessor {

    private final ActionInvokerBeanClassAccessor beanClassAccessor;

    public ActionInvokerBeanObjectAccessor(final IBeanPathActionInvoker invoker) {
        super(invoker.getAccessor());
        this.beanClassAccessor = new ActionInvokerBeanClassAccessor(invoker);
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        return beanClassAccessor;
    }

    @Override
    public Object getValue() {
        if (getPublicActionParameterCount() == 0) {
            return invoke();
        } else {
            return null;
        }
    }

    @Override
    public void setValue(final Object value) {
        if (getPublicActionParameterCount() == 1) {
            invoke(value);
        } else {
            throw ActionInvokerBeanClassAccessor.newInvokerHasNoArgumentsException(this, value);
        }
    }

}
