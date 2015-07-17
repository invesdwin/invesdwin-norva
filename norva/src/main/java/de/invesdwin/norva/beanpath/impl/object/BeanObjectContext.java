package de.invesdwin.norva.beanpath.impl.object;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContext;
import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;

@NotThreadSafe
public class BeanObjectContext extends ABeanPathContext {

    private final BeanClassContext beanClassContext;

    public BeanObjectContext(final BeanObjectContainer rootContainer) {
        super(rootContainer);
        this.beanClassContext = new BeanClassContext(new BeanClassContainer(rootContainer.getType()));
    }

    public BeanObjectContext(final IRootObjectReference rootObjectReference) {
        this(new BeanObjectContainer(rootObjectReference));
    }

    public BeanObjectContext(final Object root) {
        this(new BeanObjectContainer(root));
    }

    @Override
    public BeanObjectContainer getRootContainer() {
        return (BeanObjectContainer) super.getRootContainer();
    }

    public BeanClassContext getBeanClassContext() {
        return beanClassContext;
    }

}
