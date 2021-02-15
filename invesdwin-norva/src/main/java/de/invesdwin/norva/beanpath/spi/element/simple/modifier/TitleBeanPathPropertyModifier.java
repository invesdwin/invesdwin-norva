package de.invesdwin.norva.beanpath.spi.element.simple.modifier;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectContainer;
import de.invesdwin.norva.beanpath.impl.object.IBeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;

@NotThreadSafe
public class TitleBeanPathPropertyModifier implements IBeanPathPropertyModifier<String> {

    private final IBeanPathElement element;
    private final IBeanPathAccessor accessor;
    private final IBeanObjectAccessor beanObjectAccessor;
    private final IBeanClassAccessor beanClassAccessor;

    public TitleBeanPathPropertyModifier(final IBeanPathElement element) {
        this.element = element;
        this.accessor = element.getAccessor();
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
    public IBeanClassAccessor getBeanClassAccessor() {
        return beanClassAccessor;
    }

    @Override
    public IBeanObjectAccessor getBeanObjectAccessor() {
        return beanObjectAccessor;
    }

    @Override
    public void setValue(final String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValue() {
        final BeanObjectContainer container = (BeanObjectContainer) element.getContainer();
        return getValueFromTarget(container.getObject());
    }

    @Override
    public void setValueFromRoot(final Object root, final String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValueFromRoot(final Object root) {
        final BeanClassContainer container = (BeanClassContainer) element.getContainer();
        return getValueFromTarget(container.getObjectFromRoot(root));
    }

    @Override
    public void setValueFromTarget(final Object target, final String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getValueFromTarget(final Object target) {
        return element.getTitle(target);
    }

}
