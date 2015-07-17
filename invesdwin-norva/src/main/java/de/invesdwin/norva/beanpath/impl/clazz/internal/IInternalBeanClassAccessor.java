package de.invesdwin.norva.beanpath.impl.clazz.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;

public interface IInternalBeanClassAccessor {

    Method getRawMethod();

    Method getPublicActionMethod();

    Method getPublicGetterMethod();

    Method getPublicSetterMethod();

    Field getRawField();

    Field getPublicField();

    String getBeanPathFragment();

    boolean isPublic();

    boolean isStatic();

    BeanClassType getRawType();

    Object getValueFromTarget(Object target);

    void setValueFromTarget(Object target, Object value);

    Object invokeFromTarget(Object target, Object... params);

    String getRawName();

    <T extends Annotation> T getAnnotation(final Class<T> annotationType);

    boolean hasPublicGetter();

    boolean hasPublicSetter();

    boolean hasPublicAction();

    Integer getPublicGetterParameterCount();

    Integer getPublicSetterParameterCount();

    Integer getPublicActionParameterCount();
}
