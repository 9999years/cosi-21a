import java.lang.Exception;
import java.util.NoSuchElementException;

import java.util.function.Supplier;
import java.util.Objects;

import java.lang.Comparable;
import java.lang.StringBuilder;

import java.lang.Iterable;
import java.util.Iterator;

/**
 * always-increasing doubly-linked ordered list
 * implementation notes: if you ensure that the head and tail are always-extant
 * empty nodes and never let them leak, you can simplify a lot of the logic; by
 * ensuring that (as long as it's not the tail) a node's .next will never be
 * null, you can cut back on a lot of `if`s
 *
 * also, linked lists are terrible!  Alexis Beingessner calls array-based
 * deques and stacks "blatantly superior data structures for most workloads due
 * to less frequent allocation, lower memory overhead, true random access, and
 * cache locality" in contrast with linked lists. See:
 * http://cglab.ca/~abeinges/blah/too-many-lists/book/ I know what you're
 * thinking, we need the O(1) random removal of a linked list!  but removing a
 * random element in a linked list takes O(n) time if you account for actually
 * getting your hands on the node; N swaps are certainly no more expensive than
 * N compares, especially if an equality method is non-trivial.
 *
 * Further, Bjarne Stroustrup notes that linked list performance is often
 * closer to exponential in real-world cases due to the ways they interact with
 * CPUs and memory (badly; they have unpredictable memory allocation and access
 * patterns which wreak havoc on CPU branch predictors and caches). See:
 * https://www.youtube.com/watch?v=YQs6IC-vgmo if you can stand his weird voice
 * (doesn't he sound and look like Benjamin Franklin?)
 */
public class DoublyLinkedOrderedList<T extends Comparable<T>>
		implements Iterable<T> {
	protected class DoublyLinkedOrderedListNodeIterator
			implements Iterator<DoublyLinkedNode<T>>,
			Iterable<DoublyLinkedNode<T>> {
		protected DoublyLinkedNode<T> current =
			DoublyLinkedOrderedList.this.head;

		public boolean hasNext() {
			return current != tail.prev;
		}

		public DoublyLinkedNode<T> next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			current = current.next;
			return current;
		}

		public void remove() {
			DoublyLinkedOrderedList.this.remove(current);
		}

		public Iterator<DoublyLinkedNode<T>> iterator() {
			return this;
		}
	}

	protected class DoublyLinkedOrderedListIterator implements Iterator<T> {
		protected Iterator<DoublyLinkedNode<T>> itr =
			DoublyLinkedOrderedList.this.nodeIterator();

		public boolean hasNext() {
			return itr.hasNext();
		}

		public T next() {
			return itr.next().data;
		}

		public void remove() {
			itr.remove();
		}
	}

	protected DoublyLinkedNode<T> head;
	protected DoublyLinkedNode<T> tail;
	protected int size;

	DoublyLinkedOrderedList() {
		clear();
	}

	/**
	 * both an Iterable and an Iterator. @ me about it
	 */
	protected DoublyLinkedOrderedListNodeIterator nodeIterator() {
		return new DoublyLinkedOrderedListNodeIterator();
	}

	public Iterator<T> iterator() {
		return new DoublyLinkedOrderedListIterator();
	}

	/**
	 * @param t data to add
	 * @param n node to add after
	 */
	protected void addAfter(DoublyLinkedNode<T> n, T t) {
		Objects.requireNonNull(n);
		DoublyLinkedNode<T> insert = new DoublyLinkedNode<T>(n, t, n.next);
		n.next.prev = insert;
		n.next = insert;
		size++;
	}

	/**
	 * @param t data to add
	 * @param n node to add before
	 */
	protected void addBefore(DoublyLinkedNode<T> n, T t) {
		Objects.requireNonNull(n);
		addAfter(n.prev, t);
	}

	// DEQUE METHODS:

	/**
	 * true if size == 0
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * the list's size; O(1) time
	 */
	public int size() {
		return size;
	}

	// MUTATORS

	public void clear() {
		head = new DoublyLinkedNode<T>();
		tail = new DoublyLinkedNode<T>();
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/**
	 * removes a list node
	 */
	protected T remove(DoublyLinkedNode<T> n) {
		T data = n.data;
		n.prev.next = n.next;
		n.next.prev = n.prev;
		// i will do everything in my power to prevent you from mucking
		// around in the list even if i must let you get your dirty
		// paws on my internal nodes!!!
		n.next = n.prev = null;
		size--;
		return data;
	}

	// PUBLIC INTERFACE

	/**
	 * why does the api specify i have to allow public access to the head
	 * node??
	 */
	public DoublyLinkedNode<T> getHead() {
		// no need to check for size really here for our impl. but
		// thems the breaks if youve gotta deal with nulls
		return size == 0 ? null : head.next;
	}

	/**
	 * inserts data in the proper position such that the list is in
	 * increasing order; O(n) amortized
	 * @param data the data to insert. cannot be null
	 * @throws NullPointerException if data == null
	 */
	public void insert(T data) {
		Objects.requireNonNull(data);
		for(DoublyLinkedNode<T> n : nodeIterator()) {
			if(n.compareTo(data) > 0) {
				addBefore(n, data);
				return;
			}
		}
		addAfter(tail.prev, data);
	}

	/**
	 * deletes the first node in the list such that node.compareTo(data) ==
	 * 0; O(n) amortized
	 */
	public DoublyLinkedNode<T> delete(T data) {
		for(DoublyLinkedNode<T> n : nodeIterator()) {
			if(n.compareTo(data) == 0) {
				remove(n);
				return n;
			}
		}
		return null;
	}

	/**
	 * compatible with the java.util collections; O(n) time
	 */
	public String toString() {
		return Iterables.toString(this);
	}
}
