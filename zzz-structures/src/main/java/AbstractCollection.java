import java.lang.UnsupportedOperationException;
import java.lang.ClassCastException;
import java.lang.Iterable;

import java.util.Collection;
import java.util.Objects;
import java.util.Arrays;
import java.util.Iterator;

/**
 * clean-room reimplementation of java.util.AbstractCollection
 */
public abstract class AbstractCollection<E> implements Collection<E> {
	/**
	 * Returns an iterator over the elements contained in this collection.
	 */
	public abstract Iterator<E> iterator();

	/**
	 * Returns the number of elements in this collection.
	 */
	public abstract int size();

	/**
	 * Ensures that this collection contains the specified element
	 * (optional operation).
	 */
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds all of the elements in the specified collection to this
	 * collection (optional operation).
	 */
	public boolean addAll(Collection<? extends E> c) {
		boolean modified = false;
		for(E e : c) {
			if(add(e)) {
				modified = true;
			}
		}
		return modified;
	}

	/**
	 * Removes all of the elements from this collection (optional
	 * operation).
	 */
	public void clear() {
		Iterator<E> itr = iterator();
		while(itr.hasNext()) {
			itr.remove();
		}
	}

	/**
	 * Returns true if this collection contains the specified element.
	 */
	public boolean contains(Object o) {
		for(E e : this) {
			if(Objects.equals(o, e)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if this collection contains all of the elements in the
	 * specified collection.
	 *
	 * Runs in O(n * m) time, where c is of size m.
	 */
	public boolean containsAll(Collection<?> c) {
		for(Object o : c) {
			if(!contains(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if this collection contains no elements.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Removes a single instance of the specified element from this
	 * collection, if it is present (optional operation).
	 */
	public boolean remove(Object o) {
		Iterator<E> itr = iterator();
		while(itr.hasNext()) {
			if(Objects.equals(o, itr.next())) {
				itr.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes all of this collection's elements that are also contained in
	 * the specified collection (optional operation).
	 */
	public boolean removeAll(Collection<?> c) {
		Iterator<E> itr = iterator();
		boolean removedAny = false;
		while(itr.hasNext()) {
			if(c.contains(itr.next())) {
				// our el is in the other collection
				itr.remove();
				removedAny = true;
			}
		}
		return removedAny;
	}

	/**
	 * Retains only the elements in this collection that are contained in
	 * the specified collection (optional operation).
	 */
	public boolean retainAll(Collection<?> c) {
		Iterator<E> itr = iterator();
		boolean removedAny = false;
		while(itr.hasNext()) {
			if(!c.contains(itr.next())) {
				// our el isn't in the other collection
				itr.remove();
				removedAny = true;
			}
		}
		return removedAny;
	}

	/**
	 * Returns an array containing all of the elements in this collection.
	 */
	public Object[] toArray() {
		return toArray(new Object[0]);
	}

	/**
	 * Returns an array containing all of the elements in this collection;
	 * the runtime type of the returned array is that of the specified
	 * array.
	 */
	public <T> T[] toArray(T[] a) {
		Objects.requireNonNull(a);

		if(a.length < size()) {
			a = Arrays.copyOf(a, size());
		}

		int i = 0;
		for(E e : this) {
			if(i >= a.length) {
				// we need to expand the array
				// a better impl. might check for overflows...
				a = Arrays.copyOf(a, i * 2 + 1);
			}
			try {
				a[i] = (T) e;
			} catch(ClassCastException ex) {
				throw new ArrayStoreException();
			}
			i++;
		}

		// trim to length
		a = Arrays.copyOf(a, i + 1);

		return a;
	}

	/**
	 * Returns a string representation of this collection.
	 */
	@Override
	public String toString() {
		return Iterables.toString(this);
	}
}
