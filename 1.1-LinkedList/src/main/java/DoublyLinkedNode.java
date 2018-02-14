public class DoublyLinkedNode<T> {
	public final T data;
	protected DoublyLinkedNode<T> prev;
	protected DoublyLinkedNode<T> next;

	DoublyLinkedNode() { }

	DoublyLinkedNode(T data) {
		this.data = data;
	}

	DoublyLinkedNode(DoublyLinkedNode<T> prev, T data,
			DoublyLinkedNode<T> next) {
		this(data);
		this.prev = prev;
		this.next = next;
	}

	/**
	 * irrelevant b/c write-protection is enforced with a final field
	 */
	public T getData() {
		return data;
	}

	/**
	 * since this is public and SinglyLinkedList allows public access to
	 * all internal `SinglyLinkedNode`s, we're completely unable to
	 * maintain any invariants. amazing.
	 */
	public void setNext(SinglyLinkedNode<T> nextNode) {
		next = nextNode;
	}

	/**
	 * @url https://kirit.com/On%20following%20rules/Encapsulation%20is%20a%20Good%20Thing%E2%84%A2
	 */
	public SinglyLinkedNode<T> getNext() {
		return next;
	}

	public SinglyLinkedNode<T> getPrev() {
		return prev;
	}

	public String toString() {
		return data.toString();
	}
}
