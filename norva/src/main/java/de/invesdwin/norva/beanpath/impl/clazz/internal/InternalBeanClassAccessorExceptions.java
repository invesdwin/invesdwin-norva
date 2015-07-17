package de.invesdwin.norva.beanpath.impl.clazz.internal;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class InternalBeanClassAccessorExceptions {

    private InternalBeanClassAccessorExceptions() {}

    public static UnsupportedOperationException newNoPublicSetterOrField(final IInternalBeanClassAccessor parent,
            final Object target) {
        return new UnsupportedOperationException("No public setter method or field exists for "
                + parent.getBeanPathFragment() + " in target " + getTargetName(target));
    }

    private static String getTargetName(final Object target) {
        if (target == null) {
            return null;
        } else {
            return target.getClass().getName();
        }
    }

    public static UnsupportedOperationException newNoPublicGetterOrField(final IInternalBeanClassAccessor parent,
            final Object target) {
        return new UnsupportedOperationException("No public getter method or field exists for bean path fragment ["
                + parent.getBeanPathFragment() + "] in target [" + getTargetName(target) + "]");
    }
}
