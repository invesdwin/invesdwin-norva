package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

@NotThreadSafe
public class EnumConstantsBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<?>> {

    private final BeanPathPropertyModifier delegate;

    public EnumConstantsBeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        //explicitly checking type here for enum since the accessor should be for a selectionModifier anyway
        BeanPathAssertions.checkArgument(accessor.getType().isEnum());
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
    public void setValue(final List<?> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<?> getValue() {
        final Object[] array = getBeanClassAccessor().getType().getType().getEnumConstants();
        final List<Object> list = new ArrayList<Object>();
        if (getAccessor().isNullable()) {
            list.add(null);
        }
        list.addAll(Arrays.asList(array));
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
