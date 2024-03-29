package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.element.IActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IPropertyBeanPathElement;

@NotThreadSafe
public class SimpleBeanPathVisitorSupport extends ASimpleBeanPathVisitor {

    @Override
    public void finish() {
    }

    @Override
    public void visitProperty(final IPropertyBeanPathElement e) {
    }

    @Override
    public void visitAction(final IActionBeanPathElement e) {
    }

    @Override
    public void visitOther(final IBeanPathElement e) {
    }

    @Override
    protected void visitSubElementsOpen() {
    }

    @Override
    protected void visitSubElementsClose() {
    }

    @Override
    protected void visitInvalid(final IBeanPathElement e) {
    }

}
