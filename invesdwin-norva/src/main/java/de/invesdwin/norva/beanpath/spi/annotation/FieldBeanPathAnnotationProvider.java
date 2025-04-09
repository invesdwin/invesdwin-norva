package de.invesdwin.norva.beanpath.spi.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.BeanPathReflections;

@Immutable
public class FieldBeanPathAnnotationProvider implements IBeanPathAnnotationProvider {

    private final Field field;

    public FieldBeanPathAnnotationProvider(final Field field) {
        this.field = field;
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return BeanPathReflections.getAnnotation(field, annotationType);
    }

}
