package de.invesdwin.norva.beanpath.spi.element.utility;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.APropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class ChoiceBeanPathElement extends APropertyBeanPathElement implements IUtilityBeanPathElement {

    public static final String CHOICE_SUFFIX = "Choice";
    private final boolean shouldBeAddedToElementRegistry;
    private IBeanPathElement attachedToElement;

    public ChoiceBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement,
            final boolean shouldBeAddedToElementRegistry) {
        super(simplePropertyElement);
        this.shouldBeAddedToElementRegistry = shouldBeAddedToElementRegistry;
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean shouldBeAddedToElementRegistry() {
        return shouldBeAddedToElementRegistry;
    }

    @Override
    public void setAttachedToElement(final IBeanPathElement attachedToElement) {
        org.assertj.core.api.Assertions.assertThat(this.attachedToElement).isNull();
        this.attachedToElement = attachedToElement;
    }

    @Override
    public boolean isAttachedToElement() {
        return attachedToElement != null;
    }

}
