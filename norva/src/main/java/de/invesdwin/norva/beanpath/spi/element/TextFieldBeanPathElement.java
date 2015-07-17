package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TextFieldBeanPathElement extends APropertyBeanPathElement {

    public TextFieldBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitTextField(this);
    }

}
