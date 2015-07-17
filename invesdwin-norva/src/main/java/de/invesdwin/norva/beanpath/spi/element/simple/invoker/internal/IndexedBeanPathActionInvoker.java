package de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class IndexedBeanPathActionInvoker implements IBeanPathActionInvoker {

    private final IBeanPathPropertyModifier<List<Object>> choiceModifier;
    private final int index;
    private final NoObjectBeanPathActionInvoker delegate;

    public IndexedBeanPathActionInvoker(final IBeanPathPropertyModifier<List<Object>> choiceModifier, final int index,
            final NoObjectBeanPathActionInvoker delegate) {
        this.choiceModifier = choiceModifier;
        this.index = index;
        this.delegate = delegate;
    }

    @Override
    public IBeanObjectAccessor getAccessor() {
        return delegate.getAccessor();
    }

    @Override
    public Object invoke(final Object... params) {
        final Object target = getIndexedTarget(choiceModifier.getValue());
        return invokeFromTarget(target, params);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        final Object target = getIndexedTarget(choiceModifier.getValueFromRoot(root));
        return invokeFromTarget(target, params);
    }

    private Object getIndexedTarget(final List<Object> value) {
        return value.get(index);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return delegate.invokeFromTarget(target, params);
    }

}
