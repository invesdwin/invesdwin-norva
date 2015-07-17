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

    @Override
    BeanClassType getRawType();

    @Override
    BeanClassType getType();

}
