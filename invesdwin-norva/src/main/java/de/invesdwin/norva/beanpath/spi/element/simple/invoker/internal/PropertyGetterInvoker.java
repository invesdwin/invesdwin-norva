package de.invesdwin.norva.beanpath.spi.element.simple.invoker.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.invoker.IBeanPathActionInvoker;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class PropertyGetterInvoker implements IBeanPathActionInvoker {

    private final IBeanPathPropertyModifier<?> modifier;

    public PropertyGetterInvoker(final IBeanPathPropertyModifier<?> modifier) {
        this.modifier = modifier;
    }

    @Override
    public IBeanPathAccessor getAccessor() {
        return modifier.getAccessor();
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        return modifier.getBeanClassAccessor();
    }

    @Override
    public IBeanObjectAccessor getBeanObjectAccessor() {
        return modifier.getBeanObjectAccessor();
    }

    @Override
    public Object invoke(final Object... params) {
        assertNoParams(params);
        return modifier.getValue();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        assertNoParams(params);
        return modifier.getValueFromRoot(root);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        assertNoParams(params);
        return modifier.getValueFromTarget(target);
    }

    private void assertNoParams(final Object... params) {
        BeanPathAssertions.checkState(params.length == 0, "Parameters are not supported in %s",
                getClass().getSimpleName());
    }

}
