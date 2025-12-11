package de.invesdwin.norva.beanpath.spi.element.simple.invoker;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectContext;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class BeanPathActionInvoker implements IBeanPathActionInvoker {

    private final IBeanPathAccessor accessor;
    private final IBeanClassAccessor beanClassAccessor;
    private final IBeanObjectAccessor beanObjectAccessor;

    public BeanPathActionInvoker(final IBeanPathAccessor accessor) {
        this.accessor = accessor;
        if (accessor instanceof IBeanClassAccessor) {
            this.beanObjectAccessor = null;
            this.beanClassAccessor = (IBeanClassAccessor) accessor;
        } else if (accessor instanceof IBeanObjectAccessor) {
            this.beanObjectAccessor = (IBeanObjectAccessor) accessor;
            this.beanClassAccessor = beanObjectAccessor.getBeanClassAccessor();
        } else {
            this.beanObjectAccessor = null;
            this.beanClassAccessor = null;
        }
    }

    @Override
    public IBeanPathAccessor getAccessor() {
        return accessor;
    }

    @Override
    public IBeanObjectAccessor getBeanObjectAccessor() {
        BeanPathAssertions.checkState(beanObjectAccessor != null, "%s.getObjectAccessor() is only available in a %s",
                IBeanPathPropertyModifier.class.getSimpleName(), BeanObjectContext.class.getSimpleName());
        return beanObjectAccessor;
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        BeanPathAssertions.checkState(beanClassAccessor != null, "%s.getClassAccessor() is only available in a %s",
                IBeanPathPropertyModifier.class.getSimpleName(), BeanClassContext.class.getSimpleName());
        return beanClassAccessor;
    }

    @Override
    public Object invokeViaReflection(final Object... params) {
        return getBeanObjectAccessor().invokeViaReflection(params);
    }

    @Override
    public Object invoke(final Object... params) {
        return getBeanObjectAccessor().invoke(params);
    }

    @Override
    public Object invoke() {
        return getBeanObjectAccessor().invoke();
    }

    @Override
    public Object invoke(final Object param1) {
        return getBeanObjectAccessor().invoke(param1);
    }

    @Override
    public Object invoke(final Object param1, final Object param2) {
        return getBeanObjectAccessor().invoke(param1, param2);
    }

    @Override
    public Object invoke(final Object param1, final Object param2, final Object param3) {
        return getBeanObjectAccessor().invoke(param1, param2, param3);
    }

    @Override
    public Object invokeFromRootViaReflection(final Object root, final Object... params) {
        return getBeanClassAccessor().invokeFromRootViaReflection(root, params);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        return getBeanClassAccessor().invokeFromRoot(root, params);
    }

    @Override
    public Object invokeFromRoot(final Object... params) {
        return getBeanClassAccessor().invokeFromRoot(params);
    }

    @Override
    public Object invokeFromRoot(final Object root) {
        return getBeanClassAccessor().invokeFromRoot(root);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1) {
        return getBeanClassAccessor().invokeFromRoot(root, param1);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2) {
        return getBeanClassAccessor().invokeFromRoot(root, param1, param2);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2, final Object param3) {
        return getBeanClassAccessor().invokeFromRoot(root, param1, param2, param3);
    }

    @Override
    public Object invokeFromTargetViaReflection(final Object target, final Object... params) {
        return getBeanClassAccessor().invokeFromTargetViaReflection(target, params);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return getBeanClassAccessor().invokeFromTarget(target, params);
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        return getBeanClassAccessor().invokeFromTarget(params);
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        return getBeanClassAccessor().invokeFromTarget(target);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        return getBeanClassAccessor().invokeFromTarget(target, param1);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        return getBeanClassAccessor().invokeFromTarget(target, param1, param2);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        return getBeanClassAccessor().invokeFromTarget(target, param1, param2, param3);
    }

}
