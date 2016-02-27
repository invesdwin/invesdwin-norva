package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.simple.SimplePropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class TabbedColumnBeanPathElement extends APropertyBeanPathElement {

    private TabbedBeanPathElement tabbedElement;

    public TabbedColumnBeanPathElement(final SimplePropertyBeanPathElement simplePropertyElement) {
        super(simplePropertyElement);
    }

    public TabbedBeanPathElement getTabbedElement() {
        return tabbedElement;
    }

    void setTabbedElement(final TabbedBeanPathElement tabbedElement) {
        org.assertj.core.api.Assertions.assertThat(this.tabbedElement).isNull();
        this.tabbedElement = tabbedElement;
    }

    @Override
    protected final void innerAccept(final IBeanPathVisitor visitor) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean isStaticallyDisabled() {
        //columns normally don't have setters
        return false;
    }

}
