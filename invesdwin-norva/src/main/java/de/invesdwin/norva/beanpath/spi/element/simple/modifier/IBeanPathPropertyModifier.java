package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;

public interface IBeanPathPropertyModifier<E> {

    IBeanObjectAccessor getAccessor();

    void setValue(E value);

    E getValue();

    void setValueFromRoot(Object root, E value);

    E getValueFromRoot(Object root);

    void setValueFromTarget(Object target, E value);

    E getValueFromTarget(Object target);

}
