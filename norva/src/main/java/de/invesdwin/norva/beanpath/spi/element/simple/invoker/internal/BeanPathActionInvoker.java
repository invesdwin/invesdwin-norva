package de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.BeanObjectContext;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;

@NotThreadSafe
public class BeanPathActionInvoker implements IBeanPathActionInvoker {

    private final IBeanObjectAccessor accessor;

    public BeanPathActionInvoker(final IBeanPathAccessor accessor) {
        org.assertj.core.api.Assertions.assertThat(accessor)
                .as("%s is only available in a %s", IBeanPathActionInvoker.class.getSimpleName(),
                        BeanObjectContext.class.getSimpleName())
                .isInstanceOf(IBeanObjectAccessor.class);
        this.accessor = (IBeanObjectAccessor) accessor;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return accessor;
    }

    @Override
    public Object invoke(final Object... params) {
        return accessor.invoke(params);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        return accessor.getBeanClassAccessor().invokeFromRoot(root, params);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return accessor.getBeanClassAccessor().invokeFromTarget(target, params);
    }

}
