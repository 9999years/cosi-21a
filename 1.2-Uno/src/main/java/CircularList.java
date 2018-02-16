import java.lang.Iterable;
import java.util.Iterator;

import java.lang.NoSuchElementException;

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
		Node curr = this.CircularList.first.prev;
		int consumed = 0;

		public boolean hasNext() {
			return consumed < this.CircularList.size;
		}

		public T next() {
			if(hasNext()) {
				consumed++;
				curr = curr.next;
				return curr;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			consumed--;
			this.CircularList.remove(curr);
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
	 * inserts the T before the first element in the list / after the tail
	 *
	 * don't think about it too hard or your head will hurt
	 */
	public void insertEnd(T t) {
		if(isEmpty()) {
			// (insert crab cycle joke)
			first = new Node(t);
			first.prev = first;
			first.next = first;
		}

		Node adding = new Node(first.prev, t, first);
		first.prev.next = adding;
		first.prev = adding;

		size++;
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
			// advance first
			T ret = n.data;
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
		remove(first);
	}

	/**
	 * removes the given data from the list
	 * @return if any elements were deleted
	 */
	public boolean remove(T data) {
		Iterator<T> itr = iterator();
		while(itr.hasNext()) {
			T t = itr.next();
			if((data == null && t == null)
				|| (data != null && data.equals(t))) {
				itr.remove();
				return true;
			}
		}
		return false;
	}
}
