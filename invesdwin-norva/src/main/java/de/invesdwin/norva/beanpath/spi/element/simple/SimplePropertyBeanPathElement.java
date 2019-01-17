package de.invesdwin.norva.beanpath.spi.element.simple;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.annotation.Eager;
import de.invesdwin.norva.beanpath.annotation.Format;
import de.invesdwin.norva.beanpath.annotation.Lazy;
import de.invesdwin.norva.beanpath.impl.object.BeanObjectAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.IBeanPathContainer;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.element.ABeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
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
    public String getFormatString() {
        final Format annotation = getAccessor().getAnnotation(Format.class);
        if (annotation != null) {
            if (org.apache.commons.lang3.StringUtils.isBlank(annotation.value())) {
                throw new IllegalStateException("@" + Format.class.getSimpleName() + " format should not be blank: \""
                        + annotation.value() + "\"");
            }
            return annotation.value();
        }
        return null;
    }

    @Override
    public boolean isEager() {
        IBeanPathElement parent = this;
        while (parent != null) {
            if (!parent.isProperty()) {
                return false;
            }
            if (parent.getAccessor().getAnnotation(Lazy.class) != null) {
                return false;
            }
            if (parent.getAccessor().getAnnotation(Eager.class) != null) {
                return true;
            }
            if (parent.getContainer().getType().getAnnotation(Lazy.class) != null) {
                return false;
            }
            if (parent.getContainer().getType().getAnnotation(Eager.class) != null) {
                return true;
            }
            parent = parent.getParentElement();
        }
        return false;
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
