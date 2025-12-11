package de.invesdwin.norva.beanpath.impl.clazz;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
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
    public MethodHandle getPublicActionMethodHandle() {
        return delegate.getPublicActionMethodHandle();
    }

    @Override
    public Method getPublicGetterMethod() {
        return delegate.getPublicGetterMethod();
    }

    @Override
    public MethodHandle getPublicGetterMethodHandle() {
        return delegate.getPublicGetterMethodHandle();
    }

    @Override
    public Method getPublicSetterMethod() {
        return delegate.getPublicSetterMethod();
    }

    @Override
    public MethodHandle getPublicSetterMethodHandle() {
        return delegate.getPublicSetterMethodHandle();
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
    public MethodHandle getPublicFieldGetterHandle() {
        return delegate.getPublicFieldGetterHandle();
    }

    @Override
    public MethodHandle getPublicFieldSetterHandle() {
        return delegate.getPublicFieldSetterHandle();
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
    public Object invokeFromTargetViaReflection(final Object target, final Object... params) {
        return delegate.invokeFromTargetViaReflection(target, params);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        return delegate.invokeFromTarget(target, params);
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        return delegate.invokeFromTarget(params);
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        return delegate.invokeFromTarget(target);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        return delegate.invokeFromTarget(target, param1);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        return delegate.invokeFromTarget(target, param1, param2);
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        return delegate.invokeFromTarget(target, param1, param2, param3);
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
    public Object invokeFromRootViaReflection(final Object root, final Object... params) {
        return delegate.invokeFromRootViaReflection(root, params);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        return delegate.invokeFromRoot(root, params);
    }

    @Override
    public Object invokeFromRoot(final Object... params) {
        return delegate.invokeFromRoot(params);
    }

    @Override
    public Object invokeFromRoot(final Object root) {
        return delegate.invokeFromRoot(root);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1) {
        return delegate.invokeFromRoot(root, param1);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2) {
        return delegate.invokeFromRoot(root, param1, param2);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2, final Object param3) {
        return delegate.invokeFromRoot(root, param1, param2, param3);
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
