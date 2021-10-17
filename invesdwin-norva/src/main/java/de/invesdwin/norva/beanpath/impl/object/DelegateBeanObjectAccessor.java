package de.invesdwin.norva.beanpath.impl.object;

import java.lang.annotation.Annotation;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.impl.clazz.IBeanClassAccessor;

@NotThreadSafe
public class DelegateBeanObjectAccessor implements IBeanObjectAccessor {

    private final IBeanObjectAccessor delegate;

    public DelegateBeanObjectAccessor(final IBeanObjectAccessor delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getRawName() {
        return delegate.getRawName();
    }

    @Override
    public String getBeanPathFragment() {
        return delegate.getBeanPathFragment();
    }

    @Override
    public String getTypePathFragment() {
        return delegate.getTypePathFragment();
    }

    @Override
    public boolean isPublic() {
        return delegate.isPublic();
    }

    @Override
    public boolean isStatic() {
        return delegate.isStatic();
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return delegate.getAnnotation(annotationType);
    }

    @Override
    public Integer getPublicActionParameterCount() {
        return delegate.getPublicActionParameterCount();
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        return delegate.getPublicGetterParameterCount();
    }

    @Override
    public Integer getPublicSetterParameterCount() {
        return delegate.getPublicSetterParameterCount();
    }

    @Override
    public BeanClassType getRawType() {
        return delegate.getRawType();
    }

    @Override
    public BeanClassType getType() {
        return delegate.getType();
    }

    @Override
    public boolean hasPublicGetterOrField() {
        return delegate.hasPublicGetterOrField();
    }

    @Override
    public boolean hasPublicGetter() {
        return delegate.hasPublicGetter();
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return delegate.hasPublicSetterOrField();
    }

    @Override
    public boolean hasPublicSetter() {
        return delegate.hasPublicSetter();
    }

    @Override
    public boolean hasPublicField() {
        return delegate.hasPublicField();
    }

    @Override
    public boolean hasPublicAction() {
        return delegate.hasPublicAction();
    }

    @Override
    public IBeanClassAccessor getBeanClassAccessor() {
        return delegate.getBeanClassAccessor();
    }

    @Override
    public BeanObjectContainer getContainer() {
        return delegate.getContainer();
    }

    @Override
    public BeanObjectContext getContext() {
        return delegate.getContext();
    }

    @Override
    public Object getValue() {
        return delegate.getValue();
    }

    @Override
    public void setValue(final Object value) {
        delegate.setValue(value);
    }

    @Override
    public Object invoke(final Object... params) {
        return delegate.invoke(params);
    }

    @Override
    public Object invoke() {
        return delegate.invoke();
    }

    @Override
    public Object invoke(final Object param1) {
        return delegate.invoke(param1);
    }

    @Override
    public Object invoke(final Object param1, final Object param2) {
        return delegate.invoke(param1, param2);
    }

    @Override
    public Object invoke(final Object param1, final Object param2, final Object param3) {
        return delegate.invoke(param1, param2, param3);
    }

    @Override
    public boolean isNullable() {
        return delegate.isNullable();
    }

    @Override
    public String getPublicActionName() {
        return delegate.getPublicActionName();
    }

    @Override
    public String getPublicFieldName() {
        return delegate.getPublicFieldName();
    }

    @Override
    public String getPublicGetterName() {
        return delegate.getPublicGetterName();
    }

    @Override
    public String getPublicSetterName() {
        return delegate.getPublicSetterName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(final Class<T> type) {
        if (type.isAssignableFrom(getClass())) {
            return (T) this;
        }
        return delegate.unwrap(type);
    }

}
