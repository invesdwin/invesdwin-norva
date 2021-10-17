package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class RemoveIndexedBeanPathActionInvoker extends IndexedBeanPathActionInvoker {

    public RemoveIndexedBeanPathActionInvoker(final IBeanPathPropertyModifier<List<?>> choiceModifier, final int index,
            final NoObjectBeanPathActionInvoker delegate) {
        super(choiceModifier, index, delegate);
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        BeanPathAssertions.checkArgument(params.length == 1);
        return super.invokeFromTarget(getBeanObjectAccessor().getContainer().getObject(), params[0]);
    }

}
