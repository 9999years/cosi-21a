/**
 * Rebecca Turner (rebeccaturner@brandeis.edu)
 * license: GPLv3
 */

import java.lang.UnsupportedOperationException;
import java.lang.Comparable;

import java.util.Objects;

/**
 * sorted circularly linked list; there's of course a disjoint between the 1st
 * and last elements (positions 0 and -1; see documentation of CircularList)
 *
 * @see CircularList
 */
public class CircularOrderedList<T extends Comparable<T>>
		extends CircularList<T> {
	/**
	 * illegal operation on a sorted list! always throws an exception
	 * @throws UnsupportedOperationException always
	 */
	public void add(T t, int i) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * adds a non-null element into the list; O(n) amortized time
	 * @param t a non-null T
	 * @throws NullPointerException if t is null
	 */
	public boolean add(T t) {
		Objects.requireNonNull(t);
		if(isEmpty() || t.compareTo(getFront()) <= 0) {
			// case where the list is empty or the natural ordering
			// of t is before the first element: insert a new
			// element at the front and move the front back; if the
			// list has only one element, first.prev is still just
			// first so the move is harmless
			addBefore(super.first, t);
			super.first = super.first.prev;
		} else {
			// else: the node needs to be inserted somewhere
			// between the 2nd position and the end of the list;
			// iterate through the list until we find an element
			// larger than t and insert t before that
			Node curr = super.first.next;
			while(curr != super.first) {
				if(t.compareTo(curr.data) <= 0) {
					addBefore(curr, t);
                    return false;
				}
				curr = curr.next;
			}
			// add at end
			addBefore(super.first, t);
		}
        return false;
    }
}
