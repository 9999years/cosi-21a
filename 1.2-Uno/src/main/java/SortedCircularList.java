import java.lang.UnsupportedOperationException;
import java.lang.Comparable;

import java.util.Objects;

public class SortedCircularList<T extends Comparable<T>>
		extends CircularList<T> {
	/**
	 * illegal operation on a sorted list!
	 * @throws UnsupportedOperationException
	 */
	public void add(T t, int i) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * adds a non-null element into the list
	 * @param t a non-null T
	 * @throws NullPointerException if t is null
	 */
	public void add(T t) {
		Objects.requireNonNull(t);
		if(isEmpty() || t.compareTo(getFront()) <= 0) {
			addBefore(super.first, t);
			super.first = super.first.prev;
		} else {
			Node curr = super.first.next;
			while(curr != super.first) {
				if(t.compareTo(curr.data) <= 0) {
					addBefore(curr, t);
					return;
				}
				curr = curr.next;
			}
			addBefore(super.first, t);
		}
	}
}
