package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class EnumConstantsBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<Object>> {

    private final BeanPathPropertyModifier delegate;

    public EnumConstantsBeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        //explicitly checking rawType here for enum since the accessor should be for a selectionModifier anyway
        org.assertj.core.api.Assertions.assertThat(accessor.getRawType().isEnum()).isTrue();
        this.delegate = new BeanPathPropertyModifier(accessor);
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public void setValue(final List<Object> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Object> getValue() {
        final Object[] array = getAccessor().getRawType().getType().getEnumConstants();
        final List<Object> list = new ArrayList<Object>();
        if (getAccessor().isNullable()) {
            list.add(null);
        }
        list.addAll(Arrays.asList(array));
        return list;
    }

    @Override
    public void setValueFromRoot(final Object root, final List<Object> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Object> getValueFromRoot(final Object root) {
        return getValue();
    }

    @Override
    public void setValueFromTarget(final Object target, final List<Object> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Object> getValueFromTarget(final Object target) {
        return getValue();
    }

}
