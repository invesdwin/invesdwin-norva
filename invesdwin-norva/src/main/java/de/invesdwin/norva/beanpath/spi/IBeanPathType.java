package de.invesdwin.norva.beanpath.spi;

import de.invesdwin.norva.beanpath.spi.annotation.IBeanPathAnnotationProvider;

public interface IBeanPathType extends IBeanPathAnnotationProvider {

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

}
