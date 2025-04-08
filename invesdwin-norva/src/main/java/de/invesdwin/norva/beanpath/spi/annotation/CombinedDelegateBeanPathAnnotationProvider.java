package de.invesdwin.norva.beanpath.spi.annotation;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
public class CombinedDelegateBeanPathAnnotationProvider implements IBeanPathAnnotationProvider {

    private final IBeanPathAnnotationProvider[] delegates;

    public CombinedDelegateBeanPathAnnotationProvider(final List<IBeanPathAnnotationProvider> delegates) {
        this.delegates = delegates.toArray(EMPTY_ARRAY);
    }

    public CombinedDelegateBeanPathAnnotationProvider(final IBeanPathAnnotationProvider... delegates) {
        this.delegates = delegates;
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        for (int i = 0; i < delegates.length; i++) {
            final IBeanPathAnnotationProvider delegate = delegates[i];
            final T annotation = delegate.getAnnotation(annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

    public static IBeanPathAnnotationProvider valueOf(final IBeanPathAnnotationProvider... delegates) {
        if (delegates == null || delegates.length == 0) {
            return DisabledBeanPathAnnotationProvider.INSTANCE;
        }
        if (delegates.length == 1) {
            return delegates[0];
        }
        return new CombinedDelegateBeanPathAnnotationProvider(delegates);
    }

    public static IBeanPathAnnotationProvider valueOf(final List<IBeanPathAnnotationProvider> delegates) {
        if (delegates == null || delegates.size() == 0) {
            return DisabledBeanPathAnnotationProvider.INSTANCE;
        }
        if (delegates.size() == 1) {
            return delegates.get(0);
        }
        return new CombinedDelegateBeanPathAnnotationProvider(delegates);
    }

}
