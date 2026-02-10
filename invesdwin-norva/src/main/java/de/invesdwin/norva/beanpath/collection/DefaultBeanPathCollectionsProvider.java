package de.invesdwin.norva.beanpath.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class DefaultBeanPathCollectionsProvider implements IBeanPathCollectionsProvider {

    public static final DefaultBeanPathCollectionsProvider INSTANCE = new DefaultBeanPathCollectionsProvider();

    private DefaultBeanPathCollectionsProvider() {}

    @Override
    public <K, V> Map<K, V> newMap() {
        return new HashMap<>();
    }

    @Override
    public <K, V> Map<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<>();
    }
}
