import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.IllegalStateException;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

import java.lang.Iterable;
import java.util.Iterator;

import java.util.Arrays;

/**
 * array-based deque with elements from 0..size - 1; O(1) insertion at end of
 * queue and removal at front of queue
 */
public class Queue<T> implements Iterable<T> {
	public static final int DEFAULT_INITIAL_CAPACITY = 16;

	protected class QueueIterator implements Iterator<T> {
		int inx = 0;

		public boolean hasNext() {
			return inx < Queue.this.size;
		}

		public T next() {
			if(hasNext()) {
				T ret = Queue.this.get(inx);
				inx++;
				return ret;
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	public Iterator<T> iterator() {
		return new QueueIterator();
	}

	/**
	 * example layout:
	 * ∅ := null
	 * size = 12
	 * capacity = 18
	 * arr index:  0  1 2 3 4 5 6 7  8  9 10 11 12 13 14 15 16 17
	 * arr:       10 11 ∅ ∅ ∅ ∅ ∅ ∅  0  1  2  3  4  5  6  7  8  9
	 *                ^back = 1      ^front = 8
	 * elements are enqueue'd at the back of the queue, increasing back by
	 * 1, and dequeue'd from the front of the queue, increasing front by 1
	 *
	 * arr: ∅ ∅ ∅ ∅
	 *     bf
	 * arr: 0 ∅ ∅ ∅
	 *      b=f
	 * arr: 0 1 ∅ ∅
	 *      f b
	 * arr: 0 1 2 ∅
	 *      f   b
	 * arr: ∅ 1 2 ∅
	 *        f b
	 */
	protected T[] arr;
	// the difference between front and back is *kinda* the size but we
	// have to do math (ie add the array capacity and subtract them) to get
	// the size from that data so we store it separately
	protected int size = 0;
	protected int front = 0;
	// we add 1 to back adding our first element so this ensures that the
	// first element is enqueued into position 0
	protected int back = 0;

	Queue() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	Queue(int capacity) {
		if(capacity < 1) {
			// cannot have a 0-capacity queue
			throw new IllegalArgumentException();
		}
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

	protected void throwIfOOB(int inx) {
		if(inx < 0 || inx > size || size == 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * turn an index relative to the front of the queue into an index
	 * usable with the wrapping internal array
	 */
	protected int index(int inx) {
		return (front + inx) % arr.length;
	}

	protected void incFront() {
		// index() is relative to the front of the queue
		if(size > 0) {
			front = index(1);
		}
	}

	protected void incBack() {
		// always at the end of the queue, and size = last index + 1 or
		// 0
		back = index(size);
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
		return get(0);
	}

	public T peekBack() {
		return get(size - 1);
	}

	public void enqueue(T data) {
		// ensure we have space for another element, expanding the
		// array if necessary
		ensureAddable();
		// increment the queue back
		incBack();
		// increment our size
		size++;
		// set the new back position to our data
		arr[back] = data;
	}

	public T dequeue() {
		if(isEmpty()) {
			// cannot dequeue from empty queue!
			throw new IllegalStateException();
		}

		// get the element currently at the front
		T ret = arr[front];
		// decrease the size
		// let the gc clean up by setting the old front in the array to
		// null, leaving ret unimpacted
		arr[front] = null;
		// decrease size, increment front index
		size--;

		incFront();
		return ret;
	}

	public String toString() {
		return Iterables.toString(this);
	}

	/**
	 * thorough report on internal queue state; debugging only
	 */
	//public void debug() {
		//System.out.println("size  = " + size);
		//System.out.println("front = " + front);
		//System.out.println("back  = " + back);
		//System.out.println("arr   = " + Arrays.toString(arr) + ".\n");
	//}
}
