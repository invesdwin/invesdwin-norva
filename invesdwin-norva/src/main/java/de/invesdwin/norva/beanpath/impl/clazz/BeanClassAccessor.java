package de.invesdwin.norva.beanpath.impl.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.BeanPathAssertions;
import de.invesdwin.norva.beanpath.BeanPathObjects;
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
        this.type = determineType();
    }

    private BeanClassType determineType() {
        Type genericType = null;
        if (getRawType().isArray()) {
            if (getRawType().getGenericType() instanceof GenericArrayType) {
                final GenericArrayType genericArrayType = (GenericArrayType) getRawType().getGenericType();
                genericType = genericArrayType.getGenericComponentType();
            } else {
                genericType = getRawType().getType().getComponentType();
            }
        } else if (getRawType().isIterable() && getRawType().getGenericType() instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) getRawType().getGenericType();
            final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length == 1) {
                genericType = actualTypeArguments[0];
            } else {
                //fallback to rawType, since this is not a generic iterable collection
                genericType = getRawType().getGenericType();
            }
        } else {
            //fallback to rawType, since this is not an array or generic
            genericType = getRawType().getGenericType();
        }

        final Class<?> classType = determineClassType(genericType);
        return new BeanClassType(classType, genericType);
    }

    private Class<?> determineClassType(final Type genericType) {
        final Class<?> classType;
        if (genericType instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) genericType;
            classType = (Class<?>) parameterizedType.getRawType();
        } else if (genericType instanceof TypeVariable) {
            final TypeVariable<?> typeVariable = (TypeVariable<?>) genericType;
            if (typeVariable.getGenericDeclaration() instanceof Class<?>) {
                classType = (Class<?>) typeVariable.getGenericDeclaration();
            } else {
                final Type[] bounds = typeVariable.getBounds();
                BeanPathAssertions.checkArgument(bounds.length == 1);
                final Type bound = bounds[0];
                return determineClassType(bound);
            }
        } else if (genericType instanceof WildcardType) {
            //fallback to neutral type since wildcard cannot be traversed
            return Object.class;
        } else {
            classType = (Class<?>) genericType;
        }
        return classType;
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
        final Object targetObject = container.getObjectFromRoot(root);
        return getValueFromTarget(targetObject);
    }

    @Override
    public void setValueFromRoot(final Object root, final Object value) {
        final Object targetObject = container.getObjectFromRoot(root);
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
    public Object invokeFromRoot(final Object root, final Object... params) {
        final Object targetObject = container.getObjectFromRoot(root);
        return invokeFromTarget(targetObject, params);
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
    public Method getPublicSetterMethod() {
        return internal.getPublicSetterMethod();
    }

    @Override
    public Field getPublicField() {
        return internal.getPublicField();
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

}
