package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class ArrayBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<Object>> {

    private final BeanPathPropertyModifier delegate;
    private final IBeanPathPropertyModifier<List<Object>> invalidChoiceModifier;

    public ArrayBeanPathPropertyModifier(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<Object>> invalidChoiceModifier) {
        org.assertj.core.api.Assertions.assertThat(accessor.getRawType().isArray()).isTrue();
        this.delegate = new BeanPathPropertyModifier(accessor);
        this.invalidChoiceModifier = invalidChoiceModifier;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public void setValue(final List<Object> value) {
        final Object[] array = toArray(value);
        delegate.setValue(array);
    }

    @Override
    public List<Object> getValue() {
        final Object[] array = (Object[]) delegate.getValue();
        return toList(array, getInvalidChoices());
    }

    @Override
    public void setValueFromRoot(final Object root, final List<Object> value) {
        final Object[] array = toArray(value);
        delegate.setValueFromRoot(root, array);
    }

    @Override
    public List<Object> getValueFromRoot(final Object root) {
        final Object[] array = (Object[]) delegate.getValueFromRoot(root);
        return toList(array, getInvalidChoicesFromRoot(root));
    }

    @Override
    public void setValueFromTarget(final Object target, final List<Object> value) {
        final Object[] array = toArray(value);
        delegate.setValueFromTarget(target, array);
    }

    @Override
    public List<Object> getValueFromTarget(final Object target) {
        final Object[] array = (Object[]) delegate.getValueFromTarget(target);
        return toList(array, getInvalidChoicesFromTarget(target));
    }

    private Object[] toArray(final List<Object> value) {
        if (value == null) {
            return null;
        } else {
            return value.toArray();
        }
    }

    private List<Object> toList(final Object[] array, final List<Object> invalidChoices) {
        if (array == null) {
            if (invalidChoices != null && !invalidChoices.isEmpty()) {
                return new ArrayList<Object>(invalidChoices);
            } else {
                return new ArrayList<Object>();
            }
        }
        final List<Object> list = new ArrayList<Object>(Arrays.asList(array));
        if (invalidChoices != null) {
            final List<Object> filtedInvalidChoices = new ArrayList<Object>();
            for (final Object invalidChoice : invalidChoices) {
                if (invalidChoiceModifier != null && !list.contains(invalidChoice)
                        && (invalidChoice != null || invalidChoiceModifier.getAccessor().isNullable())) {
                    filtedInvalidChoices.add(invalidChoice);
                }
            }
            list.addAll(0, filtedInvalidChoices);
        }
        return list;
    }

    private List<Object> getInvalidChoices() {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValue();
        } else {
            return null;
        }
    }

    private List<Object> getInvalidChoicesFromRoot(final Object root) {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValueFromRoot(root);
        } else {
            return null;
        }
    }

    private List<Object> getInvalidChoicesFromTarget(final Object target) {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValueFromTarget(target);
        } else {
            return null;
        }
    }

}
