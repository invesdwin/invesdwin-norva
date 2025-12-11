package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

public interface IBeanPathActionInvoker {

    IBeanClassAccessor getBeanClassAccessor();

    IBeanObjectAccessor getBeanObjectAccessor();

    IBeanPathAccessor getAccessor();

    Object invoke();

    Object invoke(Object param1);

    Object invoke(Object param1, Object param2);

    Object invoke(Object param1, Object param2, Object param3);

    /**
     * No object is given here
     */
    Object invoke(Object... params);

    /**
     * No object is given here
     */
    Object invokeViaReflection(Object... params);

    Object invokeFromRoot(Object root);

    Object invokeFromRoot(Object root, Object param1);

    Object invokeFromRoot(Object root, Object param1, Object param2);

    Object invokeFromRoot(Object root, Object param1, Object param2, Object param3);

    /**
     * First param is the root object, can not use target as separate argument here because methodHandles would not work
     * with this
     */
    Object invokeFromRoot(Object... params);

    Object invokeFromRootViaReflection(Object root, Object... params);

    Object invokeFromRoot(Object root, Object... params);

    Object invokeFromTarget(Object target);

    Object invokeFromTarget(Object target, Object param1);

    Object invokeFromTarget(Object target, Object param1, Object param2);

    Object invokeFromTarget(Object target, Object param1, Object param2, Object param3);

    /**
     * First param is the target object, can not use target as separate argument here because methodHandles would not
     * work with this
     */
    Object invokeFromTarget(Object... params);

    Object invokeFromTargetViaReflection(Object target, Object... params);

    Object invokeFromTarget(Object target, Object... params);

}
