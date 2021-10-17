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

    Object invokeFromRoot(Object root);

    Object invokeFromRoot(Object root, Object param1);

    Object invokeFromRoot(Object root, Object param1, Object param2);

    Object invokeFromRoot(Object root, Object param1, Object param2, Object param3);

    /**
     * First param is the root object, can not use target as separate argument here because methodHandles would not work
     * with this
     */
    Object invokeFromRoot(Object... params);

    default Object invokeFromRoot(final Object root, final Object... params) {
        switch (params.length) {
        case 0:
            return invokeFromRoot(root);
        case 1:
            return invokeFromRoot(root, params[0]);
        case 2:
            return invokeFromRoot(root, params[0], params[1]);
        case 3:
            return invokeFromRoot(root, params[0], params[1], params[2]);
        default:
            final Object[] args = new Object[params.length + 1];
            System.arraycopy(params, 0, args, 1, params.length);
            args[0] = root;
            return invokeFromRoot(args);
        }
    }

    Object invokeFromTarget(Object target);

    Object invokeFromTarget(Object target, Object param1);

    Object invokeFromTarget(Object target, Object param1, Object param2);

    Object invokeFromTarget(Object target, Object param1, Object param2, Object param3);

    /**
     * First param is the target object, can not use target as separate argument here because methodHandles would not
     * work with this
     */
    Object invokeFromTarget(Object... params);

    default Object invokeFromTarget(final Object target, final Object... params) {
        switch (params.length) {
        case 0:
            return invokeFromTarget(target);
        case 1:
            return invokeFromTarget(target, params[0]);
        case 2:
            return invokeFromTarget(target, params[0], params[1]);
        case 3:
            return invokeFromTarget(target, params[0], params[1], params[2]);
        default:
            final Object[] args = new Object[params.length + 1];
            System.arraycopy(params, 0, args, 1, params.length);
            args[0] = target;
            return invokeFromTarget(args);
        }
    }

}
