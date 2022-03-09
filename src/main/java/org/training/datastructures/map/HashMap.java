package org.training.datastructures.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class HashMap<K, V> implements Map<K, V> {
	
	public HashMap() {
	}
	
	public HashMap(Map<K, V> map) {		
	}

	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	public V get(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean containsKey(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	public int size() {
		// TODO Auto-generated method stub
		return -1;
	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean containsValue(V value) {
		// TODO Auto-generated method stub
		return false;
	}

	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
	}

	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		//TODO
		return null;
	}

	public Iterator<Entry<K, V>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	static class MapEntry<K, V> implements Entry<K, V> {
		
		private final K key;
		private V value;
		
		public MapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V originalValue = this.value;
			this.value = value;
			return originalValue;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(key);
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof MapEntry<?, ?> entry) {
				return Objects.equals(key, entry.key);
			}
			return false;
		}
		
	}

}
