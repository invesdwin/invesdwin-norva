package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

public interface IBeanPathActionInvoker {

    IBeanClassAccessor getBeanClassAccessor();

    IBeanObjectAccessor getBeanObjectAccessor();

    IBeanPathAccessor getAccessor();

    Object invoke(Object... params);

    Object invokeFromRoot(Object root, Object... params);

    Object invokeFromTarget(Object target, Object... params);

}
