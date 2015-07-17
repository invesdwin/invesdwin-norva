package de.invesdwin.norva.beanpath.spi.visitor;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;

@NotThreadSafe
public abstract class ABeanPathVisitor implements IBeanPathVisitor {

    private final ABeanPathContext context;

    public ABeanPathVisitor(final ABeanPathContext context) {
        this.context = context;
    }

    @Override
    public ABeanPathContext getContext() {
        return context;
    }

}
