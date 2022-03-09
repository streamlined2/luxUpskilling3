package org.training.datastructures.map;

import static org.junit.jupiter.api.Assertions.*;
import static org.training.datastructures.map.HashMap.MapEntry;

import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HashMapTest {

	@Test
	@DisplayName("create empty map with default constructor")
	void testHashMap() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertTrue(map.keySet().isEmpty());
		assertTrue(map.values().isEmpty());
	}

	@Test
	@DisplayName("create filled-in map with copy constructor")
	void testHashMapCopy() {
		var sampleMap = new HashMap<>();
		sampleMap.put("1", 1);
		sampleMap.put("2", 2);
		sampleMap.put("3", 3);
		sampleMap.put("4", 4);
		sampleMap.put("5", 5);

		var map = new HashMap<>(sampleMap);
		assertEquals(5, map.size());
		assertFalse(map.isEmpty());
		assertEquals(Set.of("1", "2", "3", "4", "5"), map.keySet());
		assertEquals(Set.of(1, 2, 3, 4, 5), map.values());
		assertEquals(Set.of(new MapEntry<String, Integer>("1", 1), new MapEntry<String, Integer>("2", 2),
				new MapEntry<String, Integer>("3", 3), new MapEntry<String, Integer>("4", 4),
				new MapEntry<String, Integer>("5", 5)), map.entrySet());
	}

	@Test
	@DisplayName("add new entry with put")
	void testPutNewElement() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertFalse(map.containsKey("1"));

		map.put("1", 1);
		assertFalse(map.isEmpty());
		assertEquals(1, map.size());
		assertTrue(map.containsKey("1"));
		assertEquals(1, map.get("1"));
		assertFalse(map.containsKey("2"));

		map.put("2", 2);
		assertEquals(2, map.size());
		assertTrue(map.containsKey("2"));
		assertEquals(2, map.get("2"));
	}

	@Test
	@DisplayName("replace old entry with put")
	void testPutReplaceOldElement() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertFalse(map.containsKey("1"));

		map.put("1", 1);
		assertFalse(map.isEmpty());
		assertEquals(1, map.size());
		assertTrue(map.containsKey("1"));
		assertEquals(1, map.get("1"));

		map.put("1", 2);
		assertEquals(1, map.size());
		assertTrue(map.containsKey("1"));
		assertEquals(2, map.get("1"));

		map.put("2", 3);
		assertEquals(1, map.size());
		assertTrue(map.containsKey("1"));
		assertFalse(map.containsKey("2"));
		assertEquals(2, map.get("1"));
	}

	@Test
	@DisplayName("find entry with get successfully")
	void testGetSuccess() {
		var map = new HashMap<>();
		map.put("1", 1);
		assertEquals(1, map.get("1"));
	}

	@Test
	@DisplayName("failed to find entry with get")
	void testGetFail() {
		var map = new HashMap<>();
		map.put("1", 1);
		assertEquals(null, map.get("2"));
		assertEquals(null, map.get("3"));
	}

	@Test
	@DisplayName("check if map contains key")
	void testContainsKeySuccess() {
		var map = new HashMap<>();
		assertFalse(map.containsKey("1"));
		map.put("1", 1);
		assertTrue(map.containsKey("1"));
	}

	@Test
	@DisplayName("check if given key is absent in map")
	void testContainsKeyFail() {
		var map = new HashMap<>();
		assertFalse(map.containsKey("1"));
		assertFalse(map.containsKey("2"));
		map.put("1", 1);
		assertTrue(map.containsKey("1"));
		assertFalse(map.containsKey("2"));
	}

	@Test
	@DisplayName("check if size is correct")
	void testSize() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		map.put("1", 1);
		assertEquals(1, map.size());
		map.put("2", 2);
		assertEquals(2, map.size());
		map.remove("1");
		assertEquals(1, map.size());
		map.remove("2");
		assertEquals(0, map.size());
	}

	@Test
	@DisplayName("check if clear wipes map")
	void testClear() {
		var map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);
		assertEquals(5, map.size());
		assertFalse(map.isEmpty());

		map.clear();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
	}

	@Test
	@DisplayName("check for empty map")
	void testIsEmptySuccess() {
		var map = new HashMap<>();
		assertTrue(map.isEmpty());
	}

	@Test
	@DisplayName("check for non-empty map")
	void testIsEmptyFail() {
		var map = new HashMap<>();
		assertTrue(map.isEmpty());
		map.put("1", 1);
		assertFalse(map.isEmpty());
		map.put("2", 2);
		assertFalse(map.isEmpty());
	}

	@Test
	@DisplayName("checking set of keys")
	void testKeySet() {
		var map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);

		assertEquals(Set.of("1", "2", "3", "4", "5"), map.keySet());
	}

	@Test
	@DisplayName("checking set of values")
	void testValues() {
		var map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);

		assertEquals(Set.of(1, 2, 3, 4, 5), map.values());
	}

	@Test
	@DisplayName("checking set of entries")
	void testEntrySet() {
		var map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);

		assertEquals(Set.of(new MapEntry<String, Integer>("1", 1), new MapEntry<String, Integer>("2", 2),
				new MapEntry<String, Integer>("3", 3), new MapEntry<String, Integer>("4", 4),
				new MapEntry<String, Integer>("5", 5)), map.entrySet());
	}

	@Test
	@DisplayName("check if containsValue returns false/true for absent/present value")
	void testContainsValueSuccess() {
		var map = new HashMap<>();
		assertFalse(map.containsValue(1));
		assertFalse(map.containsValue(2));
		assertFalse(map.containsValue(3));
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		assertTrue(map.containsValue(1));
		assertTrue(map.containsValue(2));
		assertTrue(map.containsValue(3));
	}

	@Test
	@DisplayName("check if remove succeeds")
	void testRemoveSuccess() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		map.put("1", 1);
		assertEquals(1, map.size());
		var value = map.remove("1");
		assertEquals(0, map.size());
		assertEquals(1, value);
	}

	@Test
	@DisplayName("check if remove fails")
	void testRemoveFail() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		map.put("1", 1);
		assertEquals(1, map.size());
		var value = map.remove("2");
		assertEquals(1, map.size());
		assertNull(value);
	}

	@Test
	@DisplayName("check if putAll adds all elements")
	void testPutAll() {
		var sampleMap = new HashMap<>();
		sampleMap.put("1", 1);
		sampleMap.put("2", 2);
		sampleMap.put("3", 3);
		sampleMap.put("4", 4);
		sampleMap.put("5", 5);

		var map = new HashMap<>();
		assertTrue(map.isEmpty());
		map.putAll(sampleMap);
		assertEquals(sampleMap, map);
	}

	@Test
	@DisplayName("check if toString converts map to string")
	void testToString() {
		var map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);

		assertEquals("[1=1, 2=2, 3=3]", map.toString());
	}

	@Test
	@DisplayName("check if iterator yields object to browse map")
	void testIterator() {
		var map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);

		var i = map.iterator();
		assertTrue(i.hasNext());
		assertEquals(new MapEntry<String, Integer>("1", 1), i.next());
		assertTrue(i.hasNext());
		assertEquals(new MapEntry<String, Integer>("2", 2), i.next());
		assertTrue(i.hasNext());
		assertEquals(new MapEntry<String, Integer>("3", 3), i.next());
		assertFalse(i.hasNext());
		assertThrows(NoSuchElementException.class, i::next);
	}

}
