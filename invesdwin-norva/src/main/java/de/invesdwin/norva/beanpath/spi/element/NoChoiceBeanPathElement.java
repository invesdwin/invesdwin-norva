package de.invesdwin.norva.beanpath.spi.element;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.modifier.IBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.simple.modifier.ListWrapperBeanPathPropertyModifier;
import de.invesdwin.norva.beanpath.spi.element.utility.ContainerTitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.DisableBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.HideBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TitleBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.TooltipBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.utility.ValidateBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class NoChoiceBeanPathElement extends AChoiceBeanPathElement {

    private final APropertyBeanPathElement originalElement;

    public NoChoiceBeanPathElement(final APropertyBeanPathElement original) {
        super(original.getSimplePropertyElement());
        this.originalElement = original;
    }

    @Override
    public IBeanPathPropertyModifier<List<?>> getChoiceModifier() {
        return new ListWrapperBeanPathPropertyModifier(getModifier());
    }

    public APropertyBeanPathElement getOriginalElement() {
        return originalElement;
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException("should only be used for redirection");
    }

    /**
     * Make utilities available again
     */

    @Override
    public HideBeanPathElement getHideElement() {
        return originalElement.getHideElement();
    }

    @Override
    public DisableBeanPathElement getDisableElement() {
        return originalElement.getDisableElement();
    }

    @Override
    public ContainerTitleBeanPathElement getContainerTitleElement() {
        return originalElement.getContainerTitleElement();
    }

    @Override
    public TitleBeanPathElement getTitleElement() {
        return originalElement.getTitleElement();
    }

    @Override
    public TooltipBeanPathElement getTooltipElement() {
        return originalElement.getTooltipElement();
    }

    @Override
    public ValidateBeanPathElement getValidateElement() {
        return originalElement.getValidateElement();
    }

}