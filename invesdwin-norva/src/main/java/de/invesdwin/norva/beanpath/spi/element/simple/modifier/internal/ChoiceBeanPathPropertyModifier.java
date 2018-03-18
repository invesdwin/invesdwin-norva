package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class ChoiceBeanPathPropertyModifier extends DelegateBeanPathPropertyModifier<List<?>> {

    public ChoiceBeanPathPropertyModifier(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier) {
        super(createDelegate(accessor, invalidChoiceModifier));
    }

    private static IBeanPathPropertyModifier<List<?>> createDelegate(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier) {
        if (accessor.getRawType().isBoolean()) {
            return new BooleanBeanPathPropertyModifier(accessor);
        } else if (accessor.getRawType().isArray()) {
            return new ArrayBeanPathPropertyModifier(accessor, invalidChoiceModifier);
        } else if (accessor.getRawType().isIterable()) {
            return new IterableBeanPathPropertyModifier(accessor, invalidChoiceModifier);
        } else if (accessor.getType().isEnum()) {
            return new EnumConstantsBeanPathPropertyModifier(accessor);
        } else {
            throw new IllegalArgumentException(IBeanPathAccessor.class.getSimpleName() + " [" + accessor.toString()
                    + "] does not define a choice. Return type should be a boolean, enum, array or iterable.");
        }
    }

}
