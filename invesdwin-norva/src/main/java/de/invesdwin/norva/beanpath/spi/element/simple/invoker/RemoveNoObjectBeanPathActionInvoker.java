package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

@NotThreadSafe
public class RemoveNoObjectBeanPathActionInvoker extends NoObjectBeanPathActionInvoker {

    public RemoveNoObjectBeanPathActionInvoker(final IBeanPathAccessor accessor) {
        super(accessor);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        BeanPathAssertions.checkArgument(params.length == 1);
        return super.invokeFromTarget(target, params);
    }

}
