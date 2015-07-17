package de.invesdwin.norva.beanpath.impl.model;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.processing.ProcessingEnvironment;

import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;

@NotThreadSafe
public class BeanModelContext extends ABeanPathContext {

    private final ProcessingEnvironment env;

    public BeanModelContext(final BeanModelContainer rootContainer, final ProcessingEnvironment env) {
        super(rootContainer);
        this.env = env;
    }

    @Override
    public BeanModelContainer getRootContainer() {
        return (BeanModelContainer) super.getRootContainer();
    }

    public ProcessingEnvironment getEnv() {
        return env;
    }

}
