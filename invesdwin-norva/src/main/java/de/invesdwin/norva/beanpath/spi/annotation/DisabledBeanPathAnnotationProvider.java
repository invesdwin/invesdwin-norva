package de.invesdwin.norva.beanpath.spi.annotation;

import java.lang.annotation.Annotation;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class DisabledBeanPathAnnotationProvider implements IBeanPathAnnotationProvider {

    public static final DisabledBeanPathAnnotationProvider INSTANCE = new DisabledBeanPathAnnotationProvider();

    private DisabledBeanPathAnnotationProvider() {}

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        return null;
    }

}
