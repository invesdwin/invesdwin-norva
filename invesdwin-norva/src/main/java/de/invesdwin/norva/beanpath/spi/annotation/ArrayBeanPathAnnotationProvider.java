package de.invesdwin.norva.beanpath.spi.annotation;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import de.invesdwin.norva.beanpath.BeanPathReflections;

@Immutable
public class ArrayBeanPathAnnotationProvider implements IBeanPathAnnotationProvider {

    private final Annotation[] annotations;

    public ArrayBeanPathAnnotationProvider(final List<? extends Annotation> annotations) {
        this.annotations = annotations.toArray(BeanPathReflections.ANNOTATION_EMPTY_ARRAY);
    }

    public ArrayBeanPathAnnotationProvider(final Annotation... annotations) {
        this.annotations = annotations;
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return BeanPathReflections.getAnnotationRecursive(annotations, annotationType);
    }

}
