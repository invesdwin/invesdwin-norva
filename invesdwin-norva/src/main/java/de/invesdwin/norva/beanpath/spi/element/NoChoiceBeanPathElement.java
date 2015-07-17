package de.invesdwin.norva.beanpath.spi.element;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.internal.ListWrapperBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class NoChoiceBeanPathElement extends AChoiceBeanPathElement {

    private final APropertyBeanPathElement original;

    public NoChoiceBeanPathElement(final APropertyBeanPathElement original) {
        super(original.getSimplePropertyElement());
        this.original = original;
    }

    @Override
    public IBeanPathPropertyModifier<List<Object>> getChoiceModifier() {
        return new ListWrapperBeanPathPropertyModifier(getModifier());
    }

    public APropertyBeanPathElement getOriginal() {
        return original;
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException("should only be used for redirection");
    }

}