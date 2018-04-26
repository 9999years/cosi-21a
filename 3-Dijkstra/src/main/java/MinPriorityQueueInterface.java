public interface MinPriorityQueueInterface<T> {
	void insert(T t);

	/**
	 * pop
	 * @return the removed element
	 */
	T pullHighestPriorityElement();

	/**
	 * "calls heapify"
	 */
	protected void rebalance();

	/**
	 * when t's priority changes, restore the heap property about t
	 * @param t the node with changed priority in the queue
	 */
	protected void heapify(T t);
}
