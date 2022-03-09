package org.training.datastructures.map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HashMapTest {

	@Test
	@DisplayName("create map with default constructor")
	void testHashMap() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		assertTrue(map.isEmpty());
		assertTrue(map.keySet().isEmpty());
		assertTrue(map.values().isEmpty());
	}

	@Test
	@DisplayName("create map with copy constructor")
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
	}

	@Test
	@DisplayName("add new entry with put")
	void testPutNewElement() {
		var map = new HashMap<>();
		assertEquals(0, map.size());
		assertFalse(map.containsKey("1"));
		
		map.put("1", 1);
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
		assertFalse(map.containsKey("1"));

		map.put("1", 1);
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
	@DisplayName("find entry with get")
	void testGetSuccess() {
		var map = new HashMap<>();
		map.put("1", 1);
		assertEquals(1, map.get("1"));		
	}

	@Test
	@DisplayName("can't find entry with get")
	void testGetFail() {
		var map = new HashMap<>();
		map.put("1", 1);
		assertEquals(null, map.get("2"));		
		assertEquals(null, map.get("3"));		
	}

	@Test
	@DisplayName("containsKey finds entry and returns true")
	void testContainsKeySuccess() {
		var map = new HashMap<>();
		assertFalse(map.containsKey("1"));		
		map.put("1", 1);
		assertTrue(map.containsKey("1"));		
	}

	@Test
	@DisplayName("containsKey can't find entry and returns false")
	void testContainsKeyFail() {
		var map = new HashMap<>();
		assertFalse(map.containsKey("1"));		
		map.put("1", 1);
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
	@DisplayName("check isEmpty for empty map")
	void testIsEmptySuccess() {
		var map = new HashMap<>();
		assertTrue(map.isEmpty());
	}

	@Test
	@DisplayName("check isEmpty for non-empty map")
	void testIsEmptyFail() {
		var map = new HashMap<>();
		map.put("1", 1);
		assertFalse(map.isEmpty());
	}

	@Test
	@DisplayName("testing keySet")
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
	@DisplayName("testing values")
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
	@DisplayName("testing entrySet")
	void testEntrySet() {
		var map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);
//TODO
		assertEquals(Set.of(1, 2, 3, 4, 5), map.entrySet());
	}

	@Test
	@DisplayName("check if containsValue returns false/true for absent/present key")
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
	@DisplayName("remove succeeded")
	void testContainsRemoveSuccess() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("remove failed")
	void testContainsRemoveFail() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("putAll adds all elements")
	void testPutAll() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("yields iterator object to browse map")
	void testIterator() {
		fail("Not yet implemented");
	}

}
