import java.util.Iterator;
import java.lang.Iterable;

import java.lang.StringBuilder;

import java.util.Random;
import java.util.Objects;

import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;

public class SinglyLinkedList<T> implements Iterable<T> {
	/**
	 * oh yeah this should totally live inside a list implementation, lmao
	 */
	Random rand = new Random();

	/**
	 * the usual, singly linked lists sucked because you cant do the cool
	 * trick for doubly linked lists to simplify your logic
	 */
	protected SinglyLinkedNode<T> tail = null;
	protected SinglyLinkedNode<T> head = null;
	protected int size = 0;

	/**
	 * iterator over nodes, useful internally (and to implement the regular
	 * iterator)
	 */
	protected class SinglyLinkedListNodeIterator
			implements Iterator<SinglyLinkedNode<T>> {
		protected SinglyLinkedNode<T> curr = SinglyLinkedList.this.head;

		public boolean hasNext() {
			return curr != null;
		}

		public SinglyLinkedNode<T> next() {
			if(hasNext()) {
				SinglyLinkedNode<T> ret = curr;
				curr = curr.next;
				return ret;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	/**
	 * regular iterator because im a fan of those for(T t : this) loops
	 * (even though the semantics are extremely limited)
	 */
	protected class SinglyLinkedListIterator implements Iterator<T> {
		Iterator<SinglyLinkedNode<T>> itr
			= new SinglyLinkedListNodeIterator();

		public boolean hasNext() {
			return itr.hasNext();
		}

		public T next() {
			return itr.next().data;
		}
	}

	/**
	 * i do not want to type this out and also it fits in with the naming
	 * convention of iterator()
	 */
	protected Iterator<SinglyLinkedNode<T>> nodeIterator() {
		return new SinglyLinkedListNodeIterator();
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

	public T popHead() {
		if(size == 0) {
			return null;
		} else {
			T ret = head.data;
			head = head.next;
			return ret;
		}
	}

	/**
	 * lots of insertion logic here, which sucks; it's kinda bug prone. and
	 * multiple pieces of code are doing the same thing
	 *
	 * theres lots of cases for a 0 size list because we cant do, again,
	 * the cool doubly linked list trick
	 *
	 * anyways this inserts at the end
	 */
	public void regularInsert(T data) {
		if(size == 0) {
			insertFront(data);
		} else {
			insertAfter(tail, data);
		}
	}

	/**
	 * insert at the front of the list
	 */
	protected void insertFront(T data) {
		if(size == 0) {
			head = tail = new SinglyLinkedNode<>(data);
		} else {
			SinglyLinkedNode<T> first = head;
			head = new SinglyLinkedNode<T>(data, first);
		}
		size++;
	}

	/**
	 * insert a new node with content data after the given node
	 */
	protected void insertAfter(SinglyLinkedNode<T> node, T data) {
		SinglyLinkedNode<T> after = node.next;
		node.next = new SinglyLinkedNode<T>(data, after);
		if(node == tail) {
			tail = node.next;
		}
		size++;
	}

	/**
	 * insert at a given index such that `index` is the new index of
	 * `data`; used to implement randomInsert
	 */
	public void insert(T data, final int index) {
		if(index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException();
		}

		if(index == 0) {
			// insert before head
			insertFront(data);
			return;
		} else if(index == size) {
			insertAfter(tail, data);
			return;
		}

		// insert after some element between the first and the last
		Iterator<SinglyLinkedNode<T>> itr = nodeIterator();

		for(int i = 0; itr.hasNext(); i++) {
			SinglyLinkedNode<T> n = itr.next();
			if(index == i) {
				insertAfter(n, data);
				return;
			}
		}
	}

	/**
	 * insert data randomly in the list, anywhere between before the head
	 * and after the tail
	 */
	public void randomInsert(T data) {
		if(size == 0) {
			insertFront(data);
		} else {
			// Random complains about an upper bound of 0
			insert(data, rand.nextInt(size));
		}
	}

	/**
	 * removes the node after `prev`
	 */
	public void remove(SinglyLinkedNode<T> prev) {
		SinglyLinkedNode<T> removing = prev.next;
		// bridge over removing; removing.next might be null
		// but that doesn't matter; if removing is null we're
		// removing the tail
		prev.next = removing == null ? null : removing.next;
		size--;
	}

	/**
	 * remove the first occurance of `data` in the list
	 */
	public void remove(T data) {
		Iterator<SinglyLinkedNode<T>> itr = nodeIterator();
		SinglyLinkedNode<T> prev = head;
		SinglyLinkedNode<T> curr = head;
		while(itr.hasNext()) {
			curr = itr.next();
			if((curr.data == null && data == null)
				|| curr.data.equals(data)) {
				remove(prev);
				return;
			}
			prev = curr;
		}
	}

	/**
	 * the number of nodes currently in the list
	 */
	public int size() {
		return size;
	}

	public boolean empty() {
		return size == 0;
	}

	public String toString() {
		if(size == 0) {
			return "[]";
		}

		StringBuilder ret = new StringBuilder("[");
		for(T t : this) {
			ret.append(t);
			ret.append(", ");
		}
		// "[x, y," -> "[x, y"
		// saves us from having to keep track of if the iterator is
		// empty so we dont add an extra comma at the end
		ret.delete(ret.length() - 2, ret.length());
		// "[x, y" -> "[x, y]"
		ret.append("]");
		return ret.toString();
	}
}
