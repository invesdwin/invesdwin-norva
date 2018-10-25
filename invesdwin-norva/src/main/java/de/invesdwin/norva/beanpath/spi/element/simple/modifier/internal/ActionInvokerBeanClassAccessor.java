package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.DelegateBeanClassAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;

@NotThreadSafe
public class ActionInvokerBeanClassAccessor extends DelegateBeanClassAccessor {

    public ActionInvokerBeanClassAccessor(final IBeanPathActionInvoker invoker) {
        super(invoker.getBeanClassAccessor());
    }

    @Override
    public Object getValueFromRoot(final Object root) {
        if (getPublicActionParameterCount() == 0) {
            return invokeFromRoot(root);
        } else {
            return null;
        }
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        if (getPublicActionParameterCount() == 0) {
            return invokeFromTarget(target);
        } else {
            return null;
        }
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        if (getPublicActionParameterCount() == 1) {
            invokeFromRoot(root, value);
        } else {
            throw newInvokerHasNoArgumentsException(this, value);
        }
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        if (getPublicActionParameterCount() == 1) {
            invokeFromTarget(target, value);
        } else {
            throw newInvokerHasNoArgumentsException(this, value);
        }
    }

    public static UnsupportedOperationException newInvokerHasNoArgumentsException(final IBeanPathAccessor accessor,
            final Object value) {
        return new UnsupportedOperationException(
                IBeanPathActionInvoker.class.getSimpleName() + " for " + IBeanPathAccessor.class.getSimpleName() + " ["
                        + accessor + "] has no arguments, thus unable to set value [" + value + "]!");
    }

}
