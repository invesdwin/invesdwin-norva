package de.invesdwin.norva.beanpath.impl.clazz.internal;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
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
    private final Method publicGetterMethod;
    private final Method publicSetterMethod;
    private final FieldInternalBeanClassAccessor fieldDelegate;

    public MethodInternalBeanClassAccessor(final BeanClassContainer container, final Method method) {
        this.rawMethod = method;
        this.rawType = determineRawType();
        this.publicActionMethod = determinePublicActionMethod();
        this.publicGetterMethod = determinePublicGetterMethod(container);
        this.publicSetterMethod = determinePublicSetterMethod(container);
        this.fieldDelegate = determineFieldDelegate(container);
        //make accessible since overriden anonymous classes might prevent accessibility otherwise
        if (publicActionMethod != null) {
            BeanPathReflections.makeAccessible(publicActionMethod);
        }
        if (publicGetterMethod != null) {
            BeanPathReflections.makeAccessible(publicGetterMethod);
        }
        if (publicSetterMethod != null) {
            BeanPathReflections.makeAccessible(publicSetterMethod);
        }
    }

    private BeanClassType determineRawType() {
        if (rawMethod.getParameterTypes().length == 1
                && !BeanPathStrings.startsWithAny(rawMethod.getName(), BeanPathReflections.PROPERTY_GET_METHOD_PREFIX,
                        BeanPathReflections.PROPERTY_IS_METHOD_PREFIX)) {
            return new BeanClassType(rawMethod.getParameterTypes()[0], rawMethod.getGenericParameterTypes()[0]);
        } else {
            return new BeanClassType(rawMethod.getReturnType(), rawMethod.getGenericReturnType());
        }
    }

    private Method determinePublicActionMethod() {
        if (!BeanPathStrings.startsWithAny(getRawName(), BeanPathReflections.PROPERTY_METHOD_PREFIXES)) {
            final Method actionMethod = rawMethod;
            if (actionMethod != null && BeanPathReflections.isPublic(actionMethod)) {
                return actionMethod;
            }
        }
        return null;
    }

    private Method determinePublicGetterMethod(final BeanClassContainer container) {
        if (BeanPathStrings.startsWithAny(getRawName(), BeanPathReflections.PROPERTY_METHOD_PREFIXES)) {
            Method getterMethod = BeanPathReflections.findMethod(container.getType().getType(),
                    BeanPathReflections.PROPERTY_GET_METHOD_PREFIX + BeanPathStrings.capitalize(getBeanPathFragment()));
            if (getterMethod == null) {
                getterMethod = BeanPathReflections.findMethod(
                        container.getType().getType(),
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
        if (BeanPathStrings.startsWithAny(getRawName(), BeanPathReflections.PROPERTY_METHOD_PREFIXES)) {
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
        return Introspector.decapitalize(BeanPathStrings.removeAnyStart(getRawName(),
                BeanPathReflections.PROPERTY_METHOD_PREFIXES));
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
    public Method getPublicGetterMethod() {
        return publicGetterMethod;
    }

    @Override
    public Method getPublicSetterMethod() {
        return publicSetterMethod;
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
        if (publicGetterMethod != null) {
            return BeanPathReflections.invokeMethod(getPublicGetterMethod(), target);
        } else if (fieldDelegate != null) {
            return fieldDelegate.getValueFromTarget(target);
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicGetterOrField(this, target);
        }
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        if (publicSetterMethod != null) {
            BeanPathReflections.invokeMethod(getPublicSetterMethod(), target, value);
        } else if (fieldDelegate != null) {
            fieldDelegate.setValueFromTarget(target, value);
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicSetterOrField(this, target);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        try {
            if (publicActionMethod != null) {
                return BeanPathReflections.invokeMethod(getPublicActionMethod(), target, params);
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
    public boolean hasPublicGetter() {
        if (publicGetterMethod != null) {
            return true;
        } else if (fieldDelegate != null) {
            return fieldDelegate.hasPublicGetter();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPublicSetter() {
        if (publicSetterMethod != null) {
            return true;
        } else if (fieldDelegate != null) {
            return fieldDelegate.hasPublicSetter();
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPublicAction() {
        return publicActionMethod != null;
    }

}
