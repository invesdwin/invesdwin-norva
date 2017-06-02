package de.invesdwin.norva.beanpath.spi.element.utility;

import java.lang.annotation.Annotation;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.annotation.BeanPathRedirect;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.element.AActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.simple.SimpleActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class HideBeanPathElement extends AActionBeanPathElement implements IUtilityBeanPathElement {

    public static final String HIDE_PREFIX = "hide";
    private IBeanPathElement attachedToElement;

    public HideBeanPathElement(final SimpleActionBeanPathElement simpleActionElement) {
        super(simpleActionElement);
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
                return BeanPathUtil.maybeAddUtilityPrefix(parent.value(), HIDE_PREFIX);
            }
        };
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
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
