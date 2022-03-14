package org.training.datastructures.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import org.training.datastructures.list.ArrayList;
import org.training.datastructures.list.LinkedList;
import org.training.datastructures.list.List;

public class HashMap<K, V> implements Map<K, V> {

	private static final int DEFAULT_BUCKET_LIST_SIZE = 10;

	private List<? extends List<Entry<K, V>>> buckets;

	public HashMap() {
		this(DEFAULT_BUCKET_LIST_SIZE);
	}

	public HashMap(int bucketListSize) {
		if (bucketListSize <= 0) {
			throw new IllegalArgumentException(
					String.format("initial capacity %d should be greater 0", bucketListSize));
		}
		buckets = createBucketList(bucketListSize);
	}

	private List<? extends List<Entry<K, V>>> createBucketList(int bucketListSize) {
		var newBucketList = new ArrayList<LinkedList<Entry<K, V>>>(bucketListSize);
		createBuckets(newBucketList, bucketListSize);
		return newBucketList;
	}

	private <T extends List<Entry<K, V>>> void createBuckets(List<T> bucketList, int bucketListSize) {
		for (int k = 0; k < bucketListSize; k++) {
			bucketList.add((T) new LinkedList<Entry<K, V>>());
		}
	}

	public HashMap(Map<K, V> map) {
		this(map.size());
		putAll(map);
	}

	@Override
	public V put(K key, V value) {
		Optional<V> originalValue = locateAndApply(new MapEntry<>(key, value), Optional.of(ListIterator::set),
				Optional.of(ListIterator::add));
		return originalValue.orElse(null);
	}

	private Optional<V> locateAndApply(Entry<K, V> entry,
			Optional<BiConsumer<ListIterator<Entry<K, V>>, Entry<K, V>>> onSuccessAction,
			Optional<BiConsumer<ListIterator<Entry<K, V>>, Entry<K, V>>> onFailAction) {
		final K key = entry.getKey();
		var i = getBucketList(key).listIterator();
		while (i.hasNext()) {
			var originalEntry = i.next();
			var originalValue = originalEntry.getValue();
			if (Objects.equals(originalEntry.getKey(), key)) {
				onSuccessAction.ifPresent(action -> action.accept(i, entry));
				return Optional.of(originalValue);
			}
		}
		onFailAction.ifPresent(action -> action.accept(i, entry));
		return Optional.empty();
	}

	private List<Entry<K, V>> getBucketList(K key) {
		int bucketIndex = bucketIndex(key);
		return buckets.get(bucketIndex);
	}

	private int bucketIndex(K key) {
		long index = key.hashCode() - (long) Integer.MIN_VALUE;
		return (int) (index % buckets.size());
	}

	@Override
	public V get(K key) {
		Optional<V> originalValue = locateAndApply(new MapEntry<>(key, null), Optional.empty(), Optional.empty());
		return originalValue.orElse(null);
	}

	@Override
	public boolean containsKey(K key) {
		Optional<V> originalValue = locateAndApply(new MapEntry<>(key, null), Optional.empty(), Optional.empty());
		return originalValue.isPresent();
	}

	@Override
	public int size() {
		int size = 0;
		for (var list : buckets) {
			size += list.size();
		}
		return size;
	}

	@Override
	public void clear() {
		for (var list : buckets) {
			list.clear();
		}
	}

	@Override
	public boolean isEmpty() {
		return !iterator().hasNext();
	}

