import java.lang.Comparable;

public class DoublyLinkedNode<T extends Comparable<T>> {
	public final T data;
	protected DoublyLinkedNode<T> prev = null;
	protected DoublyLinkedNode<T> next = null;

	DoublyLinkedNode() {
		this(null);
	}

	DoublyLinkedNode(T data) {
		this.data = data;
	}

	DoublyLinkedNode(DoublyLinkedNode<T> prev, T data,
			DoublyLinkedNode<T> next) {
		this(data);
		this.prev = prev;
		this.next = next;
	}

	public int compareTo(T other) {
		return data.compareTo(other);
	}

	/**
	 * irrelevant b/c write-protection is enforced with a final field
	 */
	public T getData() {
		return data;
	}

	/**
	 * since this is public and DoublyLinkedList allows public access to
	 * all internal `DoublyLinkedNode`s, we're completely unable to
	 * maintain any invariants. amazing.
	 */
	public void setPrev(DoublyLinkedNode<T> prevNode) {
		prev = prevNode;
	}

	public void setNext(DoublyLinkedNode<T> nextNode) {
		next = nextNode;
	}

	/**
	 * @url https://kirit.com/On%20following%20rules/Encapsulation%20is%20a%20Good%20Thing%E2%84%A2
	 */
	public DoublyLinkedNode<T> getNext() {
		return next;
	}

	public DoublyLinkedNode<T> getPrev() {
		return prev;
	}

	public String toString() {
		return data.toString();
	}
}
