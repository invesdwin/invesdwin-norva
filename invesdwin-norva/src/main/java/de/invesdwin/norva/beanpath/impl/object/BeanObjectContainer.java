package de.invesdwin.norva.beanpath.impl.object;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.spi.ABeanPathContainer;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;

@NotThreadSafe
public class BeanObjectContainer extends ABeanPathContainer {

    private final BeanObjectAccessor accessor;
    private final BeanClassContainer beanClassContainer;
    private final BeanObjectContainer root;
    private final BeanObjectContainer parent;
    private final IRootObjectReference rootObjectReference;

    public BeanObjectContainer(final Object root) {
        this(new ImmutableRootObjectReference(root));
    }

    public BeanObjectContainer(final IRootObjectReference rootObjectReference) {
        this.beanClassContainer = new BeanClassContainer(
                new BeanClassType(rootObjectReference.getRootObject().getClass()));
        this.rootObjectReference = rootObjectReference;
        this.root = this;
        this.parent = null;
        this.accessor = null;
    }

    public BeanObjectContainer(final ContainerOpenBeanPathElement containerOpenElement) {
        this.accessor = (BeanObjectAccessor) containerOpenElement.getAccessor();
        final BeanObjectContainer container = (BeanObjectContainer) containerOpenElement.getContainer();
        this.beanClassContainer = new BeanClassContainer(container.unwrap(BeanClassContainer.class),
                accessor.getBeanClassAccessor());
        this.parent = container;
        this.root = parent.getRoot();
        this.rootObjectReference = null;
    }

    public Object getObject() {
        return beanClassContainer.getTargetFromRoot(getRootObject());
    }

    public Object getRootObject() {
        if (root == this) {
            return rootObjectReference.getRootObject();
        } else {
            return root.getRootObject();
        }
    }

    @Override
    public String getBeanPath() {
        return beanClassContainer.getBeanPath();
    }

    @Override
    public String getTypePath() {
        return beanClassContainer.getTypePath();
    }

    @Override
    public BeanObjectContainer getRoot() {
        return root;
    }

    @Override
    public BeanObjectContainer getParent() {
        return parent;
    }

    @Override
    public BeanClassType getType() {
        return beanClassContainer.getType();
    }

    @Override
    public BeanObjectAccessor getAccessor() {
        return accessor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(final Class<T> type) {
        if (type.isAssignableFrom(getClass())) {
            return (T) this;
        }
        if (type.isAssignableFrom(beanClassContainer.getClass())) {
            return (T) beanClassContainer;
        }
        return null;
    }

}
