package de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectContext;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;

@NotThreadSafe
public class BeanPathPropertyModifier implements IBeanPathPropertyModifier<Object> {

    private final IBeanPathAccessor accessor;
    private final IBeanClassAccessor beanClassAccessor;
    private final IBeanObjectAccessor beanObjectAccessor;

    public BeanPathPropertyModifier(final IBeanPathAccessor accessor) {
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
    public void setValue(final Object value) {
        getBeanObjectAccessor().setValue(value);
    }

    @Override
    public Object getValue() {
        return getBeanObjectAccessor().getValue();
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        getBeanClassAccessor().setValueFromRoot(root, value);
    }

    @Override
    public Object getValueFromRoot(final Object root) {
        return getBeanClassAccessor().getValueFromRoot(root);
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        getBeanClassAccessor().setValueFromTarget(target, value);
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        return getBeanClassAccessor().getValueFromTarget(target);
    }

}
