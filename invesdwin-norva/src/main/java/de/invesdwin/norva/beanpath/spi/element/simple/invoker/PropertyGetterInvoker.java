package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
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
        assertNoParamsVarArgs(params);
        return modifier.getValue();
    }

    @Override
    public Object invoke() {
        return modifier.getValue();
    }

    @Override
    public Object invoke(final Object param1) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invoke(final Object param1, final Object param2) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invoke(final Object param1, final Object param2, final Object param3) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invokeFromRoot(final Object... params) {
        assertNoParamsVarArgs(params);
        return modifier.getValueFromRoot(params[0]);
    }

    @Override
    public Object invokeFromRoot(final Object root) {
        return modifier.getValueFromRoot(root);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2, final Object param3) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        assertNoParamsVarArgs(params);
        return modifier.getValueFromTarget(params[0]);
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        return modifier.getValueFromTarget(target);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        throw newParametersNotSupportedException();
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        throw newParametersNotSupportedException();
    }

    private void assertNoParamsVarArgs(final Object... params) {
        if (params.length != 1) {
            throw newParametersNotSupportedException();
        }
    }

    private UnsupportedOperationException newParametersNotSupportedException() {
        return new UnsupportedOperationException("Parameters are not supported in " + getClass().getSimpleName());
    }

}
