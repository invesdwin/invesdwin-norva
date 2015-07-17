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
        org.assertj.core.api.Assertions.assertThat(params).isEmpty();
        return super.invokeFromTarget(getAccessor().getContainer().getObject(), target);
    }

}
