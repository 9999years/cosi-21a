/**
 * Rebecca Turner (rebeccaturner@brandeis.edu)
 * license: GPLv3
 */

import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Spliterator;

import java.util.Objects;

import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

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
	protected class EmptyIterator implements ListIterator<T> {
		public boolean hasPrevious() {
			return false;
		}

		public T previous() {
			throw new NoSuchElementException();
		}

		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		public boolean hasNext() {
			return false;
		}

		public T next() {
			throw new NoSuchElementException();
		}

		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		public void add(T t) {
			throw new UnsupportedOperationException();
		}

		public void set(T t) {
			throw new UnsupportedOperationException();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * iterates over the list ONCE; starts with the cursor before the first
	 * element; all methods are O(1)
	 */
	protected class SingleIterator implements ListIterator<T> {
		/**
		 * our "cursor" is between current and current.next
		 *
		 * before the list / after list end: current == first.prev
		 */
		protected Node current = CircularList.this.first.prev;
		/**
		 * last node returned
		 */
		protected Node last = null;
		/**
		 * inx of current
		 */
		protected int inx = -1;

		/**
		 * these are factored out into methods so that we can override
		 * them in the InfiniteIterator to loop the indicies around
		 */
		protected void decrementIndex() {
			inx--;
		}

		protected void incrementIndex() {
			inx++;
		}

		public boolean hasNext() {
			return inx < CircularList.this.size - 1;
		}

		public boolean hasPrevious() {
			return inx >= 0;
		}

		/**
		 * cursor is in front of current, so we advance the cursor
		 * to the next value and then return the current value
		 */
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			current = current.next;
			last = current;
			incrementIndex();
			return last.data;
		}

		/**
		 * cursor is in front of current, so we take our value at
		 * current (to return) and then rewind the cursor
		 */
		public T previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			last = current;
			current = current.prev;
			decrementIndex();
			return last.data;
		}

		public int nextIndex() {
			return inx + 1;
		}

		public int previousIndex() {
			return inx;
		}

		public void add(T t) {
			CircularList.this.addBefore(current, t);
		}

		public void remove() {
			// no last node
			if(last == null) {
				throw new IllegalStateException();
			}
			CircularList.this.remove(last);
		}

		public void set(T t) {
			replace(last, t);
		}

		// spliterator methods

		public int characteristics() {
			return Spliterator.SIZED;
		}

		public long estimateSize() {
			return CircularList.this.size;
		}

		/**
		 * this doesn't do anything, ever. sorry threads!
		 */
		public Spliterator<T> trySplit() {
			// lol
			return null;
		}
	}

	/**
	 * https://i.imgur.com/JRQ5S3v.jpg
	 */
	protected class InfiniteIterator extends SingleIterator {
		/**
		 * decrements the index safely. it seems like this is a
		 * use-case for mod but that can return negative numbers in
		 * java soooo
		 */
		protected void decrementIndex() {
			inx--;
			if(inx < 0) {
				// such that inx = -1 goes to inx = size
				// - 1 (last el in list)
				inx += CircularList.this.size;
			}
		}

		protected void incrementIndex() {
			inx++;
			if(inx >= CircularList.this.size) {
				// such that inx = size goes to 0
				inx -= CircularList.this.size;
			}
		}

		public boolean hasNext() {
			return true;
		}

		public boolean hasPrevious() {
			return true;
		}
	}

	/**
	 * iterates over the list once
	 */
	public ListIterator<T> iterator() {
		// return an empty iterator for an empty list so we don't have
		// to worry about null cases in the actual iterators
		return isEmpty() ? new EmptyIterator() : new SingleIterator();
	}

	/**
	 * iterates over the list forever
	 */
	public ListIterator<T> infiniteIterator() {
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
	 *
	 * O(1) time
	 */
	public void add(T t) {
		addBefore(first, t);
	}

	/**
	 * inserts the T after the given element in the list
	 * allows negative indicies because why not? that's your problem.
	 * O(n) amortized time
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
	 * adds all the elements in the given collection at the end of the list.
	 * O(n) time
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
			return ret;
		}

		// bridge over n
		n.prev.next = n.next;
		n.next.prev = n.prev;

		if(n == first) {
			// if we've removed the first node and the list isn't
			// empty
			first = first.next;
		}

		return ret;
	}

	protected void replace(Node n, T t) {
		Node replacement = new Node(n.prev, t, n.next);
		n.prev.next = replacement;
		n.next.prev = replacement;
	}

	/**
	 * removes and returns the first element in the list. O(1) time
	 */
	public T removeFront() {
		return remove(first);
	}

	/**
	 * O(n) amortized time
	 */
	public T remove(int i) {
		return remove(node(i));
	}

	/**
	 * removes the given data from the list. O(n) amortized time
	 * @return if any elements were deleted
	 */
	public boolean remove(T data) {
		Iterator<T> itr = iterator();
		while(itr.hasNext()) {
			T t = itr.next();
			if(Objects.equals(data, t)) {
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
	 * because why not? that's your problem. O(n) amortized time
	 * @param i index
	 * @throws NoSuchElementException if the list is empty or if |i| &gt;=
	 * the list's size
	 */
	public T get(int i) {
		return node(i).data;
	}

	/**
	 * O(1) time
	 */
	public T getFront() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		return first.data;
	}

	/**
	 * O(n) time
	 */
	public String toString() {
		return Iterables.toString(this);
	}
}
