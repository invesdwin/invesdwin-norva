package de.invesdwin.norva.beanpath.spi.annotation;

import java.lang.annotation.Annotation;

public interface IBeanPathAnnotationProvider {

    IBeanPathAnnotationProvider[] EMPTY_ARRAY = new IBeanPathAnnotationProvider[0];

    <T extends Annotation> T getAnnotation(Class<T> annotationType);
}
