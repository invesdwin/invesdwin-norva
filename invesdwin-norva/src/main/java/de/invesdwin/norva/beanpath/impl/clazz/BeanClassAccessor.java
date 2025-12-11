package de.invesdwin.norva.beanpath.impl.clazz;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.BeanPathObjects;
import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.impl.clazz.internal.FieldInternalBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.clazz.internal.IInternalBeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.clazz.internal.MethodInternalBeanClassAccessor;
import de.invesdwin.norva.beanpath.spi.ABeanPathAccessor;

@Immutable
public class BeanClassAccessor extends ABeanPathAccessor implements IBeanClassAccessor {

    private final BeanClassContext context;
    private final BeanClassContainer container;
    private final IInternalBeanClassAccessor internal;
    private final BeanClassType type;

    public BeanClassAccessor(final BeanClassContext context, final BeanClassContainer container, final Field field) {
        this(context, container, new FieldInternalBeanClassAccessor(field));
    }

    public BeanClassAccessor(final BeanClassContext context, final BeanClassContainer container, final Method method) {
        this(context, container, new MethodInternalBeanClassAccessor(container, method));
    }

    public BeanClassAccessor(final BeanClassContext context, final BeanClassContainer container,
            final IInternalBeanClassAccessor internal) {
        this.context = context;
        this.container = container;
        this.internal = internal;
        this.type = BeanPathReflections.determineGenercType(getRawType());
    }

    @Override
    public BeanClassContext getContext() {
        return context;
    }

    @Override
    public BeanClassContainer getContainer() {
        return container;
    }

    @Override
    public Object getValueFromRoot(final Object root) {
        final Object targetObject = container.getTargetFromRoot(root);
        return getValueFromTarget(targetObject);
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        final Object targetObject = container.getTargetFromRoot(root);
        setValueFromTarget(targetObject, value);
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        if (target == null) {
            return null;
        } else {
            return internal.getValueFromTarget(target);
        }
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        if (target == null) {
            return;
        } else {
            internal.setValueFromTarget(target, value);
        }
    }

    @Override
    public Object invokeFromRootViaReflection(final Object root, final Object... params) {
        final Object targetObject = container.getTargetFromRoot(root);
        return invokeFromTargetViaReflection(targetObject, params);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object... params) {
        final Object targetObject = container.getTargetFromRoot(root);
        return invokeFromTarget(targetObject, params);
    }

    @Override
    public Object invokeFromRoot(final Object... params) {
        final Object targetObject = container.getTargetFromRoot(params[0]);
        params[0] = targetObject;
        return invokeFromTarget(params);
    }

    @Override
    public Object invokeFromRoot(final Object root) {
        final Object targetObject = container.getTargetFromRoot(root);
        return invokeFromTarget(targetObject);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1) {
        final Object targetObject = container.getTargetFromRoot(root);
        return invokeFromTarget(targetObject, param1);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2) {
        final Object targetObject = container.getTargetFromRoot(root);
        return invokeFromTarget(targetObject, param1, param2);
    }

    @Override
    public Object invokeFromRoot(final Object root, final Object param1, final Object param2, final Object param3) {
        final Object targetObject = container.getTargetFromRoot(root);
        return invokeFromTarget(targetObject, param1, param2, param3);
    }

    @Override
    public Object invokeFromTargetViaReflection(final Object target, final Object... params) {
        if (target == null) {
            return null;
        } else {
            return internal.invokeFromTargetViaReflection(target, params);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        if (target == null) {
            return null;
        } else {
            return internal.invokeFromTarget(target, params);
        }
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        if (params == null || params.length == 0 || params[0] == null) {
            return null;
        } else {
            return internal.invokeFromTarget(params);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        if (target == null) {
            return null;
        } else {
            return internal.invokeFromTarget(target);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        if (target == null) {
            return null;
        } else {
            return internal.invokeFromTarget(target, param1);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        if (target == null) {
            return null;
        } else {
            return internal.invokeFromTarget(target, param1, param2);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        if (target == null) {
            return null;
        } else {
            return internal.invokeFromTarget(target, param1, param2, param3);
        }
    }

    @Override
    public String getTypePathFragment() {
        if (getRawType().isArray()) {
            if (getRawType().getGenericType() instanceof GenericArrayType) {
                final GenericArrayType genericArrayType = (GenericArrayType) getRawType().getGenericType();
                return BeanPathObjects.simplifyQualifiedName(genericArrayType.getGenericComponentType().toString())
                        + "[]";
            } else {
                return getRawType().getSimpleName();
            }
        } else {
            return BeanPathObjects.simplifyQualifiedName(getRawType().getGenericType().toString());
        }
    }

    @Override
    public boolean isPublic() {
        return internal.isPublic();
    }

    @Override
    public boolean isStatic() {
        return internal.isStatic();
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return internal.getAnnotation(annotationType);
    }

    @Override
    public Method getPublicActionMethod() {
        return internal.getPublicActionMethod();
    }

    @Override
    public MethodHandle getPublicActionMethodHandle() {
        return internal.getPublicActionMethodHandle();
    }

    @Override
    public Field getRawField() {
        return internal.getRawField();
    }

    @Override
    public Method getRawMethod() {
        return internal.getRawMethod();
    }

    @Override
    public String getBeanPathFragment() {
        return internal.getBeanPathFragment();
    }

    @Override
    public Integer getPublicActionParameterCount() {
        return internal.getPublicActionParameterCount();
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        return internal.getPublicGetterParameterCount();
    }

    @Override
    public Integer getPublicSetterParameterCount() {
        return internal.getPublicSetterParameterCount();
    }

    @Override
    public BeanClassType getRawType() {
        return internal.getRawType();
    }

    @Override
    public BeanClassType getType() {
        return type;
    }

    @Override
    public String getRawName() {
        return internal.getRawName();
    }

    @Override
    public Method getPublicGetterMethod() {
        return internal.getPublicGetterMethod();
    }

    @Override
    public MethodHandle getPublicGetterMethodHandle() {
        return internal.getPublicGetterMethodHandle();
    }

    @Override
    public Method getPublicSetterMethod() {
        return internal.getPublicSetterMethod();
    }

    @Override
    public MethodHandle getPublicSetterMethodHandle() {
        return internal.getPublicSetterMethodHandle();
    }

    @Override
    public Field getPublicField() {
        return internal.getPublicField();
    }

    @Override
    public MethodHandle getPublicFieldGetterHandle() {
        return internal.getPublicFieldGetterHandle();
    }

    @Override
    public MethodHandle getPublicFieldSetterHandle() {
        return internal.getPublicFieldSetterHandle();
    }

    @Override
    public boolean hasPublicGetterOrField() {
        return internal.hasPublicGetterOrField();
    }

    @Override
    public boolean hasPublicGetter() {
        return internal.hasPublicGetter();
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return internal.hasPublicSetterOrField();
    }

    @Override
    public boolean hasPublicSetter() {
        return internal.hasPublicSetter();
    }

    @Override
    public boolean hasPublicField() {
        return internal.hasPublicField();
    }

    @Override
    public boolean hasPublicAction() {
        return internal.hasPublicAction();
    }

    @Override
    public String getPublicActionName() {
        return internal.getPublicActionName();
    }

    @Override
    public String getPublicFieldName() {
        return internal.getPublicFieldName();
    }

    @Override
    public String getPublicGetterName() {
        return internal.getPublicGetterName();
    }

    @Override
    public String getPublicSetterName() {
        return internal.getPublicSetterName();
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
