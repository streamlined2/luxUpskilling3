package org.training.datastructures.list;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<E> extends AbstractList<E> {

	private static class Node<E> {
		private E data;
		private Node<E> previous;
		private Node<E> next;

		private Node(E data) {
			this.data = data;
		}
	}

	private int size;
	private Node<E> head;
	private Node<E> tail;

	public LinkedList() {
		size = 0;
		head = tail = null;
	}

	public LinkedList(E... data) {
		this();
		var i = listIterator();
		for (E e : data) {
			i.add(e);
		}
	}

	@Override
	public void add(E value, int index) {
		listIterator(index).add(value);
	}

	@Override
	public E remove(int index) {
		Objects.checkIndex(index, size());
		var i = listIterator(index);
		E oldValue = i.next();
		i.remove();
		return oldValue;
	}

	@Override
	public E get(int index) {
		Objects.checkIndex(index, size());
		return listIterator(index).next();
	}

	@Override
	public E set(E value, int index) {
		Objects.checkIndex(index, size());
		var i = listIterator(index);
		E oldValue = i.next();
		i.set(value);
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new LinkedListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}

	private class LinkedListIterator implements ListIterator<E> {
		private Node<E> nextPointer;
		private Node<E> prevPointer;
		private int index;
		private Node<E> actionPointer;

		private LinkedListIterator() {
			nextPointer = head;
			prevPointer = null;
			index = 0;
			actionPointer = null;
		}

		private void shiftRight() {
			prevPointer = nextPointer;
			nextPointer = nextPointer.next;
			index++;
		}

		private void shiftLeft() {
			nextPointer = prevPointer;
			prevPointer = prevPointer.previous;
			index--;
		}

		private void moveLeftToRight(int moveToIndex) {
			nextPointer = head;
			prevPointer = null;
			index = 0;
			for (int k = 0; k < moveToIndex; k++) {
				shiftRight();
			}
		}

		private void moveRightToLeft(int moveToIndex) {
			nextPointer = null;
			prevPointer = tail;
			index = size();
			for (int k = size(); k > moveToIndex; k--) {
				shiftLeft();
			}
		}

		private void moveTo(int moveToIndex) {
			if (moveToIndex < size() / 2) {
				moveLeftToRight(moveToIndex);
			} else {
				moveRightToLeft(moveToIndex);
			}
		}

		private LinkedListIterator(int startIndex) {
			this();
			Objects.checkIndex(startIndex, size() + 1);
			moveTo(startIndex);
		}

		@Override
		public boolean hasNext() {
			return Objects.nonNull(nextPointer);
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException("no more elements to the right, iterator exhausted");
			}
			E value = nextPointer.data;
			actionPointer = nextPointer;
			shiftRight();
			return value;
		}

		@Override
		public boolean hasPrevious() {
			return Objects.nonNull(prevPointer);
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException("no more elements to the left, iterator exhausted");
			}
			E value = prevPointer.data;
			actionPointer = prevPointer;
			shiftLeft();
			return value;
		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Override
		public void remove() {
			if (Objects.isNull(actionPointer)) {
				throw new IllegalStateException("either 'next' or 'previous' should be called first");
			}
			if (Objects.isNull(actionPointer.previous) && Objects.isNull(actionPointer.next)) {
				removeLastNode();
			} else if (Objects.isNull(actionPointer.previous)) {
				removeHead();
			} else if (Objects.isNull(actionPointer.next)) {
				removeTail();
			} else {
				removeIntermediateNode();
			}
			actionPointer = null;
			size--;
		}

		private void removeIntermediateNode() {
			actionPointer.previous.next = actionPointer.next;
			actionPointer.next.previous = actionPointer.previous;
			if (actionPointer == prevPointer) {
				prevPointer = nextPointer.previous;
			} else {
				nextPointer = prevPointer.next;
			}
		}

		private void removeLastNode() {
			head = tail = null;
			nextPointer = prevPointer = null;
			index = 0;
		}

		private void removeHead() {
			nextPointer = head = actionPointer.next;
			prevPointer = head.previous = null;
			index = 0;
		}

		private void removeTail() {
			prevPointer = tail = actionPointer.previous;
			nextPointer = tail.next = null;
			index = size - 1;
		}

		@Override
		public void set(E e) {
			actionPointer.data = e;
			actionPointer = null;
		}

		@Override
		public void add(E e) {
			var newNode = new Node<>(e);
			if (Objects.isNull(prevPointer) && Objects.isNull(nextPointer)) {
				addFirstNode(newNode);
			} else if (Objects.isNull(prevPointer)) {
				addHead(newNode);
			} else if (Objects.isNull(nextPointer)) {
				addTail(newNode);
			} else {
				addIntermediateNode(newNode);
			}
			index++;
			size++;
		}

		private void addIntermediateNode(Node<E> node) {
			node.previous = prevPointer;
			node.next = nextPointer;
			nextPointer.previous = prevPointer.next = node;
			prevPointer = node;
		}

		private void addTail(Node<E> node) {
			node.previous = tail;
			tail.next = node;
			prevPointer = tail = node;
		}

		private void addHead(Node<E> node) {
			node.next = head;
			head.previous = node;
			prevPointer = head = node;
		}

		private void addFirstNode(Node<E> node) {
			head = tail = node;
			nextPointer = null;
			prevPointer = node;
		}

	}

}
