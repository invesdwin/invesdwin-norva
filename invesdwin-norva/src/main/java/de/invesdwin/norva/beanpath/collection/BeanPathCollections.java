package de.invesdwin.norva.beanpath.collection;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class BeanPathCollections {

    private static IBeanPathCollectionsProvider provider = DefaultBeanPathCollectionsProvider.INSTANCE;

    private BeanPathCollections() {}

    public static void setProvider(final IBeanPathCollectionsProvider provider) {
        BeanPathCollections.provider = provider;
    }

    public static IBeanPathCollectionsProvider getProvider() {
        return provider;
    }

}
