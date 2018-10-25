package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class DelegateBeanPathPropertyModifier<E> implements IBeanPathPropertyModifier<E> {

    private final IBeanPathPropertyModifier<E> delegate;

    public DelegateBeanPathPropertyModifier(final IBeanPathPropertyModifier<E> delegate) {
        this.delegate = delegate;
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
    public void setValue(final E value) {
        delegate.setValue(value);
    }

    @Override
    public E getValue() {
        return delegate.getValue();
    }

    @Override
    public void setValueFromRoot(final Object root, final E value) {
        delegate.setValueFromRoot(root, value);
    }

    @Override
    public E getValueFromRoot(final Object root) {
        return delegate.getValueFromRoot(root);
    }

    @Override
    public void setValueFromTarget(final Object target, final E value) {
        delegate.setValueFromTarget(target, value);
    }

    @Override
    public E getValueFromTarget(final Object target) {
        return delegate.getValueFromTarget(target);
    }
}
