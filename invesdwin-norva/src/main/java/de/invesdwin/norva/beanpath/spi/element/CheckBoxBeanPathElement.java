package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class CheckBoxBeanPathElement extends AChoiceBeanPathElement {

    public CheckBoxBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    @Override
    public IBeanPathPropertyModifier<Object> getModifier() {
        return getSimplePropertyElement().getModifier();
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitCheckBox(this);
    }

}