package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class InterceptedBeanPathElement extends ABeanPathElement {

    private final IBeanPathElement originalElement;

    public InterceptedBeanPathElement(final IBeanPathElement originalElement) {
        super(originalElement.getContext(), originalElement.getContainer(), originalElement.getAccessor());
        this.originalElement = originalElement;
    }

    public IBeanPathElement getOriginalElement() {
        return originalElement;
    }

    @Override
    public InterceptedBeanPathElement getInterceptedElement() {
        return originalElement.getInterceptedElement();
    }

    @Override
    public boolean isProperty() {
        return originalElement.isProperty();
    }

    @Override
    public boolean isAction() {
        return originalElement.isAction();
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

}
