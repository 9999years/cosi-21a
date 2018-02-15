import java.lang.ArrayIndexOutOfBoundsException;

/**
 * array-based deque
 */
public class Queue<T> {
	public static final int DEFAULT_INITIAL_CAPACITY = 10;

	protected T[] arr;
	protected int size = 0;
	protected int front = 0;
	protected int back = 0;

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
	 *
	 * due to how strange this code is and the fact that it requires a
	 * compiler annotation i've decided to factor out / abstract something
	 * as simple as array instantiation. incredible
	 */
	protected T[] constructArray(int capacity) {
		@SuppressWarnings("unchecked")
		final T[] arr = (T[]) new Object[capacity];
		return arr;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean isFull() {
		return size == arr.length;
	}

	/**
	 * grow internal array by a factor of 2
	 */
	protected void expand() {
		T[] oldArr = arr;
		// instantiate new array; grow by factor of 2 by default
		// im pretty sure that's standard across implementations
		arr = constructArray(arr.length * 2);
		// copy old array to new, larger array
		for(int i = 0; i < size; i++) {
			arr[i] = oldArr[index(i)];
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

	protected void index(int inx) {
		return (front + inx) % arr.length;
	}

	protected void incFront() {
		front = index(front + 1);
	}

	protected void incBack() {
		back = index(back + 1);
	}

	protected void throwIfOOB(int inx) {
		if(inx < 0 || inx > size || size == 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void set(T t, int inx) {
		throwIfOOB(inx);
		arr[index(inx)] = t;
	}

	public T get(int inx) {
		throwIfOOB(inx);
		return arr[index(inx)];
	}

	public T peek() {
		throwIfOOB();
		return get(0);
	}

	public void enqueue(T data) {
		ensureAddable();
		incBack();
		size++;
		set(data, back);
	}

	public T dequeue(T data) {
		size--;
		T ret = get(front);
		// let the gc clean up
		set(null, front);
		incFront();
		return ret;
	}

	public String toString() {
		if(size == 0) {
			return "[]";
		}

		StringBuilder ret = new StringBuilder("[");
		for(int i = 0; i < size; i++) {
			ret.append(get(i));
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
