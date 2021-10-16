package de.invesdwin.norva.beanpath.impl.model;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.ABeanPathContainer;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;

@NotThreadSafe
public class BeanModelContainer extends ABeanPathContainer {

    private final BeanModelType type;
    private final String beanPath;
    private final String typePath;
    private final BeanModelContainer root;
    private final BeanModelContainer parent;
    private final BeanModelAccessor accessor;

    public BeanModelContainer(final BeanModelType root) {
        this.parent = null;
        this.root = this;
        this.type = root;
        this.beanPath = "";
        this.typePath = "";
        this.accessor = null;
    }

    public BeanModelContainer(final ContainerOpenBeanPathElement containerOpenElement) {
        this.parent = (BeanModelContainer) containerOpenElement.getContainer();
        this.root = parent.getRoot();
        this.accessor = (BeanModelAccessor) containerOpenElement.getAccessor();
        this.type = accessor.getType();
        this.beanPath = BeanPathUtil.newBeanPath(parent, accessor);
        this.typePath = BeanPathUtil.newTypePath(parent, accessor);
    }

    @Override
    public String getBeanPath() {
        return beanPath;
    }

    @Override
    public String getTypePath() {
        return typePath;
    }

    @Override
    public BeanModelContainer getRoot() {
        return root;
    }

    @Override
    public BeanModelContainer getParent() {
        return parent;
    }

    @Override
    public BeanModelType getType() {
        return type;
    }

    @Override
    public BeanModelAccessor getAccessor() {
        return accessor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(final Class<T> type) {
        if (type.isAssignableFrom(getClass())) {
            return (T) this;
        }
        return null;
    }

}
