package de.invesdwin.norva.beanpath.impl.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.spi.IBeanPathType;

@NotThreadSafe
public class BeanClassType implements IBeanPathType {

    private final Class<?> type;
    private final Type genericType;

    public BeanClassType(final Class<?> type) {
        this.type = type;
        this.genericType = type;
    }

    public BeanClassType(final Class<?> type, final Type genericType) {
        this.type = type;
        this.genericType = genericType;
    }

    public Class<?> getType() {
        return type;
    }

    public Type getGenericType() {
        return genericType;
    }

    @Override
    public String getSimpleName() {
        if (isArray()) {
            return getType().getComponentType().getSimpleName() + "[]";
        } else {
            return BeanPathReflections.findSimpleName(getType());
        }
    }

    @Override
    public String getQualifiedName() {
        if (isArray()) {
            return getType().getComponentType().getName() + "[]";
        } else {
            return getType().getName();
        }
    }

    @Override
    public boolean isEnum() {
        return getType().isEnum();
    }

    @Override
    public boolean isPrimitive() {
        return getType().isPrimitive();
    }

    @Override
    public boolean isArray() {
        return getType().isArray();
    }

    @Override
    public boolean isVoid() {
        return BeanPathReflections.isVoid(getType());
    }

    @Override
    public boolean isAbstract() {
        return BeanPathReflections.isAbstract(getType());
    }

    @Override
    public boolean isBoolean() {
        return BeanPathReflections.isBoolean(getType());
    }

    @Override
    public boolean isNumber() {
        return BeanPathReflections.isNumber(getType());
    }

    @Override
    public boolean isIntegralNumber() {
        return BeanPathReflections.isIntegralNumber(getType());
    }

    @Override
    public boolean isDecimalNumber() {
        return BeanPathReflections.isDecimalNumber(getType());
    }

    @Override
    public boolean isInstanceOf(final Class<?> type) {
        return type.isAssignableFrom(getType());
    }

    @Override
    public boolean isDate() {
        return BeanPathReflections.isDate(getType());
    }

    @Override
    public boolean isIterable() {
        return isInstanceOf(Iterable.class);
    }

    @Override
    public boolean isJavaType() {
        return BeanPathReflections.isJavaType(getQualifiedName());
    }

    @Override
    public boolean isString() {
        return BeanPathReflections.isString(getType());
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return BeanPathReflections.getAnnotation(getType(), annotationType);
    }

}
