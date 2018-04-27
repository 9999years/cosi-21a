import java.util.Comparator;

/**
 * to create a max-queue call the constructor with
 * Comparator.naturalOrder().reversed()
 * @param <T> the type of elements in the queue
 */
public class MinPriorityQueue<T extends Comparable<T>> implements MinPriorityQueueInterface<T> {
	private Comparator<T> comparator = Comparator.naturalOrder();

	PriorityQueue(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public void insert(T t) {

	}

	@Override
	public T pullHighestPriorityElement() {
		return null;
	}

	@Override
	protected void rebalance() {

	}

	@Override
	protected void heapify(T t) {

	}
}
