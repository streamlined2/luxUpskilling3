package org.training.datastructures.map;

import java.util.Collection;
import java.util.Set;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {

	V put(K key, V value);

	V get(K key);

	boolean containsKey(K key);

	int size();

	Set<Entry<K, V>> entrySet();

	void clear();

	boolean isEmpty();

	Set<K> keySet();

	Collection<V> values();

	boolean containsValue(V value);

	void putAll(Map<? extends K, ? extends V> m);

	V remove(K key);

	interface Entry<K, V> {
		
		K getKey();

		V getValue();

		V setValue(V value);

	}

}
