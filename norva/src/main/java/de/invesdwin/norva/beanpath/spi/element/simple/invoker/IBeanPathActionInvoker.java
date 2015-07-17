package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;

public interface IBeanPathActionInvoker {

    IBeanObjectAccessor getAccessor();

    Object invoke(Object... params);

    Object invokeFromRoot(Object root, Object... params);

    Object invokeFromTarget(Object target, Object... params);

}
