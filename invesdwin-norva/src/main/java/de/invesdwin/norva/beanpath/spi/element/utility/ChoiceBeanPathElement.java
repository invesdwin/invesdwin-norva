package de.invesdwin.norva.beanpath.spi.element.utility;

import java.lang.annotation.Annotation;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.annotation.BeanPathRedirect;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
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
        superInit();
    }

    @Override
    protected void init() {
        //noop, gets delayed
    }

    private void superInit() {
        super.init();
    }

    @Override
    protected BeanPathRedirect postProcessRedirect(final BeanPathRedirect annotation) {
        final BeanPathRedirect parent = super.postProcessRedirect(annotation);
        return new BeanPathRedirect() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return BeanPathRedirect.class;
            }

            @Override
            public String value() {
                return BeanPathUtil.maybeAddUtilitySuffix(parent.value(), CHOICE_SUFFIX);
            }
        };
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
        BeanPathAssertions.checkState(this.attachedToElement == null);
        this.attachedToElement = attachedToElement;
    }

    @Override
    public boolean isAttachedToElement() {
        return attachedToElement != null;
    }

}
