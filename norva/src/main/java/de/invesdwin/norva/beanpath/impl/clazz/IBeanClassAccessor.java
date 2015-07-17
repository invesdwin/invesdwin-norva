package de.invesdwin.norva.beanpath.impl.clazz;

import de.invesdwin.norva.beanpath.impl.clazz.internal.IInternalBeanClassAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

public interface IBeanClassAccessor extends IInternalBeanClassAccessor, IBeanPathAccessor {

    Object invokeFromRoot(Object root, Object... params);

    Object getValueFromRoot(Object root);

    void setValueFromRoot(Object root, Object value);

    @Override
    BeanClassType getType();

    @Override
    BeanClassContext getContext();

    @Override
    BeanClassContainer getContainer();

}
