package de.invesdwin.norva.beanpath.impl.model;

import java.lang.annotation.Annotation;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.jboss.logging.generator.util.ElementHelper;

import de.invesdwin.norva.beanpath.BeanPathObjects;
import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.spi.IBeanPathType;

@NotThreadSafe
public class BeanModelType implements IBeanPathType {

    private final ProcessingEnvironment env;
    private final TypeMirror typeMirror;
    private final TypeElement typeElement;

    public BeanModelType(final ProcessingEnvironment env, final TypeMirror typeMirror, final TypeElement typeElement) {
        this.env = env;
        this.typeMirror = typeMirror;
        this.typeElement = typeElement;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    @Override
    public String getSimpleName() {
        return BeanPathObjects.simplifyQualifiedName(getQualifiedName());
    }

    @Override
    public String getQualifiedName() {
        if (typeElement != null) {
            return typeElement.getQualifiedName().toString();
        } else {
            return BeanPathObjects.removeGenericsFromQualifiedName(getTypeMirror().toString());
        }
    }

    @Override
    public boolean isVoid() {
        return getTypeMirror().getKind() == TypeKind.VOID;
    }

    @Override
    public boolean isPrimitive() {
        return getTypeMirror().getKind().isPrimitive();
    }

    @Override
    public boolean isEnum() {
        if (getTypeElement() == null) {
            return false;
        } else {
            return getTypeElement().getKind() == ElementKind.ENUM;
        }
    }

    @Override
    public boolean isArray() {
        return getTypeMirror().getKind() == TypeKind.ARRAY;
    }

    @Override
    public boolean isAbstract() {
        if (getTypeElement() == null) {
            return false;
        } else {
            return getTypeElement().getModifiers().contains(Modifier.ABSTRACT);
        }
    }

    @Override
    public boolean isBoolean() {
        if (getTypeElement() == null) {
            return false;
        }
        return getTypeMirror().getKind() == TypeKind.BOOLEAN
                || ElementHelper.isAssignableFrom(Boolean.class, getTypeElement());
    }

    @Override
    public boolean isNumber() {
        if (getTypeElement() == null) {
            return false;
        }
        return ElementHelper.isAssignableFrom(Number.class, getTypeElement()) || isIntegralNumber()
                || isDecimalNumber();
    }

    @Override
    public boolean isIntegralNumber() {
        if (getTypeElement() == null) {
            return false;
        }
        for (final Class<?> type : BeanPathReflections.TYPES_INTEGRAL_NUMBER) {
            if (ElementHelper.isAssignableFrom(type, getTypeElement())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDecimalNumber() {
        if (getTypeElement() == null) {
            return false;
        }
        if (isIntegralNumber()) {
            return false;
        }
        for (final Class<?> type : BeanPathReflections.TYPES_DECIMAL_NUMBER) {
            if (ElementHelper.isAssignableFrom(type, getTypeElement())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDate() {
        if (getTypeElement() == null) {
            return false;
        }
        for (final Class<?> type : BeanPathReflections.TYPES_DATE) {
            if (ElementHelper.isAssignableFrom(type, getTypeElement())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isString() {
        if (getTypeElement() == null) {
            return false;
        }
        for (final Class<?> type : BeanPathReflections.TYPES_STRING) {
            if (ElementHelper.isAssignableFrom(type, getTypeElement())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIterable() {
        if (getTypeElement() == null) {
            return false;
        }
        return ElementHelper.isAssignableFrom(Iterable.class, getTypeElement());
    }

    @Override
    public boolean isInstanceOf(final Class<?> type) {
        if (getTypeElement() == null) {
            return false;
        }
        return ElementHelper.isAssignableFrom(type, getTypeElement());
    }

    @Override
    public boolean isJavaType() {
        return BeanPathReflections.isJavaType(getQualifiedName());
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return BeanPathReflections.getAnnotation(env, getTypeElement(), annotationType);
    }
}
