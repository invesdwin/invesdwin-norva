package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class ContainerOpenBeanPathElement extends APropertyBeanPathElement {

    private final boolean shouldBeAddedToElementRegistry;

    public ContainerOpenBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement,
            final boolean shouldBeAddedToElementRegistry) {
        super(simplePropertyElement);
        this.shouldBeAddedToElementRegistry = shouldBeAddedToElementRegistry;
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitContainerOpen(this);
    }

    @Override
    public boolean shouldBeAddedToElementRegistry() {
        return shouldBeAddedToElementRegistry;
    }

    @Override
    protected boolean isStaticallyDisabled() {
        //we don't care about public setters for container open elements
        return false;
    }

}
