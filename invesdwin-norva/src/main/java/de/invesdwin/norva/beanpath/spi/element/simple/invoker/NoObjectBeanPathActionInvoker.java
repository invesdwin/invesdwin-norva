package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

@NotThreadSafe
public class NoObjectBeanPathActionInvoker implements IBeanPathActionInvoker {

    private final BeanPathActionInvoker delegate;

    public NoObjectBeanPathActionInvoker(final IBeanPathAccessor accessor) {
        this.delegate = new BeanPathActionInvoker(accessor);
    }

    @Override
    public IBeanPathAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        return delegate.getBeanClassAccessor();
    }

    @Override
    public IBeanObjectAccessor getBeanObjectAccessor() {
        return delegate.getBeanObjectAccessor();
    }

    @Override
    public Object invoke(final Object... params) {
        throw newUnsupportedOperationException();
    }

    private UnsupportedOperationException newUnsupportedOperationException() {
        return new UnsupportedOperationException(
                "There is no default uniquely defined object for this property: " + getAccessor());
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return delegate.invokeFromTarget(target, params);
    }

}
