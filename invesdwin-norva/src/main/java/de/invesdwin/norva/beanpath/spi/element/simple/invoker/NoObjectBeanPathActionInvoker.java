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
    public Object invokeViaReflection(final Object... params) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invoke(final Object... params) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invoke() {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invoke(final Object param1) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invoke(final Object param1, final Object param2) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invoke(final Object param1, final Object param2, final Object param3) {
        throw newUnsupportedOperationException();
    }

    private UnsupportedOperationException newUnsupportedOperationException() {
        return new UnsupportedOperationException(
                "There is no default uniquely defined object for this property: " + getAccessor());
    }

    @Override
    public Object invokeFromRootViaReflection(final Object root, final Object... params) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromRoot(final Object... params) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromRoot(final Object root) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2, final Object param3) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object invokeFromTargetViaReflection(final Object target, final Object... params) {
        return delegate.invokeFromTargetViaReflection(target, params);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return delegate.invokeFromTarget(target, params);
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        return delegate.invokeFromTarget(params);
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        return delegate.invokeFromTarget(target);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        return delegate.invokeFromTarget(target, param1);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        return delegate.invokeFromTarget(target, param1, param2);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        return delegate.invokeFromTarget(target, param1, param2, param3);
    }

}
