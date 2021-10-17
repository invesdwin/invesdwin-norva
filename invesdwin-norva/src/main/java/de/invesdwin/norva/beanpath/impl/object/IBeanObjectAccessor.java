package de.invesdwin.norva.beanpath.impl.object;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

public interface IBeanObjectAccessor extends IBeanPathAccessor {

    IBeanClassAccessor getBeanClassAccessor();

    @Override
    BeanObjectContainer getContainer();

    @Override
    BeanObjectContext getContext();

    Object getValue();

    void setValue(Object value);

    Object invoke(Object... params);

    Object invoke();

    Object invoke(Object param1);

    Object invoke(Object param1, Object param2);

    Object invoke(Object param1, Object param2, Object param3);

    @Override
    BeanClassType getRawType();

    @Override
    BeanClassType getType();

}
