import java.util.Iterator;
import java.lang.Iterable;

import java.lang.StringBuilder;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
	// implementation notes:
	// if you ensure that the head and tail are always-extant empty nodes
	// and never let them leak, you can simplify a lot of the logic; by
	// ensuring that (as long as it's not the tail) a node's .next will
	// never be null, you can cut back on a lot of `if`s. however, this
	// implementation requires insertion at the *end* of the list, and
	// because it's not a doubly-linked list that makes things a bit
	// trickier, because there'd be no way to get from an empty tail node
	// to the last real node in the list. one horrible and unmaintainable
	// solution would be to add another field for the last real node in the
	// list and just keep the tail node around for book-keeping purposes.
	// ANOTHER solution is, because this linked 'list' interface requires
	// no insertion / removal at the front of the list (which really
	// defeats the purpose of using a linked list, also see [1]), is to
	// insert the elements in backwards order and then just pretend the
	// head is the tail.
	//
	// [1]: Alexis Beingessner calls array-based deques and stacks
	// "blatantly superior data structures for most workloads due to less
	// frequent allocation, lower memory overhead, true random access, and
	// cache locality" in contrast with linked lists. See:
	// http://cglab.ca/~abeinges/blah/too-many-lists/book/
	// I know what you're thinking, we need the O(1) random removal of a
	// linked list! but removing a random element in a linked list takes
	// O(n) time if you account for actually getting your hands on the
	// node; N swaps are certainly no more expensive than N compaers,
	// especially if an equality method is non-trivial.

	Random rand = new Random();

	protected SinglyLinkedNode<T> tail = null;
	protected SinglyLinkedNode<T> head = null
	protected int size = 0;

	/**
	 * due to the way we've implemented the list, this is actually a
	 * *backwards* iterator. but that's okay? because no methods of the
	 * list actually expect any ordered access
	 */
	protected class SinglyLinkedListIterator implements Iterator<T> {
		SinglyLinkedNode<T> curr = this.SinglyLinkedList.head;
		SinglyLinkedNode<T> prev = this.SinglyLinkedList.head;

		public boolean hasNext() {
			return curr.next == this.SinglyLinkedList.tail;
		}

		public T next() {
			if(hasNext()) {
				prev = curr;
				curr = curr.next;
				return curr;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			// bridge over curr
			prev.next = curr.next;
			// let the GC clean up
			curr.data = null;
			// when we set `prev` to `curr` in next() we still get
			// the old `prev` and not the now-deleted `curr`
			curr = prev;
		}
	}

	public Iterator<T> iterator() {
		return new SinglyLinkedListIterator();
	}

	/**
	 * uhh, what? isn't half the point of java encapsulation? is this meant
	 * to return a plain T?
	 */
	public SinglyLinkedNode<T> getHead() {
		return size == 0 ? null : head.next;
	}

	public void regularInsert(T data) {
		SinglyLinkedNode<T> node = new SinglyLinkedNode<T>(data);
		if(size == 0) {
			head = tail = data;
		} else {
			tail.next = data;
		}
		size++;
	}

	protected void insert(SinglyLinkedNode<T> node, T dat) {
		SinglyLinkedList<T> after = node.next;
		node.next = new SinglyLinkedNode<T>(dat, after);
		size++;
	}

	public void randomInsert(T data) {
		int pos = rand.nextInt(size + 1);
		if(pos == 0) {
			// insert before head
			SinglyLinkedNode<T> first = head;
			head = new SinglyLinkedNode<T>(data, first);
			return;
		}

		// insert after some element between the first and the last
		int inx = 0;
		for(SinglyLinkedNode<T> n : this) {
			if(pos == inx) {
				insert(n, data);
				return;
			}
			inx++;
		}
	}

	public int size() {
		return size;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder("[");
		for(T t : this) {
			ret.append(t);
		}
		// "[x, y," -> "[x, y"
		// saves us from having to keep track of if the iterator is
		// empty so we dont add an extra comma at the end
		ret.delete(ret.length() - 2, ret.length() - 1);
		// "[x, y" -> "[x, y]"
		ret.append("]");
		return ret.toString();
	}
}
