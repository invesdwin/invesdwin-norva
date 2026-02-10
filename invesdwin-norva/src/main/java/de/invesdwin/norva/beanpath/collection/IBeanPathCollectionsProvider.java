package de.invesdwin.norva.beanpath.collection;

import java.util.Map;

public interface IBeanPathCollectionsProvider {

    <K, V> Map<K, V> newMap();

    <K, V> Map<K, V> newConcurrentMap();

}
