import java.util.Iterator;
import java.lang.Iterable;

import java.lang.StringBuilder;

import java.util.NoSuchElementException;
import java.lang.IllegalStateException;

public class SinglyLinkedList<T> {
	protected SinglyLinkedNode<T> tail = null;
	protected SinglyLinkedNode<T> head = null;
	protected int size = 0;

	protected class SinglyLinkedListIterator implements Iterator<T> {
		SinglyLinkedNode<T> curr = this.SinglyLinkedList.head;
		// keep track of the previous node so we can remove nodes
		SinglyLinkedNode<T> prev = this.SinglyLinkedList.head;

		public boolean hasNext() {
			// empty list OR current el is last
			return curr == null || curr.next == null;
		}

		public T next() {
			if(hasNext()) {
				prev = curr;
				curr = curr.next;
				return curr.data;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			if(curr == null) {
				throw new IllegalStateException();
			}
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
	 * uhh, what? isn't half the point of java encapsulation? was the
	 * assignment meant to specify this as returning a plain T?
	 */
	public SinglyLinkedNode<T> getHead() {
		return head;
	}

	public void regularInsert(T data) {
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
