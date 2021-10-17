package de.invesdwin.norva.beanpath.impl.clazz.internal;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.concurrent.NotThreadSafe;

import de.invesdwin.norva.beanpath.BeanPathReflections;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;

@NotThreadSafe
public class FieldInternalBeanClassAccessor implements IInternalBeanClassAccessor {

    private final Field rawField;
    private final BeanClassType rawType;
    private final Field publicField;
    private final MethodHandle publicFieldSetterHandle;
    private final MethodHandle publicFieldGetterHandle;

    public FieldInternalBeanClassAccessor(final Field field) {
        this.rawField = field;
        this.rawType = new BeanClassType(field.getType(), field.getGenericType());
        if (BeanPathReflections.isPublic(field)) {
            this.publicField = field;
            BeanPathReflections.makeAccessible(publicField);
            try {
                final Lookup lookup = MethodHandles.lookup();
                publicFieldGetterHandle = lookup.unreflectGetter(publicField);
                publicFieldSetterHandle = lookup.unreflectSetter(publicField);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.publicField = null;
            this.publicFieldSetterHandle = null;
            this.publicFieldGetterHandle = null;
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
    public MethodHandle getPublicActionMethodHandle() {
        return null;
    }

    @Override
    public Method getPublicGetterMethod() {
        return null;
    }

    @Override
    public MethodHandle getPublicGetterMethodHandle() {
        return null;
    }

    @Override
    public Method getPublicSetterMethod() {
        return null;
    }

    @Override
    public MethodHandle getPublicSetterMethodHandle() {
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
    public MethodHandle getPublicFieldGetterHandle() {
        return publicFieldSetterHandle;
    }

    @Override
    public MethodHandle getPublicFieldSetterHandle() {
        return publicFieldGetterHandle;
    }

    @Override
    public BeanClassType getRawType() {
        return rawType;
    }

    @Override
    public Object getValueFromTarget(final Object target) {
        if (publicFieldGetterHandle != null) {
            try {
                return publicFieldGetterHandle.invoke(target);
            } catch (final Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicGetterOrField(this, target);
        }
    }

    @Override
    public void setValueFromTarget(final Object target, final Object value) {
        if (publicFieldSetterHandle != null) {
            try {
                publicFieldSetterHandle.invoke(target, value);
            } catch (final Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            throw InternalBeanClassAccessorExceptions.newNoPublicSetterOrField(this, target);
        }
    }

    @Override
    public Object invokeFromTarget(final Object... params) {
        throw newInvokeNotSupportedException();
    }

    private UnsupportedOperationException newInvokeNotSupportedException() {
        return new UnsupportedOperationException("It is not possible to invoke a field!");
    }

    @Override
    public Object invokeFromTarget(final Object target) {
        throw newInvokeNotSupportedException();
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1) {
        throw newInvokeNotSupportedException();
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2) {
        throw newInvokeNotSupportedException();
    }

    @Override
    public Object invokeFromTarget(final Object target, final Object param1, final Object param2, final Object param3) {
        throw newInvokeNotSupportedException();
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
    public boolean hasPublicGetter() {
        return false;
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return publicField != null;
    }

    @Override
    public boolean hasPublicSetter() {
        return false;
    }

    @Override
    public boolean hasPublicField() {
        return publicField != null;
    }

    @Override
    public String getPublicFieldName() {
        if (publicField != null) {
            return publicField.getName();
        } else {
            return null;
        }
    }

    @Override
    public String getPublicActionName() {
        return null;
    }

    @Override
    public String getPublicGetterName() {
        return null;
    }

    @Override
    public String getPublicSetterName() {
        return null;
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
