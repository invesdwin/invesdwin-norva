package de.invesdwin.norva.beanpath.spi.context;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.IBeanPathContainer;

@NotThreadSafe
public abstract class ABeanPathContext {
    private final BeanPathElementRegistry elementRegistry;
    private final IBeanPathContainer rootContainer;

    public ABeanPathContext(final IBeanPathContainer rootContainer) {
        this.rootContainer = rootContainer;
        this.elementRegistry = new BeanPathElementRegistry();
    }

    public IBeanPathContainer getRootContainer() {
        return rootContainer;
    }

    public final BeanPathElementRegistry getElementRegistry() {
        return elementRegistry;
    }

}