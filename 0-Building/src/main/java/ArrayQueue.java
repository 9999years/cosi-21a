/**
 * I know what you're thinking: "it's ridiculous to implement a queue with an
 * array!" well, yes, but i did it because the assignment said "the only
 * data structure that you will be using is arrays" and im not sure if that
 * meant to not use any of the default java collections or to not construct any
 * linked lists but im not going to find out
 *
 * also, if your data is small, eg an int, you're wasting like 2x space by
 * using a linked list!! time / space tradeoffs to watch out for
 *
 * note that this implementation never shinks the internal array
 */
public class ArrayQueue<T> {
	public static final int DEFAULT_INITIAL_CAPACITY = 10;

	protected T[] arr;
	protected int size;

	ArrayQueue() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	ArrayQueue(int capacity) {
		arr = constructArray(capacity);
		size = 0;
	}

	/**
	 * java generics are basically a hacked in feature so we have to write
	 * weird and unintuitive code like this
	 */
	protected T[] constructArray(int capacity) {
		@SuppressWarnings("unchecked")
		final T[] arr = (T[]) new Object[capacity];
		return arr;
	}

	public int size() {
		return size;
	}

	/**
	 * grow internal array by a factor of 2
	 */
	protected void expand() {
		T[] oldArr = arr;
		// instantiate new array; grow by factor of 2 by
		// default
		// im pretty sure that's standard across implementations
		arr = constructArray(arr.length * 2);
		for(int i = 0; i < size; i++) {
			arr[i] = oldArr[i];
		}
	}

	/**
	 * ensures the internal array can add another element
	 */
	protected void ensureAddable() {
		// length and capacity are in terms of elements and not indicies
		// BUT they're consistent with each other so we don't have to
		// do any OBO arithmetic to make them compatible
		// ...
		// but we also add one because we want to ensure we have space for one more element
		if(arr.length <= size + 1) {
			expand();
		}
	}

	public void add(T t) {
		ensureAddable();
		arr[size] = t;
		size++;
	}

	public T remove() {
		T ret = arr[0];
		size--;
		for(int i = 0; i < size; i++) {
			// this is OK because we've just decremented the array
			// size
			arr[i] = arr[i + 1];
		}
		return ret;
	}
}