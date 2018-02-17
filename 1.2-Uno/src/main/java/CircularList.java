import java.lang.Iterable;
import java.util.Iterator;

import java.util.NoSuchElementException;

/**
 * circular linked list of type T; this allows us to separate our player logic
 * (having a hand and playing uno) from our game logic (where the players are
 * in relation to each other)
 *
 * circular lists are notoriously bug-prone, so i think this is silly. but the
 * assignment requests it, so here we are...
 *
 * anyways, as a bug-mitigation measure, internally, there is only one removal
 * and one insertion method. the default iterator only traverses the list once,
 * but an infiniteIterator is supplied which never ends
 */
public class CircularList<T> implements Iterable<T> {
	protected class Node {
		final T data;
		Node next;
		Node prev;

		Node(Node prev, T data, Node next) {
			this.prev = prev;
			this.data = data;
			this.next = next;
		}

		Node(T data) {
			this(null, data, null);
		}
	}

	/**
	 * iterator of no elements
	 */
	protected class EmptyIterator implements Iterator<T> {
		public boolean hasNext() {
			return false;
		}

		public T next() {
			throw new NoSuchElementException();
		}
	}

	/**
	 * iterates over the list ONCE
	 */
	protected class SingleIterator implements Iterator<T> {
		Node curr = CircularList.this.first.prev;
		int consumed = 0;

		public boolean hasNext() {
			return consumed < CircularList.this.size;
		}

		public T next() {
			if(hasNext()) {
				consumed++;
				curr = curr.next;
				return curr.data;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			consumed--;
			CircularList.this.remove(curr);
			if(curr == CircularList.this.first) {
				CircularList.this.first =
					CircularList.this.first.next;
			}
		}
	}

	protected class InfiniteIterator extends SingleIterator
			implements Iterator<T> {
		public boolean hasNext() {
			return true;
		}

		public T next() {
			return super.next();
		}
	}

	/**
	 * iterates over the list once
	 */
	public Iterator<T> iterator() {
		// return an empty iterator for an empty list so we don't have
		// to worry about null cases in the actual iterators
		return isEmpty() ? new EmptyIterator() : new SingleIterator();
	}

	/**
	 * iterates over the list forever
	 */
	public Iterator<T> infiniteIterator() {
		return isEmpty() ? new EmptyIterator() : new InfiniteIterator();
	}

	/**
	 * a nebulously-defined concept within any sort of polygon
	 */
	protected Node first;
	protected int size = 0;

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * adds data t before a given node n, or at the start of the list if n
	 * is null or the list is empty
	 */
	protected void addBefore(Node n, T t) {
		if(isEmpty() || n == null) {
			// (insert crab cycle joke)
			first = new Node(t);
			first.prev = first;
			first.next = first;
		} else {
			Node adding = new Node(n.prev, t, n);
			n.prev.next = adding;
			n.prev = adding;
		}
		size++;
	}

	/**
	 * inserts the T before the first element in the list / after the tail
	 *
	 * don't think about it too hard or your head will hurt
	 */
	public void add(T t) {
		addBefore(first, t);
	}

	/**
	 * inserts the T after the given element in the list
	 * allows negative indicies because why not? that's your problem.
	 * @param i index to insert at; this is the new element's index
	 * @throws NoSuchElementException if the list is empty or if |i| >= the
	 * list's size
	 */
	public void add(T t, int i) {
		addBefore(node(i), t);
		if(i == 0) {
			// if we've inserted at the first node, we don't expect
			// our new element to be at the end of the list
			first = first.prev;
		}
	}

	/**
	 * adds all the elements in the given collection at the end of the list
	 */
	public void addAll(Iterable<T> collection) {
		for(T t : collection) {
			add(t);
		}
	}

	protected T remove(Node n) {
		size--;
		T ret = n.data;
		if(size == 0) {
			first = null;
		} else {
			// bridge over n
			n.prev.next = n.next;
			n.next.prev = n.prev;
		}

		if(n == first && size > 1) {
			// if we've removed the first node and there's more
			// than one node
			first = first.next;
		}

		return ret;
	}

	/**
	 * removes and returns the first element in the list
	 */
	public T removeFront() {
		return remove(first);
	}

	public T remove(int i) {
		return remove(node(i));
	}

	/**
	 * removes the given data from the list
	 * @return if any elements were deleted
	 */
	public boolean remove(T data) {
		Iterator<T> itr = iterator();
		while(itr.hasNext()) {
			T t = itr.next();
			if(Equality.nullableEquals(data, t)) {
				itr.remove();
				return true;
			}
		}
		return false;
	}

	protected Node node(int i) {
		if(isEmpty() || i >= size || i <= -size) {
			throw new NoSuchElementException();
		}

		Node curr = first;
		if(i > 0) {
			while(i != 0) {
				curr = curr.next;
				i--;
			}
		} else {
			while(i != 0) {
				curr = curr.prev;
				i++;
			}
		}
		return curr;
	}

	/**
	 * get an element at a specified index; allows negative indicies
	 * because why not? that's your problem.
	 * @param i index
	 * @throws NoSuchElementException if the list is empty or if |i| &gt;=
	 * the list's size
	 */
	public T get(int i) {
		return node(i).data;
	}

	public T getFront() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return first.data;
	}

	public String toString() {
		return Iterables.toString(this);
	}
}
