package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class ListWrapperBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<Object>> {

    private final IBeanPathPropertyModifier<Object> delegate;

    public ListWrapperBeanPathPropertyModifier(final IBeanPathPropertyModifier<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public void setValue(final List<Object> value) {
        delegate.setValue(fromList(value));
    }

    private Object fromList(final List<Object> value) {
        if (value.size() > 1) {
            throw new UnsupportedOperationException("Only single selection supported by accessor [" + getAccessor()
                    + "]: " + value);
        } else if (value.isEmpty()) {
            return null;
        } else {
            return value.get(0);
        }
    }

    @Override
    public List<Object> getValue() {
        return wrapWithList(delegate.getValue());
    }

    @Override
    public void setValueFromRoot(final Object root, final List<Object> value) {
        delegate.setValueFromRoot(root, fromList(value));
    }

    @Override
    public List<Object> getValueFromRoot(final Object root) {
        return wrapWithList(delegate.getValueFromRoot(root));
    }

    @Override
    public void setValueFromTarget(final Object target, final List<Object> value) {
        delegate.setValueFromTarget(target, fromList(value));
    }

    @Override
    public List<Object> getValueFromTarget(final Object target) {
        return wrapWithList(delegate.getValueFromTarget(target));
    }

    private List<Object> wrapWithList(final Object value) {
        return Arrays.asList(value);
    }

}
