import java.util.Comparator;

/**
 * a heap-backed minimum-priority queue
 *
 * to create a max-queue call the constructor with
 * Comparator.naturalOrder().reversed()
 * @param <E> the type of elements in the queue
 * @param <P> the type of the priorities; can be as simple as an Integer
 */
public class MinPriorityQueue<X extends PriorityQueueEntry<E, P>> implements MinPriorityQueueInterface<P> {
	private Comparator<P> comparator = Comparator.naturalOrder();

	/**
	 * cache line sizes vary from around 64 bytes (8 64-bit java objects
	 * or 16 32-bit java objects) to 256 bytes (32 64-bit or 64 32-bit
	 * objects), so we pick a number in the middle as a compromise
	 */
	public static int DEFAULT_QUEUE_SIZE = 16;

	MinPriorityQueue() {
	}

	MinPriorityQueue(Comparator<P> comparator) {
		this();
		this.comparator = comparator;
	}

	@Override
	public void insert(P p) {

	}

	/**
	 * either this unwieldy method name is copied directly from wikipedia
	 * or whoever wrote this assignment also wrote the wikipedia article
	 * on priority queues and im not sure which one is funnier
	 */
	@Override
	public P pullHighestPriorityElement() {
		return null;
	}

	@Override
	public void rebalance() {

	}

	@Override
	public void heapify(P p) {

	}
}
