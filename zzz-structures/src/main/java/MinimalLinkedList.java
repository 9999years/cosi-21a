import java.lang.Exception;
import java.util.NoSuchElementException;

import java.util.function.Supplier;
import java.util.Objects;

/**
 * minimal linked list implementation; no iterators, no fancy methods, just the
 * basics; adding, peeking, removing at both ends
 */
public abstract class MinimalLinkedList<E> extends AbstractCollection<E> {
	protected class Node<E> {
		E value;
		Node<E> previous;
		Node<E> next;

		Node() {
		}

		Node(E value) {
			this(null, value, null);
		}

		Node(Node<E> previous, E value, Node<E> next) {
			this.value    = value;
			this.previous = previous;
			this.next     = next;
		}
	}

	protected Node<E> head;
	protected Node<E> tail;
	protected int size;

	MinimalLinkedList() {
		clear();
	}

	protected void throwIfEmpty() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	protected <T> T nullIfEmpty(Supplier<T> t) {
		return isEmpty() ? null : t.get();
	}

	protected void addAfter(Node<E> n, E e) {
		Objects.requireNonNull(n);
		Node<E> insert = new Node<E>(n, e, n.next);
		n.next.previous = insert;
		n.next = insert;
		size++;
	}

	protected void addBefore(Node<E> n, E e) {
		Objects.requireNonNull(n);
		addAfter(n.previous, e);
	}

	// DEQUE METHODS:

	public int size() {
		return size;
	}

	// MUTATORS

	@Override
	public void clear() {
		head = new Node<E>();
		tail = new Node<E>();
		head.next = tail;
		tail.previous = head;
		size = 0;
	}

	protected E remove(Node<E> n) {
		E value = n.value;
		n.previous.next = n.next;
		n.next.previous = n.previous;
		size--;
		return value;
	}

	// HEAD OPERATIONS

	// insertion:

	public void addFirst(E e) {
		addAfter(head, e);
	}

	// removing:

	public E removeFirst() {
		throwIfEmpty();
		return remove(head.next);
	}

	// examining:

	public E getFirst() {
		throwIfEmpty();
		return head.next.value;
	}

	// TAIL OPERATIONS

	public void addLast(E e) {
		addBefore(tail, e);
	}

	public E removeLast() {
		throwIfEmpty();
		return remove(tail.previous);
	}

	public E getLast() {
		throwIfEmpty();
		return tail.previous.value;
	}
}
