package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.BeanObjectContext;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class FixedValueBeanPathModifier<E> implements IBeanPathPropertyModifier<E> {

    private final IBeanObjectAccessor accessor;
    private final E fixedValue;

    public FixedValueBeanPathModifier(final IBeanPathAccessor accessor, final E fixedValue) {
        com.google.common.base.Preconditions.checkState(accessor instanceof IBeanObjectAccessor,
                "%s is only available in a %s", IBeanPathPropertyModifier.class.getSimpleName(),
                BeanObjectContext.class.getSimpleName());
        this.accessor = (IBeanObjectAccessor) accessor;
        this.fixedValue = fixedValue;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return accessor;
    }

    @Override
    public void setValue(final E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getValue() {
        return fixedValue;
    }

    @Override
    public void setValueFromRoot(final Object root, final E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getValueFromRoot(final Object root) {
        return fixedValue;
    }

    @Override
    public void setValueFromTarget(final Object target, final E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getValueFromTarget(final Object target) {
        return fixedValue;
    }

}
