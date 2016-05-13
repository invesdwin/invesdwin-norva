package de.invesdwin.norva.beanpath.impl.clazz.internal;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;

@NotThreadSafe
public class FieldInternalBeanClassAccessor implements IInternalBeanClassAccessor {

    private final Field rawField;
    private final BeanClassType rawType;
    private Field publicField;

    public FieldInternalBeanClassAccessor(final Field field) {
        this.rawField = field;
        this.rawType = new BeanClassType(field.getType(), field.getGenericType());
        if (BeanPathReflections.isPublic(field)) {
            this.publicField = field;
            BeanPathReflections.makeAccessible(publicField);
        } else {
            this.publicField = null;
        }
    }

    @Override
    public String getRawName() {
        return rawField.getName();
    }

    @Override
    public String getBeanPathFragment() {
        return Introspector.decapitalize(getRawName());
    }

    @Override
    public boolean isPublic() {
        return BeanPathReflections.isPublic(rawField);
    }

    @Override
    public boolean isStatic() {
        return BeanPathReflections.isStatic(rawField);
    }

    @Override
    public Method getPublicActionMethod() {
        return null;
    }

    @Override
    public Method getPublicGetterMethod() {
        return null;
    }

    @Override
    public Method getPublicSetterMethod() {
        return null;
    }

    @Override
    public Method getRawMethod() {
        return null;
    }

    @Override
    public Field getRawField() {
        return rawField;
    }

    @Override
    public Field getPublicField() {
        return publicField;
    }

    @Override
    public BeanClassType getRawType() {
        return rawType;
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        if (getPublicField() != null) {
            return BeanPathReflections.getField(getPublicField(), target);
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicGetterOrField(this, target);
        }
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        if (getPublicField() != null) {
            BeanPathReflections.setField(getPublicField(), target, value);
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicSetterOrField(this, target);
        }
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object... params) {
        throw new UnsupportedOperationException("It is not possible to invoke a field!");
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return BeanPathReflections.getAnnotation(getRawField(), annotationType);
    }

    @Override
    public boolean hasPublicGetterOrField() {
        return publicField != null;
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return publicField != null;
    }

    @Override
    public boolean hasPublicAction() {
        return false;
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        if (hasPublicGetterOrField()) {
            return 0;
        } else {
            return null;
        }
    }

    @Override
    public Integer getPublicSetterParameterCount() {
        if (hasPublicSetterOrField()) {
            return 1;
        } else {
            return null;
        }
    }

    @Override
    public Integer getPublicActionParameterCount() {
        return null;
    }

}
