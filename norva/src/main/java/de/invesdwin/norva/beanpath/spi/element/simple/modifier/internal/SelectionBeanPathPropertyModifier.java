package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class SelectionBeanPathPropertyModifier extends DelegateBeanPathPropertyModifier<List<Object>> {

    public SelectionBeanPathPropertyModifier(final IBeanPathAccessor accessor) {
        super(createDelegate(accessor));
    }

    private static IBeanPathPropertyModifier<List<Object>> createDelegate(final IBeanPathAccessor accessor) {
        if (accessor.getRawType().isArray()) {
            return new ArrayBeanPathPropertyModifier(accessor, null);
        } else if (accessor.getRawType().isIterable()) {
            return new IterableBeanPathPropertyModifier(accessor, null);
        } else {
            return new ListWrapperBeanPathPropertyModifier(new BeanPathPropertyModifier(accessor));
        }
    }

}
