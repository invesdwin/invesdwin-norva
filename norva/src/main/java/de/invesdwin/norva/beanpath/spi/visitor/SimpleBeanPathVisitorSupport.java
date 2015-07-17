package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.element.AActionBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.APropertyBeanPathElement;
import de.invesdwin.norva.beanpath.spi.element.IBeanPathElement;

@NotThreadSafe
public class SimpleBeanPathVisitorSupport extends ASimpleBeanPathVisitor {

    public SimpleBeanPathVisitorSupport(final ABeanPathContext context) {
        super(context);
    }

    @Override
    public void finish() {}

    @Override
    public void visitProperty(final APropertyBeanPathElement e) {}

    @Override
    public void visitAction(final AActionBeanPathElement e) {}

    @Override
    public void visitOther(final IBeanPathElement e) {}

    @Override
    protected void visitSubElementsOpen() {}

    @Override
    protected void visitSubElementsClose() {}

}
