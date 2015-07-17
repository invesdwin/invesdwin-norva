package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class HiddenBeanPathElement extends ABeanPathElement {

    private final IBeanPathElement originalElement;

    public HiddenBeanPathElement(final IBeanPathElement originalElement) {
        super(originalElement.getContext(), originalElement.getContainer(), originalElement.getAccessor());
        this.originalElement = originalElement;
        //add interceptor after original element has been added
        super.init();
    }

    @Override
    protected void init() {
        //disable init in super constructor
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
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        //might be a container open element for a table element that needs to be skipped
        if (shouldBeAddedToElementRegistry()) {
            visitor.visitHidden(this);
        }
    }

    @Override
    public boolean isVisible(final Object target) {
        return false;
    }

    @Override
    public boolean isEnabled(final Object target) {
        return false;
    }

    @Override
    public boolean shouldBeAddedToElementRegistry() {
        return originalElement.shouldBeAddedToElementRegistry();
    }

    public static IBeanPathElement maybeUnwrap(final IBeanPathElement beanPathElement) {
        if (beanPathElement instanceof HiddenBeanPathElement) {
            final HiddenBeanPathElement hiddenElement = (HiddenBeanPathElement) beanPathElement;
            return hiddenElement.getOriginalElement();
        } else {
            return beanPathElement;
        }
    }

}
