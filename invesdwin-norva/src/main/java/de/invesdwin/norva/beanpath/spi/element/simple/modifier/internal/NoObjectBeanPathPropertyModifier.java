package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class NoObjectBeanPathPropertyModifier implements IBeanPathPropertyModifier<Object> {

    private final BeanPathPropertyModifier delegate;

    public NoObjectBeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        this.delegate = new BeanPathPropertyModifier(accessor);
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public void setValue(final Object value) {
        throw newUnsupportedOperationException();
    }

    private UnsupportedOperationException newUnsupportedOperationException() {
        return new UnsupportedOperationException("There is no default uniquely defined object for this property: "
                + getAccessor());
    }

    @Override
    public Object getValue() {
        throw newUnsupportedOperationException();
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        throw newUnsupportedOperationException();
    }

    @Override
    public Object getValueFromRoot(final Object root) {
        throw newUnsupportedOperationException();
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        delegate.setValueFromTarget(target, value);
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        return delegate.getValueFromTarget(target);
    }

}
