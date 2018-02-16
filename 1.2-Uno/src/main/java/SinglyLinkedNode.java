public class SinglyLinkedNode<T> {
	public final T data;
	protected SinglyLinkedNode<T> next;

	SinglyLinkedNode(T data) {
		this.data = data;
	}

	SinglyLinkedNode(T data, SinglyLinkedNode<T> next) {
		this(data);
		this.next = next;
	}

	/**
	 * irrelevant to encapsulate this with a getter method b/c
	 * write-protection is enforced with a final field (plus it wastes a
	 * stack frame!)
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

	public String toString() {
		return data.toString();
	}
}
