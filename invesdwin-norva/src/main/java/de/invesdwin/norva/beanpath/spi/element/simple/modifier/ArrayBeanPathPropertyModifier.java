package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

@NotThreadSafe
public class ArrayBeanPathPropertyModifier implements IBeanPathPropertyModifier<List<?>> {

    private final BeanPathPropertyModifier delegate;
    private final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier;

    public ArrayBeanPathPropertyModifier(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier) {
        BeanPathAssertions.checkArgument(accessor.getRawType().isArray());
        this.delegate = new BeanPathPropertyModifier(accessor);
        this.invalidChoiceModifier = invalidChoiceModifier;
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
        final Object[] array = toArray(value);
        delegate.setValue(array);
    }

    @Override
    public List<?> getValue() {
        final Object[] array = (Object[]) delegate.getValue();
        return toList(array, getInvalidChoices());
    }

    @Override
    public void setValueFromRoot(final Object root, final List<?> value) {
        final Object[] array = toArray(value);
        delegate.setValueFromRoot(root, array);
    }

    @Override
    public List<?> getValueFromRoot(final Object root) {
        final Object[] array = (Object[]) delegate.getValueFromRoot(root);
        return toList(array, getInvalidChoicesFromRoot(root));
    }

    @Override
    public void setValueFromTarget(final Object target, final List<?> value) {
        final Object[] array = toArray(value);
        delegate.setValueFromTarget(target, array);
    }

    @Override
    public List<?> getValueFromTarget(final Object target) {
        final Object[] array = (Object[]) delegate.getValueFromTarget(target);
        return toList(array, getInvalidChoicesFromTarget(target));
    }

    private Object[] toArray(final List<?> value) {
        if (value == null) {
            return null;
        } else {
            try {
                final Object[] newArray = (Object[]) java.lang.reflect.Array
                        .newInstance(getBeanClassAccessor().getType().getType(), 0);
                return value.toArray(newArray);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<?> toList(final Object[] array, final List<?> invalidChoices) {
        if (array == null) {
            if (invalidChoices != null && !invalidChoices.isEmpty()) {
                return new ArrayList<Object>(invalidChoices);
            } else {
                return new ArrayList<Object>();
            }
        }
        final List<Object> list = new ArrayList<Object>(java.util.Arrays.asList(array));
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

    private List<?> getInvalidChoices() {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValue();
        } else {
            return null;
        }
    }

    private List<?> getInvalidChoicesFromRoot(final Object root) {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValueFromRoot(root);
        } else {
            return null;
        }
    }

    private List<?> getInvalidChoicesFromTarget(final Object target) {
        if (invalidChoiceModifier != null) {
            return invalidChoiceModifier.getValueFromTarget(target);
        } else {
            return null;
        }
    }

}
