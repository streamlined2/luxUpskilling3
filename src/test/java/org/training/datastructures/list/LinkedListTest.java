package org.training.datastructures.list;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LinkedListTest {

	@Test
	@DisplayName("testing default constructor")
	void testLinkedList() {
		var list = new LinkedList<String>();
		assertEquals(0, list.size());
		assertArrayEquals(new Object[] {}, list.toArray());
	}

	@Test
	@DisplayName("testing varargs constructor")
	void testLinkedListEArray() {
		final var data = new String[] { "A", "B", "C", "D", "E", "F" };
		final var list = new LinkedList<>(data);
		assertEquals(data.length, list.size());
		assertArrayEquals(data, list.toArray());
	}

	@Test
	@DisplayName("testing clear method")
	void testClear() {
		final var data = new String[] { "A", "B", "C" };
		final var list = new LinkedList<>(data);
		assertEquals(data.length, list.size());
		assertArrayEquals(data, list.toArray());
		assertFalse(list.isEmpty());
		list.clear();
		assertEquals(0, list.size());
		assertArrayEquals(new Object[] {}, list.toArray());
		assertTrue(list.isEmpty());
	}

	@Test
	@DisplayName("testing add to tail method")
	void testAdd() {
		final var list = new LinkedList<String>();
		assertTrue(list.isEmpty());
		list.add("A");
		assertFalse(list.isEmpty());
		assertEquals(1, list.size());
		assertArrayEquals(new Object[] { "A" }, list.toArray());
		list.add("B");
		assertFalse(list.isEmpty());
		assertEquals(2, list.size());
		assertArrayEquals(new Object[] { "A", "B" }, list.toArray());
		list.add("C");
		assertFalse(list.isEmpty());
		assertEquals(3, list.size());
		assertArrayEquals(new Object[] { "A", "B", "C" }, list.toArray());
	}

	@Test
	@DisplayName("testing add to head method")
	void testAddToHead() {
		final var list = new LinkedList<String>();
		assertTrue(list.isEmpty());
		list.add("A", 0);
		assertFalse(list.isEmpty());
		assertEquals(1, list.size());
		assertArrayEquals(new Object[] { "A" }, list.toArray());
		list.add("B", 0);
		assertFalse(list.isEmpty());
		assertEquals(2, list.size());
		assertArrayEquals(new Object[] { "B", "A" }, list.toArray());
		list.add("C", 0);
		assertFalse(list.isEmpty());
		assertEquals(3, list.size());
		assertArrayEquals(new Object[] { "C", "B", "A" }, list.toArray());
	}

	@Test
	@DisplayName("testing add at random index method")
	void testAddRandomIndex() {
		final var list = new LinkedList<String>("A", "B", "C", "D", "E");
		assertEquals(5, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("X", -1));
		list.add("X", 1);
		assertEquals(6, list.size());
		assertArrayEquals(new Object[] { "A", "X", "B", "C", "D", "E" }, list.toArray());
		list.add("Y", 3);
		assertEquals(7, list.size());
		assertArrayEquals(new Object[] { "A", "X", "B", "Y", "C", "D", "E" }, list.toArray());
		list.add("Z", 6);
		assertEquals(8, list.size());
		assertArrayEquals(new Object[] { "A", "X", "B", "Y", "C", "D", "Z", "E" }, list.toArray());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("X", list.size() + 1));
	}

	@Test
	@DisplayName("testing set at random index method")
	void testSetIndex() {
		final var list = new LinkedList<String>("A", "B", "C", "D", "E");
		assertArrayEquals(new Object[] { "A", "B", "C", "D", "E" }, list.toArray());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("X", -1));
		list.set("X", 0);
		assertArrayEquals(new Object[] { "X", "B", "C", "D", "E" }, list.toArray());
		list.set("Y", 2);
		assertArrayEquals(new Object[] { "X", "B", "Y", "D", "E" }, list.toArray());
		list.set("Z", 4);
		assertArrayEquals(new Object[] { "X", "B", "Y", "D", "Z" }, list.toArray());
		assertThrows(IndexOutOfBoundsException.class, () -> list.set("X", 5));
	}

	@Test
	@DisplayName("testing get at random index method")
	void testGetIndex() {
		final var list = new LinkedList<String>("A", "B", "C", "D", "E");
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		assertEquals("D", list.get(3));
		assertEquals("E", list.get(4));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
	}

	@Test
	@DisplayName("testing contains method")
	void testContains() {
		final var list = new LinkedList<>("A", "B", "C");
		assertTrue(list.contains("A"));
		assertTrue(list.contains("B"));
		assertTrue(list.contains("C"));
		assertFalse(list.contains("X"));
		assertFalse(list.contains("Y"));
		assertFalse(list.contains("Z"));
	}

	@Test
	@DisplayName("testing indexOf method")
	void testIndexOf() {
		final var list = new LinkedList<>("A", "B", "C");
		assertEquals(0, list.indexOf("A"));
		assertEquals(1, list.indexOf("B"));
		assertEquals(2, list.indexOf("C"));
		assertEquals(-1, list.indexOf("X"));
		assertEquals(-1, list.indexOf("Y"));
		assertEquals(-1, list.indexOf("Z"));
	}

	@Test
	@DisplayName("testing indexOf method with starting index")
	void testIndexOfStartingIndex() {
		final var list = new LinkedList<>("A", "B", "C", "A", "B", "C", "A", "B", "C");
		assertEquals(0, list.indexOf("A", 0));
		assertEquals(3, list.indexOf("A", 1));
		assertEquals(6, list.indexOf("A", 4));
		assertEquals(-1, list.indexOf("A", 7));
		assertEquals(1, list.indexOf("B", 0));
		assertEquals(4, list.indexOf("B", 2));
		assertEquals(7, list.indexOf("B", 5));
		assertEquals(-1, list.indexOf("B", 8));
	}

	@Test
	@DisplayName("testing lastIndexOf method")
	void testLastIndexOf() {
		final var list = new LinkedList<>("A", "B", "C");
		assertEquals(0, list.lastIndexOf("A"));
		assertEquals(1, list.lastIndexOf("B"));
		assertEquals(2, list.lastIndexOf("C"));
		assertEquals(-1, list.lastIndexOf("X"));
		assertEquals(-1, list.lastIndexOf("Y"));
		assertEquals(-1, list.lastIndexOf("Z"));
	}

	@Test
	@DisplayName("testing lastIndexOf method with starting index")
	void testLastIndexOfStartingIndex() {
		final var list = new LinkedList<>("A", "B", "C", "A", "B", "C", "A", "B", "C");
		assertEquals(6, list.lastIndexOf("A", 8));
		assertEquals(3, list.lastIndexOf("A", 5));
		assertEquals(0, list.lastIndexOf("A", 2));
		assertEquals(0, list.lastIndexOf("A", 1));
		assertEquals(-1, list.lastIndexOf("A", 0));
		assertEquals(7, list.lastIndexOf("B", 8));
		assertEquals(4, list.lastIndexOf("B", 7));
		assertEquals(4, list.lastIndexOf("B", 6));
		assertEquals(1, list.lastIndexOf("B", 3));
		assertEquals(1, list.lastIndexOf("B", 2));
		assertEquals(-1, list.lastIndexOf("B", 1));
		assertEquals(-1, list.lastIndexOf("B", 0));
	}

	@Test
	@DisplayName("testing string conversion method")
	void testToString() {
		final var list = new LinkedList<>("A", "B", "C");
		assertEquals("[A,B,C]", list.toString());
	}

	@Test
	@DisplayName("testing iterator beyond upper limit")
	void testIteratorExhaustedUpperLimit() {
		final var emptyList = new LinkedList<>();
		assertTrue(emptyList.isEmpty());
		final var i = emptyList.listIterator();
		assertFalse(i.hasNext());
		assertThrows(NoSuchElementException.class, () -> i.next());
	}

	@Test
	@DisplayName("testing iterator beyond lower limit")
	void testIteratorExhaustedLowerLimit() {
		final var emptyList = new LinkedList<>();
		assertTrue(emptyList.isEmpty());
		final var i = emptyList.listIterator();
		assertFalse(i.hasPrevious());
		assertThrows(NoSuchElementException.class, () -> i.previous());
	}

	@Test
	@DisplayName("testing removing items from tail")
	void testRemoveFromTail() {
		final var list = new LinkedList<>("A", "B", "C");
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(3));
		list.remove(2);
		assertArrayEquals(new Object[] { "A", "B" }, list.toArray());
		list.remove(1);
		assertArrayEquals(new Object[] { "A" }, list.toArray());
		list.remove(0);
		assertTrue(list.isEmpty());
		assertArrayEquals(new Object[] {}, list.toArray());
	}

	@Test
	@DisplayName("testing removing items from start")
	void testRemoveFromStart() {
		final var list = new LinkedList<>("A", "B", "C");
		list.remove(0);
		assertArrayEquals(new Object[] { "B", "C" }, list.toArray());
		list.remove(0);
		assertArrayEquals(new Object[] { "C" }, list.toArray());
		list.remove(0);
		assertTrue(list.isEmpty());
		assertArrayEquals(new Object[] {}, list.toArray());
	}

	@Test
	@DisplayName("testing removing items without calling previous/next")
	void testRemoveRemoveWithoutPreviousNext() {
		final var list = new LinkedList<>("A", "B", "C");
		final var i = list.listIterator();
		assertThrows(IllegalStateException.class, () -> i.remove());
	}

	@Test
	@DisplayName("testing removing intermediate items")
	void testRemoveInermediateItems() {
		final var list = new LinkedList<>("A", "B", "C", "D", "E");
		list.remove(1);
		assertArrayEquals(new Object[] { "A", "C", "D", "E" }, list.toArray());
		list.remove(2);
		assertArrayEquals(new Object[] { "A", "C", "E" }, list.toArray());
		list.remove(1);
		assertArrayEquals(new Object[] { "A", "E" }, list.toArray());
		list.remove(1);
		assertArrayEquals(new Object[] { "A" }, list.toArray());
		list.remove(0);
		assertTrue(list.isEmpty());
		assertArrayEquals(new Object[] {}, list.toArray());
	}

	@Test
	@DisplayName("testing removing items by iterator")
	void testRemoveItemsByIterator() {
		final var list = new LinkedList<>("A", "B", "C", "D", "E");
		final var i = list.listIterator(3);
		assertEquals("C", i.previous());
		i.remove();
		assertArrayEquals(new Object[] { "A", "B", "D", "E" }, list.toArray());
		assertEquals("B", i.previous());
		i.remove();
		assertArrayEquals(new Object[] { "A", "D", "E" }, list.toArray());
		assertEquals("A", i.previous());
		i.remove();
		assertArrayEquals(new Object[] { "D", "E" }, list.toArray());
		assertEquals("D", i.next());
		i.remove();
		assertArrayEquals(new Object[] { "E" }, list.toArray());
		assertEquals("E", i.next());
		i.remove();
		assertArrayEquals(new Object[] {}, list.toArray());
		assertTrue(list.isEmpty());
	}

}
