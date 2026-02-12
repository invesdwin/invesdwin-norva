package de.invesdwin.norva.beanpath.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class DefaultBeanPathCollectionsProvider implements IBeanPathCollectionsProvider {

    public static final DefaultBeanPathCollectionsProvider INSTANCE = new DefaultBeanPathCollectionsProvider();

    private DefaultBeanPathCollectionsProvider() {}

    @Override
    public <K, V> Map<K, V> newMap() {
        //CHECKSTYLE:OFF
        return new HashMap<>();
        //CHECKSTYLE:ON
    }

    @Override
    public <K, V> Map<K, V> newConcurrentMap() {
        //CHECKSTYLE:OFF
        return new ConcurrentHashMap<>();
        //CHECKSTYLE:ON
    }

    @Override
    public <T> Set<T> newSet() {
        //CHECKSTYLE:OFF
        return new HashSet<>();
        //CHECKSTYLE:ON
    }
}
