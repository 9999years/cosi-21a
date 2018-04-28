import javax.swing.text.DefaultTextUI;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * a heap-backed minimum-priority queue of GraphNodes. implementation of a
 * generic queue or a max-queue is left as an exercise to the reader
 */
public class MinPriorityQueue implements MinPriorityQueueInterface<GraphNode> {
	/**
	 * cache line sizes vary from around 64 bytes (8 64-bit java objects
	 * or 16 32-bit java objects) to 256 bytes (32 64-bit or 64 32-bit
	 * objects), so we pick a number in the middle as a compromise
	 */
	public static int DEFAULT_QUEUE_SIZE = 16;
	private int size = 0;
	private GraphNode[] dat;

	MinPriorityQueue() {
		this(DEFAULT_QUEUE_SIZE);
	}

	MinPriorityQueue(int capacity) {
		dat = new GraphNode[capacity];
	}

	private void ensureCapacity(int newCapacity) {
		if (dat.length < newCapacity) {
			dat = Arrays.copyOf(dat, newCapacity);
		}
	}

	@Override
	public void insert(GraphNode g) {
		Objects.requireNonNull(g);
		size++;
		ensureCapacity(size);
		dat[size] = g;
		heapifyUp(size);
	}

	/**
	 * either this unwieldy method name is copied directly from wikipedia
	 * or whoever wrote this assignment also wrote the wikipedia article
	 * on priority queues and im not sure which one is funnier
	 */
	@Override
	public GraphNode pullHighestPriorityElement() {
		if (isEmpty()) {
			throw new IllegalStateException("Heap underflow");
		}
		GraphNode max = dat[1];
		dat[1] = dat[size];
		size--;
		heapifyDown(1);
		return max;
	}

	@Override
	public void rebalance() {
		heapify(dat[1]);
	}

	/**
	 * O(n) because we don't know where g is in the heap
	 */
	@Override
	public void heapify(GraphNode g) {
		Objects.requireNonNull(g);
		int inx = 0;
		for (int i = 0; i <= size; i++) {
			if (g.equals(dat[i])) {
				inx = i;
				break;
			}
		}

		if (inx == 0) {
			throw new IllegalArgumentException("node not in heap");
		}

		heapifyDown(inx);
		heapifyUp(inx);
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private int search(GraphNode g) {
		return 0;
	}

	private void swap(int a, int b) {
		GraphNode tmp = dat[a];
		dat[a] = dat[b];
		dat[b] = tmp;
	}

	private void heapifyUp(int i) {
		while (i > 0 && dat[i].priority < dat[parent(i)].priority) {
			int p = parent(i);
			swap(i, p);
			i = p;
		}
	}

	private void heapifyDown(int i) {
		int l = left(i);
		int r = right(i);
		int largest = i;
		if (l <= size && dat[l].priority < dat[i].priority) {
			largest = l;
		}
		if (r <= size && dat[r].priority < dat[largest].priority) {
			largest = r;
		}
		if (largest != i) {
			swap(i, largest);
			heapifyDown(largest);
		}
	}

	private int parent(int i) {
		return i / 2;
	}

	private int left(int i) {
		return 2 * i;
	}

	private int right(int i) {
		return 2 * i + 1;
	}
}
