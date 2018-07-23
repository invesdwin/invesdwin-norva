package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class ChoiceBeanPathPropertyModifier extends DelegateBeanPathPropertyModifier<List<?>> {

    public ChoiceBeanPathPropertyModifier(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier, final boolean allowEnum) {
        super(createDelegate(accessor, invalidChoiceModifier, allowEnum));
    }

    private static IBeanPathPropertyModifier<List<?>> createDelegate(final IBeanPathAccessor accessor,
            final IBeanPathPropertyModifier<List<?>> invalidChoiceModifier, final boolean allowEnum) {
        if (accessor.getRawType().isBoolean()) {
            return new BooleanBeanPathPropertyModifier(accessor);
        } else if (allowEnum && accessor.getType().isEnum()) {
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
