import java.util.Iterator;
import java.lang.Iterable;

import java.lang.StringBuilder;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
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
			return curr != null &&
				curr.next != this.SinglyLinkedList.tail;
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
		return head;
	}

	/**
	 * lots of insertion logic here, which sucks; it's kinda bug prone.
	 * it's simpler if we have a doubly-linked list and then we can have
	 * simple `insertBefore` and `insertAfter` methods
	 */
	public void regularInsert(T data) {
		SinglyLinkedNode<T> node = new SinglyLinkedNode<T>(data);
		if(size == 0) {
			head = tail = node;
		} else {
			tail.next = node;
		}
		size++;
	}

	protected void insertFront(T dat) {
		SinglyLinkedList<T> first = head.next;
		head = new SinglyLinkedNode<T>(dat, first);
		size++;
	}

	protected void insertAfter(SinglyLinkedNode<T> node, T dat) {
		SinglyLinkedList<T> after = node.next;
		node.next = new SinglyLinkedNode<T>(dat, after);
		size++;
	}

	public void insert(T data, int index) {
		if(index == 0) {
			// insert before head
			insertFront(data);
		}

		// insert after some element between the first and the last
		int i = 0;
		for(SinglyLinkedNode<T> n : this) {
			if(index == i) {
				insertAfter(n, data);
				return;
			}
			i++;
		}
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
