/**
 * Rebecca Turner (rebeccaturner@brandeis.edu)
 * license: GPLv3
 */

import java.lang.IllegalArgumentException;
import java.util.Collection;
import java.util.NoSuchElementException;

import java.lang.Iterable;
import java.util.Iterator;

import java.util.Arrays;

/**
 * array-based stack with elements from 0..size - 1; O(1)
 */
public class Deque<T> extends AbstractCollection<T> {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;

    protected class QueueIterator implements Iterator<T> {
        int inx = 0;

        public boolean hasNext() {
            return inx < Deque.this.size;
        }

        public T next() {
            if(hasNext()) {
                T ret = Deque.this.get(inx);
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
    private T[] arr;
    // the difference between front and back is *kinda* the size but we
    // have to do math (ie add the array capacity and subtract them) to get
    // the size from that data so we store it separately
    private int size = 0;
    // index of first el
    private int front = 0;
    // index of last el
    // we add 1 to back adding our first element so this ensures that the
    // first element is enqueued into position 0
    private int back = 0;

    Deque() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    Deque(int capacity) {
        if(capacity < 1) {
            // cannot have a 0-capacity queue
            throw new IllegalArgumentException();
        }
        arr = constructArray(capacity);
        size = 0;
    }

    Deque(Collection<? extends T> col) {
        this(col.size());
        addAll(col);
    }

    /**
     * java generics are basically a hacked in feature so we have to write
     * weird and unintuitive code like this
     *
     * due to how strange this code is and the fact that it requires a
     * compiler annotation i've decided to factor out / abstract something
     * as simple as array instantiation. incredible
     */
    private T[] constructArray(int capacity) {
        @SuppressWarnings("unchecked")
        final T[] arr = (T[]) new Object[capacity];
        return arr;
    }

    /**
     * O(1) time
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * O(1) time; indicates next insertion will expand the internal array
     */
    public boolean isFull() {
        return size == arr.length;
    }

    /**
     * grow internal array by a factor of 2; O(n) time
     */
    private void expand() {
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
     * ensures the internal array can add another element; O(1) time amortized
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

    private void throwIfOOB(int inx) {
        Parameters.checkState(size > 0);
        Parameters.checkElementIndex(inx, size);
    }

    /**
     * turn an index relative to the front of the queue into an index
     * usable with the wrapping internal array
     * @param inx an element index > -size
     */
    private int index(int inx) {
        return (front + inx + arr.length) % arr.length;
    }

    private void incFront() {
        // index() is relative to the front of the queue
        front = index(1);
    }

    private void incBack() {
        // always at the end of the queue, and size = last index + 1 or
        // 0
        back = index(size);
    }

    private void decBack() {
        back = index(size - 1);
    }

    private void decFront() {
        front = index(-1);
    }

    /**
     * O(1) time
     */
    public void set(T t, int inx) {
        throwIfOOB(inx);
        arr[index(inx)] = t;
    }

    /**
     * O(1) time
     */
    public T get(int inx) {
        throwIfOOB(inx);
        return arr[index(inx)];
    }

    /**
     * O(1) time
     */
    public T getFirst() {
        return get(0);
    }

    /**
     * O(1) time
     */
    public T getLast() {
        return get(size - 1);
    }

    public void addFirst(T data) {
        ensureAddable();
        decFront();
        size++;
        arr[front] = data;
    }

    /**
     * O(1) amortized time
     */
    public void addLast(T data) {
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

    /**
     * O(1) amortized time
     */
    public T removeFirst() {
        Parameters.checkState(!isEmpty(), "cannot dequeue from empty queue!");

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

    public T removeLast() {
        Parameters.checkState(!isEmpty(), "cannot dequeue from empty queue!");
        T ret = arr[back];
        arr[back] = null;
        size--;
        decBack();
        return ret;
    }

    /**
     * thorough report on internal queue state; debugging only
     */
    public String debug() {
        return "size  = " + size + "\n"
                + "front = " + front + "\n"
                + "back  = " + back + "\n"
                + "arr   = " + Arrays.toString(arr) + ".\n";
    }
}