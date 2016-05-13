package de.invesdwin.norva.beanpath.impl.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class DelegateBeanClassAccessor implements IBeanClassAccessor {

    private final IBeanClassAccessor delegate;

    public DelegateBeanClassAccessor(final IBeanClassAccessor delegate) {
        this.delegate = delegate;
    }

    @Override
    public BeanClassContext getContext() {
        return delegate.getContext();
    }

    @Override
    public BeanClassContainer getContainer() {
        return delegate.getContainer();
    }

    @Override
    public String getTypePathFragment() {
        return delegate.getTypePathFragment();
    }

    @Override
    public Method getPublicActionMethod() {
        return delegate.getPublicActionMethod();
    }

    @Override
    public Method getPublicGetterMethod() {
        return delegate.getPublicGetterMethod();
    }

    @Override
    public Method getPublicSetterMethod() {
        return delegate.getPublicSetterMethod();
    }

    @Override
    public Field getRawField() {
        return delegate.getRawField();
    }

    @Override
    public Field getPublicField() {
        return delegate.getPublicField();
    }

    @Override
    public String getBeanPathFragment() {
        return delegate.getBeanPathFragment();
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
    public Integer getPublicActionParameterCount() {
        return delegate.getPublicActionParameterCount();
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        return delegate.getPublicSetterParameterCount();
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
    public Object getValueFromTarget(final Object target) {
        return delegate.getValueFromTarget(target);
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        delegate.setValueFromTarget(target, value);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return delegate.invokeFromTarget(target, params);
    }

    @Override
    public String getRawName() {
        return delegate.getRawName();
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return delegate.getAnnotation(annotationType);
    }

    @Override
    public boolean hasPublicGetterOrField() {
        return delegate.hasPublicGetterOrField();
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return delegate.hasPublicSetterOrField();
    }

    @Override
    public boolean hasPublicAction() {
        return delegate.hasPublicAction();
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        return delegate.invokeFromRoot(root, params);
    }

    @Override
    public Object getValueFromRoot(final Object root) {
        return delegate.getValueFromRoot(root);
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        delegate.setValueFromRoot(root, value);
    }

    @Override
    public Method getRawMethod() {
        return delegate.getRawMethod();
    }

    @Override
    public boolean isNullable() {
        return delegate.isNullable();
    }

}
