import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.ClassCastException;
import java.lang.Iterable;

import java.util.Collection;
import java.util.Arrays;
import java.util.Objects;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> extends AbstractCollection<T> {
	public static final int DEFAULT_INITIAL_CAPACITY = 10;

	protected class ArrayListIterator
		implements Iterator<T>, Iterable<T> {
		int inx = -1;
		ArrayList<T> arr = ArrayList.this;

		public Iterator<T> iterator() {
			return this;
		}

		public boolean hasNext() {
			return inx + 1 < arr.size();
		}

		public T next() {
			if(hasNext()) {
				inx++;
				return arr.get(inx);
			}
			throw new NoSuchElementException();
		}

		public void remove() {
			arr.remove(inx);
		}
	}

	protected T[] arr;
	protected int size;

	ArrayList() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	ArrayList(int capacity) {
		arr = constructArray(capacity);
		size = 0;
	}

	ArrayList(Collection<? extends T> c) {
		this(c.size());
		addAll(c);
	}

	public Iterator<T> iterator() {
		return new ArrayListIterator();
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
		// copy old array to new, larger array
		for(int i = 0; i < size; i++) {
			arr[i] = oldArr[i];
		}
	}

	void trimToSize() {
		T[] oldArr = arr;
		arr = constructArray(size);
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

	public boolean add(T t) {
		ensureAddable();
		arr[size] = t;
		size++;
		return true;
	}

	/**
	 * removes the last element
	 */
	public T remove() {
		return remove(size - 1);
	}

	public T remove(int inx) {
		T ret = arr[inx];
		size--;
		// shift over the rest of the array
		for(int i = inx; i < size; i++) {
			// this is OK because we've just decremented the array
			// size
			arr[i] = arr[i + 1];
		}
		return ret;
	}

	public T get(int inx) {
		// >= because sizes are in terms of cardinality and indexes are
		// in terms of... a 0-based ordering system
		if(inx >= size || inx < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return arr[inx];
	}

	/**
	 * uh oh
	 */
	@Override
	public <E> E[] toArray(E[] a) {
		Objects.requireNonNull(a);

		if(a.length < size) {
			a = Arrays.copyOf(a, size);
		}

		for(int i = 0; i < size(); i++) {
			try {
				a[i] = (E) get(i);
			} catch(ClassCastException ex) {
				throw new ArrayStoreException();
			}
		}

		return a;
	}
}
