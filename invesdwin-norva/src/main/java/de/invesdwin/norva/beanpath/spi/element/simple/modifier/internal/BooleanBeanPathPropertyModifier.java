package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class BooleanBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<?>> {

    private final BeanPathPropertyModifier delegate;

    public BooleanBeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        //explicitly checking rawType here for enum since the accessor should be for a selectionModifier anyway
        com.google.common.base.Preconditions.checkArgument(accessor.getRawType().isBoolean());
        this.delegate = new BeanPathPropertyModifier(accessor);
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public void setValue(final List<?> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getValue() {
        final List<Object> list = new ArrayList<Object>();
        list.add(true);
        list.add(false);
        if (getAccessor().isNullable()) {
            list.add(null);
        }
        return list;
    }

    @Override
    public void setValueFromRoot(final Object root, final List<?> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getValueFromRoot(final Object root) {
        return getValue();
    }

    @Override
    public void setValueFromTarget(final Object target, final List<?> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getValueFromTarget(final Object target) {
        return getValue();
    }

}
