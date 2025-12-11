package de.invesdwin.norva.beanpath.impl.clazz;

import de.invesdwin.norva.beanpath.impl.clazz.internal.IInternalBeanClassAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

public interface IBeanClassAccessor extends IInternalBeanClassAccessor, IBeanPathAccessor {

    Object invokeFromRootViaReflection(Object root, Object... params);

    Object invokeFromRoot(Object root, Object... params);

    Object invokeFromRoot(Object... params);

    Object invokeFromRoot(Object root);

    Object invokeFromRoot(Object root, Object param1);

    Object invokeFromRoot(Object root, Object param1, Object param2);

    Object invokeFromRoot(Object root, Object param1, Object param2, Object param3);

    Object getValueFromRoot(Object root);

    void setValueFromRoot(Object root, Object value);

    @Override
    BeanClassType getType();

    @Override
    BeanClassContext getContext();

    @Override
    BeanClassContainer getContainer();

}
