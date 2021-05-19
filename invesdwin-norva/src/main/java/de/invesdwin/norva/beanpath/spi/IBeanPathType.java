package de.invesdwin.norva.beanpath.spi;

import java.lang.annotation.Annotation;

public interface IBeanPathType {

    String getSimpleName();

    String getQualifiedName();

    boolean isEnum();

    boolean isPrimitive();

    boolean isArray();

    boolean isVoid();

    boolean isAbstract();

    boolean isBoolean();

    boolean isNumber();

    boolean isIntegralNumber();

    boolean isDecimalNumber();

    boolean isInstanceOf(Class<?> type);

    boolean isDate();

    boolean isIterable();

    boolean isJavaType();

    boolean isString();

    <T extends Annotation> T getAnnotation(Class<T> annotationType);

}
