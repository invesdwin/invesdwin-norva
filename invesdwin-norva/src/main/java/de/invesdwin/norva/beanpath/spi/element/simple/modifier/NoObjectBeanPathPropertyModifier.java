package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

@NotThreadSafe
public class NoObjectBeanPathPropertyModifier implements IBeanPathPropertyModifier<Object> {

    private final BeanPathPropertyModifier delegate;

    public NoObjectBeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        this.delegate = new BeanPathPropertyModifier(accessor);
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
    public void setValue(final Object value) {
        throw newUnsupportedOperationException();
    }

    private UnsupportedOperationException newUnsupportedOperationException() {
        return new UnsupportedOperationException(
                "There is no default uniquely defined object for this property: " + getAccessor());
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
