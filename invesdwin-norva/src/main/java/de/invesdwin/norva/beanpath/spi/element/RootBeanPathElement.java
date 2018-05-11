package de.invesdwin.norva.beanpath.spi.element;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.RootBeanPathAccessor;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;
import de.invesdwin.norva.beanpath.spi.visitor.IBeanPathVisitor;

@NotThreadSafe
public class RootBeanPathElement extends ABeanPathElement {

    public static final String ROOT_BEAN_PATH = "";

    public RootBeanPathElement(final ABeanPathContext context) {
        super(context, context.getRootContainer(), new RootBeanPathAccessor(context));
        com.google.common.base.Preconditions.checkArgument(ROOT_BEAN_PATH.equals(getBeanPath()));
    }

    @Override
    public boolean isProperty() {
        return false;
    }

    @Override
    public boolean isAction() {
        return false;
    }

    @Override
    public IBeanPathElement getParentElement() {
        return null;
    }

    @Override
    protected void innerAccept(final IBeanPathVisitor visitor) {
        visitor.visitRoot(this);
    }

}
