package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.BeanObjectContext;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class BeanPathPropertyModifier implements IBeanPathPropertyModifier<Object> {

    private final IBeanObjectAccessor accessor;

    public BeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        org.assertj.core.api.Assertions.assertThat(accessor)
                .as("%s is only available in a %s", IBeanPathPropertyModifier.class.getSimpleName(),
                        BeanObjectContext.class.getSimpleName())
                .isInstanceOf(IBeanObjectAccessor.class);
        this.accessor = (IBeanObjectAccessor) accessor;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return accessor;
    }

    @Override
    public void setValue(final Object value) {
        accessor.setValue(value);
    }

    @Override
    public Object getValue() {
        return accessor.getValue();
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        accessor.getBeanClassAccessor().setValueFromRoot(root, value);
    }

    @Override
    public Object getValueFromRoot(final Object root) {
        return accessor.getBeanClassAccessor().getValueFromRoot(root);
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        accessor.getBeanClassAccessor().setValueFromTarget(target, value);
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        return accessor.getBeanClassAccessor().getValueFromTarget(target);
    }

}