	@Override
	public V remove(K key) {
		Optional<V> originalValue = locateAndApply(new MapEntry<>(key, null),
				Optional.of((iter, entry) -> iter.remove()), Optional.empty());
		return originalValue.orElse(null);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (var entry : m) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public boolean containsValue(V value) {
		var i = iterator();
		while (i.hasNext()) {
			var entry = i.next();
			if (Objects.equals(entry.getValue(), value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringJoiner join = new StringJoiner(",", "[", "]");
		for (var entry : this) {
			join.add(entry.getKey() + "=" + entry.getValue());
		}
		return join.toString();
	}

	@Override
	public int hashCode() {
		return entrySet().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof HashMap<?, ?> map) {
			return entrySet().equals(map.entrySet());
		}
		return false;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new Iterator<Entry<K, V>>() {

			private final Iterator<? extends List<Entry<K, V>>> bucketIterator = buckets.iterator();
			private Iterator<Entry<K, V>> listIterator = null;

			private Iterator<Entry<K, V>> getIterator() {
				while ((listIterator == null || !listIterator.hasNext()) && bucketIterator.hasNext()) {
					listIterator = bucketIterator.next().iterator();
				}
				return listIterator;
			}

			@Override
			public boolean hasNext() {
				var i = getIterator();
				return (i != null && i.hasNext());
			}

			@Override
			public Entry<K, V> next() {
				if (listIterator == null || !listIterator.hasNext()) {
					throw new NoSuchElementException("no more elements in map");
				}
				return listIterator.next();
			}

		};
	}

	@Override
	public Set<K> keySet() {
		return new Set<K>() {

			@Override
			public int size() {
				return HashMap.this.size();
			}

			@Override
			public boolean isEmpty() {
				return HashMap.this.isEmpty();
			}

			@Override
			public boolean contains(Object key) {
				return HashMap.this.containsKey((K) key);
			}

			@Override
			public Iterator<K> iterator() {
				return new Iterator<>() {

					private final Iterator<Entry<K, V>> i = HashMap.this.iterator();

					@Override
					public boolean hasNext() {
						return i.hasNext();
					}

					@Override
					public K next() {
						return i.next().getKey();
					}

					@Override
					public void remove() {
						i.remove();
					}

				};
			}

			@Override
			public Object[] toArray() {
				Object[] array = new Object[HashMap.this.size()];
				fillInArrayData(array);
				return array;
			}

			@Override
			public K[] toArray(Object[] a) {
				final int mapSize = HashMap.this.size();
				Object[] array = a.length >= mapSize ? a : new Object[mapSize];
				fillInArrayData(array);
				return (K[]) array;
			}

			private void fillInArrayData(Object[] array) {
				int k = 0;
				for (var value : HashMap.this) {
					array[k++] = value;
				}
			}

			@Override
			public boolean add(Object e) {
				throw new UnsupportedOperationException("backing set for keys doesn't support 'add' operation");
			}

			@Override
			public boolean remove(Object key) {
				return HashMap.this.remove((K) key) != null;
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				for (var key : collection) {
					if (!HashMap.this.containsKey((K) key)) {
						return false;
					}
				}
				return true;
			}

			@Override
			public boolean addAll(Collection<? extends K> c) {
				throw new UnsupportedOperationException("backing set for keys doesn't support 'addAll' operation");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				boolean changed = false;
				var i = iterator();
				while (i.hasNext()) {
					if (!collection.contains(i.next())) {
						i.remove();
						changed = true;
					}
				}
				return changed;
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				boolean changed = false;
				var i = iterator();
				while (i.hasNext()) {
					if (collection.contains(i.next())) {
						i.remove();
						changed = true;
					}
				}
				return changed;
			}

			@Override
			public void clear() {
				HashMap.this.clear();
			}

			@Override
			public int hashCode() {
				return Objects.hash(toArray());
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Collection<?> collection) {
					return containsAll(collection) && collection.containsAll(this);
				}
				return false;
			}

			@Override
			public String toString() {
				StringJoiner join = new StringJoiner(",", "[", "]");
				for (var key : this) {
					join.add(key.toString());
				}
				return join.toString();
			}

		};
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new Set<Entry<K, V>>() {

			@Override
			public int size() {
				return HashMap.this.size();
			}

			@Override
			public boolean isEmpty() {
				return HashMap.this.isEmpty();
			}

			@Override
			public boolean contains(Object entry) {
				return HashMap.this.containsKey(((Entry<K, V>) entry).getKey());
			}

			@Override
			public Iterator<Entry<K, V>> iterator() {
				return HashMap.this.iterator();
			}

			@Override
			public Object[] toArray() {
				Object[] array = new Object[HashMap.this.size()];
				fillInArrayData(array);
				return array;
			}

			@Override
			public <T> T[] toArray(T[] a) {
				final int mapSize = HashMap.this.size();
				Object[] array = a.length >= mapSize ? a : new Object[mapSize];
				fillInArrayData(array);
				return (T[]) array;
			}

			private void fillInArrayData(Object[] array) {
				int k = 0;
				for (var value : HashMap.this) {
					array[k++] = value;
				}
			}

			@Override
			public boolean add(Entry<K, V> e) {
				throw new UnsupportedOperationException("backing set for entries doesn't support 'add' operation");
			}

			@Override
			public boolean remove(Object entry) {
				return HashMap.this.remove(((Entry<K, V>) entry).getKey()) != null;
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				for (var entry : collection) {
					if (!HashMap.this.containsKey(((Entry<K, V>) entry).getKey())) {
						return false;
					}
				}
				return true;
			}

			@Override
			public boolean addAll(Collection<? extends Entry<K, V>> c) {
				throw new UnsupportedOperationException("backing set for entries doesn't support 'addAll' operation");
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				boolean changed = false;
				var i = iterator();
				while (i.hasNext()) {
					if (!collection.contains(i.next())) {
						i.remove();
						changed = true;
					}
				}
				return changed;
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				boolean changed = false;
				var i = iterator();
				while (i.hasNext()) {
					if (collection.contains(i.next())) {
						i.remove();
						changed = true;
					}
				}
				return changed;
			}

			@Override
			public void clear() {
				HashMap.this.clear();
			}

			@Override
			public int hashCode() {
				return Objects.hash(toArray());
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Collection<?> collection) {
					return containsAll(collection) && collection.containsAll(this);
				}
				return false;
			}

			@Override
			public String toString() {
				StringJoiner join = new StringJoiner(",", "[", "]");
				for (var entry : this) {
					join.add(entry.getKey() + "=" + entry.getValue());
				}
				return join.toString();
			}

		};
	}

	@Override
	public Collection<V> values() {
		return new Collection<V>() {

			@Override
			public int size() {
				return HashMap.this.size();
			}

			@Override
			public boolean isEmpty() {
				return HashMap.this.isEmpty();
			}

			@Override
			public boolean contains(Object value) {
				return HashMap.this.containsValue((V) value);
			}

			@Override
			public Iterator<V> iterator() {
				return new Iterator<>() {

					private final Iterator<Entry<K, V>> i = HashMap.this.iterator();

					@Override
					public boolean hasNext() {
						return i.hasNext();
					}

					@Override
					public V next() {
						return i.next().getValue();
					}

					@Override
					public void remove() {
						i.remove();
					}

				};
			}

			@Override
			public Object[] toArray() {
				Object[] array = new Object[HashMap.this.size()];
				fillInArrayData(array);
				return array;
			}

			@Override
			public <T> T[] toArray(T[] a) {
				final int mapSize = HashMap.this.size();
				Object[] array = a.length >= mapSize ? a : new Object[mapSize];
				fillInArrayData(array);
				return (T[]) array;
			}

			private void fillInArrayData(Object[] array) {
				int k = 0;
				for (var value : HashMap.this) {
					array[k++] = value;
				}
			}

			@Override
			public boolean add(V e) {
				throw new UnsupportedOperationException("backing set for values doesn't support 'add' operation");
			}

			@Override
			public boolean remove(Object value) {
				var i = iterator();
				while (i.hasNext()) {
					if (Objects.equals(i.next(), value)) {
						i.remove();
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				for (var value : collection) {
					if (!HashMap.this.containsValue((V) value)) {
						return false;
					}
				}
				return true;
			}

			@Override
			public boolean addAll(Collection<? extends V> c) {
				throw new UnsupportedOperationException("backing set for values doesn't support 'addAll' operation");
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				boolean changed = false;
				var i = iterator();
				while (i.hasNext()) {
					if (collection.contains(i.next())) {
						i.remove();
						changed = true;
					}
				}
				return changed;
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				boolean changed = false;
				var i = iterator();
				while (i.hasNext()) {
					if (!collection.contains(i.next())) {
						i.remove();
						changed = true;
					}
				}
				return changed;
			}

			@Override
			public void clear() {
				HashMap.this.clear();
			}

			@Override
			public int hashCode() {
				return Objects.hash(toArray());
			}

			@Override
			public boolean equals(Object o) {
				if (o instanceof Collection<?> collection) {
					return containsAll(collection) && collection.containsAll(this);
				}
				return false;
			}

			@Override
			public String toString() {
				StringJoiner join = new StringJoiner(",", "[", "]");
				for (var value : this) {
					join.add(value.toString());
				}
				return join.toString();
			}

		};
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
			if (o instanceof MapEntry<?, ?> entry) {
				return Objects.equals(key, entry.key);
			}
			return false;
		}

	}

}
