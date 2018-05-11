package de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class RemoveIndexedBeanPathActionInvoker extends IndexedBeanPathActionInvoker {

    public RemoveIndexedBeanPathActionInvoker(final IBeanPathPropertyModifier<List<?>> choiceModifier, final int index,
            final NoObjectBeanPathActionInvoker delegate) {
        super(choiceModifier, index, delegate);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        com.google.common.base.Preconditions.checkArgument(params.length == 0);
        return super.invokeFromTarget(getAccessor().getContainer().getObject(), target);
    }

}
