package de.invesdwin.norva.beanpath.impl.clazz.internal;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.BeanPathStrings;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;

@NotThreadSafe
public class MethodInternalBeanClassAccessor implements IInternalBeanClassAccessor {

    private final Method rawMethod;
    private final BeanClassType rawType;
    private final Method publicActionMethod;
    private final MethodHandle publicActionMethodHandle;
    private final Method publicGetterMethod;
    private final MethodHandle publicGetterMethodHandle;
    private final Method publicSetterMethod;
    private final MethodHandle publicSetterMethodHandle;
    private final FieldInternalBeanClassAccessor fieldDelegate;

    public MethodInternalBeanClassAccessor(final BeanClassContainer container, final Method method) {
        this.rawMethod = method;
        this.rawType = determineRawType();
        this.publicActionMethod = determinePublicActionMethod();
        this.publicGetterMethod = determinePublicGetterMethod(container);
        this.publicSetterMethod = determinePublicSetterMethod(container);
        this.fieldDelegate = determineFieldDelegate(container);
        //make accessible since overriden anonymous classes might prevent accessibility otherwise
        final Lookup lookup = MethodHandles.lookup();
        try {
            if (publicActionMethod != null) {
                BeanPathReflections.makeAccessible(publicActionMethod);
                publicActionMethodHandle = lookup.unreflect(publicActionMethod);
            } else {
                publicActionMethodHandle = null;
            }
            if (publicGetterMethod != null) {
                BeanPathReflections.makeAccessible(publicGetterMethod);
                publicGetterMethodHandle = lookup.unreflect(publicGetterMethod);
            } else {
                publicGetterMethodHandle = null;
            }
            if (publicSetterMethod != null) {
                BeanPathReflections.makeAccessible(publicSetterMethod);
                publicSetterMethodHandle = lookup.unreflect(publicSetterMethod);
            } else {
                publicSetterMethodHandle = null;
            }
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private BeanClassType determineRawType() {
        if (rawMethod.getParameterTypes().length == 1 && !BeanPathStrings.startsWithAny(rawMethod.getName(),
                BeanPathReflections.PROPERTY_GET_METHOD_PREFIX, BeanPathReflections.PROPERTY_IS_METHOD_PREFIX)) {
            return new BeanClassType(rawMethod.getParameterTypes()[0], rawMethod.getGenericParameterTypes()[0]);
        } else {
            return new BeanClassType(rawMethod.getReturnType(), rawMethod.getGenericReturnType());
        }
    }

    private Method determinePublicActionMethod() {
        if (!BeanPathReflections.isPropertyMethodName(getRawName())) {
            final Method actionMethod = rawMethod;
            if (actionMethod != null && BeanPathReflections.isPublic(actionMethod)) {
                return actionMethod;
            }
        }
        return null;
    }

    private Method determinePublicGetterMethod(final BeanClassContainer container) {
        if (BeanPathReflections.isPropertyMethodName(getRawName())) {
            Method getterMethod = BeanPathReflections.findMethod(container.getType().getType(),
                    BeanPathReflections.PROPERTY_GET_METHOD_PREFIX + BeanPathStrings.capitalize(getBeanPathFragment()));
            if (getterMethod == null) {
                getterMethod = BeanPathReflections.findMethod(container.getType().getType(),
                        BeanPathReflections.PROPERTY_IS_METHOD_PREFIX
                                + BeanPathStrings.capitalize(getBeanPathFragment()));
            }
            if (getterMethod != null && BeanPathReflections.isPublic(getterMethod)) {
                return getterMethod;
            }
        }
        return null;
    }

    private Method determinePublicSetterMethod(final BeanClassContainer container) {
        if (BeanPathReflections.isPropertyMethodName(getRawName())) {
            final String setterMethodName = BeanPathReflections.PROPERTY_SET_METHOD_PREFIX
                    + BeanPathStrings.capitalize(getBeanPathFragment());
            Method setterMethod = BeanPathReflections.findMethod(container.getType().getType(), setterMethodName,
                    getRawType().getType());
            if (setterMethod == null) {
                //fallback to find by name in case the setter has a superclass as type argument or even Object maybe
                setterMethod = BeanPathReflections.findMethodByName(container.getType().getType(), setterMethodName, 1);
            }
            if (setterMethod != null && BeanPathReflections.isPublic(setterMethod)) {
                return setterMethod;
            }
        }
        return null;
    }

    private FieldInternalBeanClassAccessor determineFieldDelegate(final BeanClassContainer container) {
        final Field field = BeanPathReflections.findField(container.getType().getType(), getBeanPathFragment());
        if (field != null) {
            return new FieldInternalBeanClassAccessor(field);
        }
        return null;
    }

    @Override
    public String getRawName() {
        return rawMethod.getName();
    }

    @Override
    public String getBeanPathFragment() {
        return BeanPathReflections.getPropertyBeanPathFragment(getRawName());
    }

    @Override
    public boolean isPublic() {
        return BeanPathReflections.isPublic(rawMethod);
    }

    @Override
    public boolean isStatic() {
        return BeanPathReflections.isStatic(rawMethod);
    }

    @Override
    public Method getRawMethod() {
        return rawMethod;
    }

    @Override
    public Method getPublicActionMethod() {
        return publicActionMethod;
    }

    @Override
    public MethodHandle getPublicActionMethodHandle() {
        return publicActionMethodHandle;
    }

    @Override
    public Method getPublicGetterMethod() {
        return publicGetterMethod;
    }

    @Override
    public MethodHandle getPublicGetterMethodHandle() {
        return publicGetterMethodHandle;
    }

    @Override
    public Method getPublicSetterMethod() {
        return publicSetterMethod;
    }

    @Override
    public MethodHandle getPublicSetterMethodHandle() {
        return publicSetterMethodHandle;
    }

    @Override
    public Field getRawField() {
        if (fieldDelegate != null) {
            return fieldDelegate.getRawField();
        } else {
            return null;
        }
    }

    @Override
    public Field getPublicField() {
        if (fieldDelegate != null) {
            return fieldDelegate.getPublicField();
        } else {
            return null;
        }
    }

    @Override
    public MethodHandle getPublicFieldGetterHandle() {
        if (fieldDelegate != null) {
            return fieldDelegate.getPublicFieldGetterHandle();
        } else {
            return null;
        }
    }

    @Override
    public MethodHandle getPublicFieldSetterHandle() {
        if (fieldDelegate != null) {
            return fieldDelegate.getPublicFieldSetterHandle();
        } else {
            return null;
        }
    }

    @Override
    public Integer getPublicActionParameterCount() {
        if (publicActionMethod != null) {
            return publicActionMethod.getParameterTypes().length;
        } else {
            return null;
        }
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        if (publicGetterMethod != null) {
            return publicGetterMethod.getParameterTypes().length;
        } else if (fieldDelegate != null) {
            return fieldDelegate.getPublicGetterParameterCount();
        } else {
            return null;
        }
    }

    @Override
    public Integer getPublicSetterParameterCount() {
        if (publicSetterMethod != null) {
            return publicSetterMethod.getParameterTypes().length;
        } else if (fieldDelegate != null) {
            return fieldDelegate.getPublicSetterParameterCount();
        } else {
            return null;
        }
    }

    @Override
    public BeanClassType getRawType() {
        return rawType;
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        if (publicGetterMethodHandle != null) {
            try {
                return publicGetterMethodHandle.invoke(target);
            } catch (final Throwable e) {
                throw new RuntimeException(e);
            }
        } else if (fieldDelegate != null) {
            return fieldDelegate.getValueFromTarget(target);
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicGetterOrField(this, target);
        }
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        if (publicSetterMethodHandle != null) {
            try {
                publicSetterMethodHandle.invoke(target, value);
            } catch (final Throwable e) {
                throw new RuntimeException(e);
            }
        } else if (fieldDelegate != null) {
            fieldDelegate.setValueFromTarget(target, value);
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicSetterOrField(this, target);
        }
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        try {
            if (publicActionMethodHandle != null) {
                return publicActionMethodHandle.invoke(params);
            } else {
                throw new UnsupportedOperationException("No public action method exists");
            }
        } catch (final UndeclaredThrowableException e) {
            throw new UndeclaredThrowableException(e.getUndeclaredThrowable(),
                    newInvokeFromTargetExceptionMessage(params[0]));
        } catch (final Exception e) {
            throw new UndeclaredThrowableException(e, newInvokeFromTargetExceptionMessage(params[0]));
        } catch (final Throwable t) {
            throw new RuntimeException(newInvokeFromTargetExceptionMessage(params[0]), t);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        try {
            if (publicActionMethodHandle != null) {
                return publicActionMethodHandle.invoke(target);
            } else {
                throw new UnsupportedOperationException("No public action method exists");
            }
        } catch (final UndeclaredThrowableException e) {
            throw new UndeclaredThrowableException(e.getUndeclaredThrowable(),
                    newInvokeFromTargetExceptionMessage(target));
        } catch (final Exception e) {
            throw new UndeclaredThrowableException(e, newInvokeFromTargetExceptionMessage(target));
        } catch (final Throwable t) {
            throw new RuntimeException(newInvokeFromTargetExceptionMessage(target), t);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        try {
            if (publicActionMethodHandle != null) {
                return publicActionMethodHandle.invoke(target, param1);
            } else {
                throw new UnsupportedOperationException("No public action method exists");
            }
        } catch (final UndeclaredThrowableException e) {
            throw new UndeclaredThrowableException(e.getUndeclaredThrowable(),
                    newInvokeFromTargetExceptionMessage(target));
        } catch (final Exception e) {
            throw new UndeclaredThrowableException(e, newInvokeFromTargetExceptionMessage(target));
        } catch (final Throwable t) {
            throw new RuntimeException(newInvokeFromTargetExceptionMessage(target), t);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        try {
            if (publicActionMethodHandle != null) {
                return publicActionMethodHandle.invoke(target, param1, param2);
            } else {
                throw new UnsupportedOperationException("No public action method exists");
            }
        } catch (final UndeclaredThrowableException e) {
            throw new UndeclaredThrowableException(e.getUndeclaredThrowable(),
                    newInvokeFromTargetExceptionMessage(target));
        } catch (final Exception e) {
            throw new UndeclaredThrowableException(e, newInvokeFromTargetExceptionMessage(target));
        } catch (final Throwable t) {
            throw new RuntimeException(newInvokeFromTargetExceptionMessage(target), t);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        try {
            if (publicActionMethodHandle != null) {
                return publicActionMethodHandle.invoke(target, param1, param2, param3);
            } else {
                throw new UnsupportedOperationException("No public action method exists");
            }
        } catch (final UndeclaredThrowableException e) {
            throw new UndeclaredThrowableException(e.getUndeclaredThrowable(),
                    newInvokeFromTargetExceptionMessage(target));
        } catch (final Exception e) {
            throw new UndeclaredThrowableException(e, newInvokeFromTargetExceptionMessage(target));
        } catch (final Throwable t) {
            throw new RuntimeException(newInvokeFromTargetExceptionMessage(target), t);
        }
    }

    private String newInvokeFromTargetExceptionMessage(final Object target) {
        return "For beanpath [" + getBeanPathFragment() + "] on target [" + target.getClass().getName()
                + "] with method [" + getPublicActionMethod() + "]";
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        if (publicActionMethod != null) {
            final T annotation = BeanPathReflections.getAnnotation(publicActionMethod, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (publicGetterMethod != null) {
            final T annotation = BeanPathReflections.getAnnotation(publicGetterMethod, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (publicSetterMethod != null) {
            final T annotation = BeanPathReflections.getAnnotation(publicSetterMethod, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (fieldDelegate != null) {
            final T annotation = fieldDelegate.getAnnotation(annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        //fallback to raw method
        return BeanPathReflections.getAnnotation(rawMethod, annotationType);
    }

    @Override
    public boolean hasPublicGetterOrField() {
        if (publicGetterMethod != null) {
            return true;
        } else if (fieldDelegate != null) {
            return fieldDelegate.hasPublicGetterOrField();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPublicGetter() {
        return publicGetterMethod != null;
    }

    @Override
    public boolean hasPublicSetterOrField() {
        if (publicSetterMethod != null) {
            return true;
        } else if (fieldDelegate != null) {
            return fieldDelegate.hasPublicSetterOrField();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPublicSetter() {
        return publicSetterMethod != null;
    }

    @Override
    public boolean hasPublicField() {
        if (fieldDelegate != null) {
            return fieldDelegate.hasPublicField();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPublicAction() {
        return publicActionMethod != null;
    }

    @Override
    public String getPublicActionName() {
        if (publicActionMethod != null) {
            return publicActionMethod.getName();
        } else {
            return null;
        }
    }

    @Override
    public String getPublicFieldName() {
        return null;
    }

    @Override
    public String getPublicGetterName() {
        if (publicGetterMethod != null) {
            return publicGetterMethod.getName();
        } else {
            return null;
        }
    }

    @Override
    public String getPublicSetterName() {
        if (publicSetterMethod != null) {
            return publicSetterMethod.getName();
        } else {
            return null;
        }
    }

}
