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
    public Object invoke(final Object... params) {
        return getBeanObjectAccessor().invoke(params);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        return getBeanClassAccessor().invokeFromRoot(root, params);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return getBeanClassAccessor().invokeFromTarget(target, params);
    }

}
