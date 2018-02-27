import java.lang.Exception;
import java.lang.Iterable;
import java.lang.SuppressWarnings;
import java.lang.StringBuilder;

//exceptions
import java.lang.UnsupportedOperationException;
import java.lang.ArrayStoreException;
import java.lang.ClassCastException;
import java.lang.IndexOutOfBoundsException;
import java.util.NoSuchElementException;

// functional
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;

import java.util.Objects;
import java.util.Iterator;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Deque;

/**
 * Linked list class; see java.util.Deque for details
 *
 * note: all add methods never fail (throw exceptions or return false)
 *
 * note: retainAll, removeAll, and containsAll just throw a
 * java.lang.UnsupportedOperationException
 */
public class LinkedList<E> extends MinimalLinkedList<E>
		implements Iterable<E>, Deque<E> {
	protected class LinkedListIterator implements Iterable<E>,
		ListIterator<E>, Spliterator<E> {
		/**
		 * our "cursor" is between current and current.next
		 *
		 * before the list: current == head
		 * after list end: current == tail.previous
		 */
		protected Node<E> current;
		/**
		 * last node returned
		 */
		protected Node<E> last = null;
		/**
		 * inx of current
		 */
		protected int inx;

		/**
		 * startingElement is where the iterator starts, advancer is
		 * a function that takes a node and returns the next node
		 */
		LinkedListIterator(Node<E> startingElement, int startingIndex) {
			current = startingElement;
			inx = startingIndex;
		}

		public Iterator<E> iterator() {
			return this;
		}

		public boolean hasNext() {
			return current != tail.previous;
		}

		public boolean hasPrevious() {
			// we must be able to get the ListIterator to the
			// position "before" the first element, which is, in
			// our case, where curent == head
			return current != head;
		}

		/**
		 * cursor is in front of current, so we advance the cursor
		 * to the next value and then return the current value
		 */
		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			current = current.next;
			last = current;
			inx++;
			return last.value;
		}

		/**
		 * cursor is in front of current, so we take our value at
		 * current (to return) and then rewind the cursor
		 */
		public E previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			last = current;
			current = current.previous;
			inx--;
			return last.value;
		}

		public int nextIndex() {
			return inx + 1;
		}

		public int previousIndex() {
			return inx;
		}

		public void add(E e) {
			Node<E> n = new Node<>(e);
			LinkedList.this.addBefore(current, e);
		}

		public void remove() {
			if(last == null) {
				throw new IllegalStateException();
			}
			LinkedList.this.remove(last);
			last = null;
		}

		public void set(E e) {
			last.value = e;
		}

		public void forEachRemaining(Consumer<? super E> action) {
			while(hasNext()) {
				action.accept(next());
			}
		}

		// spliterator methods

		public int characteristics() {
			return Spliterator.SIZED;
		}

		public long estimateSize() {
			return size;
		}

		public boolean tryAdvance(Consumer<? super E> action) {
			if(hasNext()) {
				action.accept(next());
				return true;
			} else {
				return false;
			}
		}

		/**
		 * this doesn't do anything, ever. sorry threads!
		 */
		public Spliterator<E> trySplit() {
			// lol
			return null;
		}
	}

	protected class DescendingLinkedListIterator implements Iterable<E>,
			ListIterator<E>, Spliterator<E> {
		LinkedListIterator itr;

		DescendingLinkedListIterator(
			Node<E> startingElement, int startingIndex) {
			itr = new LinkedListIterator(startingElement, startingIndex);
		}

		public boolean hasNext() {
			return itr.hasPrevious();
		}

		public boolean hasPrevious() {
			return itr.hasNext();
		}

		public E next() {
			return itr.previous();
		}

		public E previous() {
			return itr.next();
		}

		public int nextIndex() {
			return itr.previousIndex();
		}

		public int previousIndex() {
			return itr.nextIndex();
		}

		public Iterator<E> iterator() {
			return this;
		}

		public void add(E e) {
			itr.add(e);
		}

		public void remove() {
			itr.remove();
		}

		public void set(E e) {
			itr.set(e);
		}

		public long estimateSize() {
			return size;
		}

		public void forEachRemaining(Consumer<? super E> action) {
			itr.forEachRemaining(action);
		}

		public boolean tryAdvance(Consumer<? super E> action) {
			return itr.tryAdvance(action);
		}

		public Spliterator<E> trySplit() {
			return null;
		}

		public int characteristics() {
			return Spliterator.SIZED;
		}
	}

	LinkedList() {
		clear();
	}

	LinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}

	/**
	 * optional operation; if o is in this list, applies operation on it
	 *
	 * kind of a combination .contains / .get
	 *
	 * @return true if operation was performed, false otherwise
	 */
	public boolean operateOnFirst(Object o, Consumer<E> operation) {
		for(E e : this) {
			if(Objects.equals(e, o)) {
				operation.accept(e);
				return true;
			}
		}
		return false;
	}

	protected <T> T nullIfEmpty(Supplier<T> t) {
		return isEmpty() ? null : t.get();
	}

	// DEQUE METHODS:

	public Iterator<E> iterator() {
		return listIterator();
	}

	public ListIterator<E> descendingIterator() {
		return new DescendingLinkedListIterator(tail.previous, size - 1);
	}

	public ListIterator<E> listIterator() {
		return new LinkedListIterator(head, -1);
	}

	// MUTATORS

	protected boolean remove(Object o, Iterator<E> itr) {
		E e;
		while(itr.hasNext()) {
			e = itr.next();
			if(Objects.equals(e, o)) {
				itr.remove();
				return true;
			}
		}
		return false;
	}

	public boolean removeFirstOccurrence(Object o) {
		return remove(o);
	}

	public boolean removeLastOccurrence(Object o) {
		return remove(o, descendingIterator());
	}

	// HEAD OPERATIONS

	// insertion:

	public boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}

	public void push(E e) {
		addFirst(e);
	}

	// removing:

	public E pop() {
		throwIfEmpty();
		return removeFirst();
	}

	public E remove() {
		throwIfEmpty();
		return removeFirst();
	}

	public E pollFirst() {
		return nullIfEmpty(() -> remove(head.next));
	}

	public E poll() {
		return pollFirst();
	}

	// examining:

	public E element() {
		return getFirst();
	}

	public E peekFirst() {
		return nullIfEmpty(() -> head.next.value);
	}

	public E peek() {
		return peekFirst();
	}

	// TAIL OPERATIONS

	// insertion

	@Override
	public boolean add(E e) {
		addLast(e);
		return true;
	}

	public boolean offerLast(E e) {
		addLast(e);
		return true;
	}

	public boolean offer(E e) {
		return offerLast(e);
	}

	// removal

	public E pollLast() {
		return nullIfEmpty(this::removeLast);
	}

	// examining

	public E peekLast() {
		return nullIfEmpty(() -> tail.previous.value);
	}
}
