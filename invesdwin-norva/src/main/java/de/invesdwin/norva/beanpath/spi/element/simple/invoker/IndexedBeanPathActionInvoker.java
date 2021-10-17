package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class IndexedBeanPathActionInvoker implements IBeanPathActionInvoker {

    private final IBeanPathPropertyModifier<List<?>> choiceModifier;
    private final int index;
    private final NoObjectBeanPathActionInvoker delegate;

    public IndexedBeanPathActionInvoker(final IBeanPathPropertyModifier<List<?>> choiceModifier, final int index,
            final NoObjectBeanPathActionInvoker delegate) {
        this.choiceModifier = choiceModifier;
        this.index = index;
        this.delegate = delegate;
    }

    @Override
    public IBeanPathAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        return delegate.getBeanClassAccessor();
    }

    @Override
    public IBeanObjectAccessor getBeanObjectAccessor() {
        return delegate.getBeanObjectAccessor();
    }

    @Override
    public Object invoke(final Object... params) {
        final Object targetObject = getIndexedTarget(choiceModifier.getValue());
        switch (params.length) {
        case 0:
            return delegate.invokeFromTarget(targetObject);
        case 1:
            return delegate.invokeFromTarget(targetObject, params[0]);
        case 2:
            return delegate.invokeFromTarget(targetObject, params[0], params[1]);
        case 3:
            return delegate.invokeFromTarget(targetObject, params[0], params[1], params[2]);
        default:
            final Object[] args = new Object[params.length + 1];
            System.arraycopy(params, 0, args, 1, params.length);
            args[0] = targetObject;
            return delegate.invokeFromTarget(args);
        }
    }

    @Override
    public Object invoke() {
        final Object targetObject = getIndexedTarget(choiceModifier.getValue());
        return delegate.invokeFromTarget(targetObject);
    }

    @Override
    public Object invoke(final Object param1) {
        final Object targetObject = getIndexedTarget(choiceModifier.getValue());
        return delegate.invokeFromTarget(targetObject, param1);
    }

    @Override
    public Object invoke(final Object param1, final Object param2) {
        final Object targetObject = getIndexedTarget(choiceModifier.getValue());
        return delegate.invokeFromTarget(targetObject, param1, param2);
    }

    @Override
    public Object invoke(final Object param1, final Object param2, final Object param3) {
        final Object targetObject = getIndexedTarget(choiceModifier.getValue());
        return delegate.invokeFromTarget(targetObject, param1, param2, param3);
    }

    @Override
    public Object invokeFromRoot(final Object... params) {
        final Object target = getIndexedTarget(choiceModifier.getValueFromRoot(params[0]));
        params[0] = target;
        return invokeFromTarget(target, params);
    }

    @Override
    public Object invokeFromRoot(final Object root) {
        final Object target = getIndexedTarget(choiceModifier.getValueFromRoot(root));
        return invokeFromTarget(target);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1) {
        final Object target = getIndexedTarget(choiceModifier.getValueFromRoot(root));
        return invokeFromTarget(target, param1);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2) {
        final Object target = getIndexedTarget(choiceModifier.getValueFromRoot(root));
        return invokeFromTarget(target, param1, param2);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2, final Object param3) {
        final Object target = getIndexedTarget(choiceModifier.getValueFromRoot(root));
        return invokeFromTarget(target, param1, param2, param3);
    }

    private Object getIndexedTarget(final List<?> value) {
        return value.get(index);
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        return delegate.invokeFromTarget(params);
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        return delegate.invokeFromTarget(target);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        return delegate.invokeFromTarget(target, param1);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        return delegate.invokeFromTarget(target, param1, param2);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        return delegate.invokeFromTarget(target, param1, param2, param3);
    }

}
