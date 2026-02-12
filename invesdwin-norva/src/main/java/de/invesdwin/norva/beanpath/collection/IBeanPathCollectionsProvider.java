package de.invesdwin.norva.beanpath.collection;

import java.util.Map;
import java.util.Set;

public interface IBeanPathCollectionsProvider {

    <K, V> Map<K, V> newMap();

    <K, V> Map<K, V> newConcurrentMap();

    <T> Set<T> newSet();

}
