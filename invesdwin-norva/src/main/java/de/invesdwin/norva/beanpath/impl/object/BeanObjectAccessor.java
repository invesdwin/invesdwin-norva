package de.invesdwin.norva.beanpath.impl.object;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.impl.clazz.BeanClassAccessor;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassContainer;
import de.invesdwin.norva.beanpath.impl.clazz.BeanClassType;
import de.invesdwin.norva.beanpath.spi.ABeanPathAccessor;

@Immutable
public class BeanObjectAccessor extends ABeanPathAccessor implements IBeanObjectAccessor {

    private final BeanObjectContext context;
    private final BeanClassAccessor beanClassAccessor;
    private final BeanObjectContainer container;

    public BeanObjectAccessor(final BeanObjectContext context, final BeanObjectContainer container, final Field field) {
        this.context = context;
        this.container = container;
        this.beanClassAccessor = new BeanClassAccessor(context.getBeanClassContext(),
                container.unwrap(BeanClassContainer.class), field);
    }

    public BeanObjectAccessor(final BeanObjectContext context, final BeanObjectContainer container,
            final Method method) {
        this.context = context;
        this.container = container;
        this.beanClassAccessor = new BeanClassAccessor(context.getBeanClassContext(),
                container.unwrap(BeanClassContainer.class), method);
    }

    @Override
    public BeanClassAccessor getBeanClassAccessor() {
        return beanClassAccessor;
    }

    @Override
    public BeanObjectContainer getContainer() {
        return container;
    }

    @Override
    public BeanObjectContext getContext() {
        return context;
    }

    @Override
    public Object getValue() {
        final Object targetObject = container.getObject();
        return beanClassAccessor.getValueFromTarget(targetObject);
    }

    @Override
    public void setValue(final Object value) {
        final Object targetObject = container.getObject();
        beanClassAccessor.setValueFromTarget(targetObject, value);
    }

    @Override
    public Object invoke(final Object... params) {
        final Object targetObject = container.getObject();
        switch (params.length) {
        case 0:
            return beanClassAccessor.invokeFromTarget(targetObject);
        case 1:
            return beanClassAccessor.invokeFromTarget(targetObject, params[0]);
        case 2:
            return beanClassAccessor.invokeFromTarget(targetObject, params[0], params[1]);
        case 3:
            return beanClassAccessor.invokeFromTarget(targetObject, params[0], params[1], params[2]);
        default:
            final Object[] args = new Object[params.length + 1];
            System.arraycopy(params, 0, args, 1, params.length);
            args[0] = targetObject;
            return beanClassAccessor.invokeFromTarget(args);
        }
    }

    @Override
    public Object invoke() {
        final Object targetObject = container.getObject();
        return beanClassAccessor.invokeFromTarget(targetObject);
    }

    @Override
    public Object invoke(final Object param1) {
        final Object targetObject = container.getObject();
        return beanClassAccessor.invokeFromTarget(targetObject, param1);
    }

    @Override
    public Object invoke(final Object param1, final Object param2) {
        final Object targetObject = container.getObject();
        return beanClassAccessor.invokeFromTarget(targetObject, param1, param2);
    }

    @Override
    public Object invoke(final Object param1, final Object param2, final Object param3) {
        final Object targetObject = container.getObject();
        return beanClassAccessor.invokeFromTarget(targetObject, param1, param2, param3);
    }

    @Override
    public String getRawName() {
        return beanClassAccessor.getRawName();
    }

    @Override
    public String getBeanPathFragment() {
        return beanClassAccessor.getBeanPathFragment();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return beanClassAccessor.getAnnotation(annotationType);
    }

    @Override
    public String getTypePathFragment() {
        return beanClassAccessor.getTypePathFragment();
    }

    @Override
    public boolean isPublic() {
        return beanClassAccessor.isPublic();
    }

    @Override
    public boolean isStatic() {
        return beanClassAccessor.isStatic();
    }

    @Override
    public Integer getPublicActionParameterCount() {
        return beanClassAccessor.getPublicActionParameterCount();
    }

    @Override
    public Integer getPublicGetterParameterCount() {
        return beanClassAccessor.getPublicGetterParameterCount();
    }

    @Override
    public Integer getPublicSetterParameterCount() {
        return beanClassAccessor.getPublicSetterParameterCount();
    }

    @Override
    public BeanClassType getRawType() {
        return beanClassAccessor.getRawType();
    }

    @Override
    public BeanClassType getType() {
        return beanClassAccessor.getType();
    }

    @Override
    public boolean hasPublicGetterOrField() {
        return beanClassAccessor.hasPublicGetterOrField();
    }

    @Override
    public boolean hasPublicGetter() {
        return beanClassAccessor.hasPublicGetter();
    }

    @Override
    public boolean hasPublicSetterOrField() {
        return beanClassAccessor.hasPublicSetterOrField();
    }

    @Override
    public boolean hasPublicSetter() {
        return beanClassAccessor.hasPublicSetter();
    }

    @Override
    public boolean hasPublicField() {
        return beanClassAccessor.hasPublicField();
    }

    @Override
    public boolean hasPublicAction() {
        return beanClassAccessor.hasPublicAction();
    }

    @Override
    public String getPublicActionName() {
        return beanClassAccessor.getPublicActionName();
    }

    @Override
    public String getPublicFieldName() {
        return beanClassAccessor.getPublicFieldName();
    }

    @Override
    public String getPublicGetterName() {
        return beanClassAccessor.getPublicGetterName();
    }

    @Override
    public String getPublicSetterName() {
        return beanClassAccessor.getPublicSetterName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(final Class<T> type) {
        if (type.isAssignableFrom(getClass())) {
            return (T) this;
        }
        return beanClassAccessor.unwrap(type);
    }

}
