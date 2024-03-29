package de.invesdwin.norva.beanpath.impl.clazz;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.ABeanPathContainer;
import de.invesdwin.norva.beanpath.spi.BeanPathUtil;
import de.invesdwin.norva.beanpath.spi.element.ContainerOpenBeanPathElement;

@NotThreadSafe
public class BeanClassContainer extends ABeanPathContainer {

    private final BeanClassType type;
    private final String beanPath;
    private final String typePath;
    private final BeanClassAccessor accessor;
    private final BeanClassContainer root;
    private final BeanClassContainer parent;

    public BeanClassContainer(final BeanClassType root) {
        this.type = root;
        this.beanPath = "";
        this.typePath = "";
        this.accessor = null;
        this.root = this;
        this.parent = null;
    }

    public BeanClassContainer(final ContainerOpenBeanPathElement containerOpenElement) {
        this.parent = (BeanClassContainer) containerOpenElement.getContainer();
        this.root = parent.getRoot();
        this.accessor = (BeanClassAccessor) containerOpenElement.getAccessor();
        this.type = accessor.getType();
        this.beanPath = BeanPathUtil.newBeanPath(parent, accessor);
        this.typePath = BeanPathUtil.newTypePath(parent, accessor);
    }

    public BeanClassContainer(final BeanClassContainer parent, final BeanClassAccessor accessor) {
        this.parent = parent;
        this.root = parent.getRoot();
        this.accessor = accessor;
        this.type = accessor.getType();
        this.beanPath = BeanPathUtil.newBeanPath(parent, accessor);
        this.typePath = BeanPathUtil.newTypePath(parent, accessor);
    }

    public Object getTargetFromRoot(final Object rootObject) {
        if (accessor == null) {
            //this is the root object
            return rootObject;
        } else {
            //retrieve object by calling the accessors
            return accessor.getValueFromRoot(rootObject);
        }
    }

    @Override
    public BeanClassType getType() {
        return type;
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
    public BeanClassContainer getRoot() {
        return root;
    }

    @Override
    public BeanClassContainer getParent() {
        return parent;
    }

    @Override
    public BeanClassAccessor getAccessor() {
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
