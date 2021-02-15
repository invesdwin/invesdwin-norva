package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import java.util.concurrent.Callable;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectContext;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;

@NotThreadSafe
public class CallableValueBeanPathModifier<E> implements IBeanPathPropertyModifier<E> {

    private final Callable<E> callableValue;
    private final IBeanPathAccessor accessor;
    private final IBeanObjectAccessor beanObjectAccessor;
    private final IBeanClassAccessor beanClassAccessor;

    public CallableValueBeanPathModifier(final IBeanPathAccessor accessor, final Callable<E> callableValue) {
        this.callableValue = callableValue;
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
    public void setValue(final E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getValue() {
        try {
            return callableValue.call();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setValueFromRoot(final Object root, final E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getValueFromRoot(final Object root) {
        try {
            return callableValue.call();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setValueFromTarget(final Object target, final E value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E getValueFromTarget(final Object target) {
        try {
            return callableValue.call();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
