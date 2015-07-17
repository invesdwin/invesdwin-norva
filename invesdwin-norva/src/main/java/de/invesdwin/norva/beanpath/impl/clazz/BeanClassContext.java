package de.invesdwin.norva.beanpath.impl.clazz;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;

@NotThreadSafe
public class BeanClassContext extends ABeanPathContext {

    public BeanClassContext(final BeanClassContainer rootContainer) {
        super(rootContainer);
    }

    public BeanClassContext(final Class<?> root) {
        this(new BeanClassContainer(new BeanClassType(root)));
    }

    @Override
    public BeanClassContainer getRootContainer() {
        return (BeanClassContainer) super.getRootContainer();
    }

}
