package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;

public interface IBeanPathPropertyModifier<E> {

    IBeanObjectAccessor getAccessor();

    void setValue(final E value);

    E getValue();

    void setValueFromRoot(final Object root, final E value);

    E getValueFromRoot(final Object root);

    void setValueFromTarget(final Object target, final E value);

    E getValueFromTarget(final Object target);

}
