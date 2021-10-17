package de.invesdwin.norva.beanpath.impl.clazz.internal;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;

public interface IInternalBeanClassAccessor {

    Method getRawMethod();

    Method getPublicActionMethod();

    MethodHandle getPublicActionMethodHandle();

    Method getPublicGetterMethod();

    MethodHandle getPublicGetterMethodHandle();

    Method getPublicSetterMethod();

    MethodHandle getPublicSetterMethodHandle();

    Field getRawField();

    Field getPublicField();

    MethodHandle getPublicFieldGetterHandle();

    MethodHandle getPublicFieldSetterHandle();

    String getBeanPathFragment();

    boolean isPublic();

    boolean isStatic();

    BeanClassType getRawType();

    Object getValueFromTarget(Object target);

    void setValueFromTarget(Object target, Object value);

    Object invokeFromTarget(Object... params);

    Object invokeFromTarget(Object target);

    Object invokeFromTarget(Object target, Object param1);

    Object invokeFromTarget(Object target, Object param1, Object param2);

    Object invokeFromTarget(Object target, Object param1, Object param2, Object param3);

    String getRawName();

    <T extends Annotation> T getAnnotation(Class<T> annotationType);

    boolean hasPublicGetterOrField();

    boolean hasPublicGetter();

    boolean hasPublicSetterOrField();

    boolean hasPublicSetter();

    boolean hasPublicField();

    boolean hasPublicAction();

    Integer getPublicGetterParameterCount();

    Integer getPublicSetterParameterCount();

    Integer getPublicActionParameterCount();

    String getPublicActionName();

    String getPublicFieldName();

    String getPublicGetterName();

    String getPublicSetterName();

}
