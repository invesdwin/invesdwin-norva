package de.invesdwin.norva.beanpath.spi.element.simple;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.object.BeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathContainer;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.element.ABeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.BeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class SimplePropertyBeanPathElement extends ABeanPathElement implements IPropertyBeanPathElement {

    private IBeanPathPropertyModifier<Object> modifier;

    public SimplePropertyBeanPathElement(final ABeanPathContext context, final IBeanPathContainer container,
            final IBeanPathAccessor accessor) {
        super(context, container, accessor);
    }

    @Override
    public boolean isProperty() {
        return true;
    }

    @Override
    public boolean isAction() {
        return false;
    }

    @Override
    public IBeanPathPropertyModifier<Object> getModifier() {
        if (modifier == null) {
            modifier = new BeanPathPropertyModifier(getAccessor());
        }
        return modifier;
    }

    @Override
    public boolean isModifierAvailable() {
        return getAccessor() instanceof BeanObjectAccessor;
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean shouldBeAddedToElementRegistry() {
        return false;
    }

}
