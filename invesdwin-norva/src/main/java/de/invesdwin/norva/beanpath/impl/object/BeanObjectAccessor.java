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
        return beanClassAccessor.invokeFromTarget(targetObject, params);
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

}
