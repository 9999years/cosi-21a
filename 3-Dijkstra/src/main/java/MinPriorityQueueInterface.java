public interface MinPriorityQueueInterface<T> {
	/**
	 * inserts an element into the queue
	 * @param t the element to insert
	 */
	void insert(T t);

	/**
	 * pop
	 * @return the removed element
	 */
	T pullHighestPriorityElement();

	/**
	 * "calls heapify"
	 */
	void rebalance();

	/**
	 * when t's priority changes, restore the heap property about t
	 * @param t the node with changed priority in the queue
	 */
	void heapify(T t);
}
