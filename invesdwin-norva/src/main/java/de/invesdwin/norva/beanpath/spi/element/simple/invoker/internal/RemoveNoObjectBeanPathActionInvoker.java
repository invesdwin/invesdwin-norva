package de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

@NotThreadSafe
public class RemoveNoObjectBeanPathActionInvoker extends NoObjectBeanPathActionInvoker {

    public RemoveNoObjectBeanPathActionInvoker(final IBeanPathAccessor accessor) {
        super(accessor);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        com.google.common.base.Preconditions.checkArgument(params.length == 0);
        return super.invokeFromTarget(getAccessor().getContainer().getObject(), target);
    }

}
