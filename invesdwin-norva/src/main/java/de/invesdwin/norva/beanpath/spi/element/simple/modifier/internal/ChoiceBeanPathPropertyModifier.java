package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class ChoiceBeanPathPropertyModifier extends DelegateBeanPathPropertyModifier<List<Object>> {

    public ChoiceBeanPathPropertyModifier(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<Object>> invalidChoiceModifier) {
        super(createDelegate(accessor, invalidChoiceModifier));
    }

    private static IBeanPathPropertyModifier<List<Object>> createDelegate(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<Object>> invalidChoiceModifier) {
        if (accessor.getRawType().isBoolean()) {
            return new BooleanBeanPathPropertyModifier(accessor);
        } else if (accessor.getRawType().isEnum()) {
            return new EnumConstantsBeanPathPropertyModifier(accessor);
        } else if (accessor.getRawType().isArray()) {
            return new ArrayBeanPathPropertyModifier(accessor, invalidChoiceModifier);
        } else if (accessor.getRawType().isIterable()) {
            return new IterableBeanPathPropertyModifier(accessor, invalidChoiceModifier);
        } else {
            throw new IllegalArgumentException(IBeanPathAccessor.class.getSimpleName() + " [" + accessor.toString()
                    + "] does not define a choice. Return type should be a boolean, enum, array or iterable.");
        }
    }

}