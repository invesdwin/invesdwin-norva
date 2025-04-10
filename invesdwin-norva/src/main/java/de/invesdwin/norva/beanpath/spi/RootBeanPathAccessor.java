package de.invesdwin.norva.beanpath.spi;

import java.lang.annotation.Annotation;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.spi.context.ABeanPathContext;

@NotThreadSafe
public class RootBeanPathAccessor implements IBeanPathAccessor {

    private final ABeanPathContext context;

    public RootBeanPathAccessor(final ABeanPathContext context) {
        this.context = context;
    }

    @Override
    public ABeanPathContext getContext() {
        return context;
    }

    @Override
    public IBeanPathContainer getContainer() {
        return null;
    }

    @Override
    public String getRawName() {
        return "";
    }

    @Override
    public String getBeanPathFragment() {
        return "";
    }

    @Override
    public String getTypePathFragment() {
        return "";
    }

    @Override
    public boolean isPublic() {
        return false;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return null;
    }

    @Override
    public Integer getPublicActionParameterCount() {
        return null;
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        return null;
    }

    @Override
    public Integer getPublicSetterParameterCount() {
        return null;
    }

    @Override
    public IBeanPathType getRawType() {
        return context.getRootContainer().getType();
    }

    @Override
    public IBeanPathType getType() {
        return context.getRootContainer().getType();
    }

    @Override
    public boolean hasPublicGetterOrField() {
        return false;
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return false;
    }

    @Override
    public boolean hasPublicAction() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public boolean hasPublicGetter() {
        return false;
    }

    @Override
    public boolean hasPublicSetter() {
        return false;
    }

    @Override
    public boolean hasPublicField() {
        return false;
    }

    @Override
    public String getPublicGetterName() {
        return null;
    }

    @Override
    public String getPublicSetterName() {
        return null;
    }

    @Override
    public String getPublicFieldName() {
        return null;
    }

    @Override
    public String getPublicActionName() {
        return null;
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
