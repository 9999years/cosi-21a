/**
 * Rebecca Turner (rebeccaturner@brandeis.edu)
 * license: GPLv3
 */

import java.lang.Iterable;
import java.util.Comparator;
import java.util.Iterator;

/**
 * utility class for operations on iterables i.e. finding minima and prettily
 * converting to strings
 */
public class Iterables {
	/**
	 * slices `len` code units from the end of sb
	 */
	protected static void sliceEnd(StringBuilder sb, int len) {
		sb.delete(sb.length() - len, sb.length());
	}

	public static <T> String toString(Iterable<T> itr) {
		StringBuilder ret = new StringBuilder("[");
		for(T t : itr) {
			ret.append(t);
			ret.append(", ");
		}
		if(ret.length() > 2) {
			// more than an opening bracket i.e. non-empty iterable

			// "[x, y," -> "[x, y"
			// saves us from having to keep track of if the
			// iterator is empty so we dont add an extra comma at
			// the end
			sliceEnd(ret, 2);
			// "[x, y" -> "[x, y]"
		}
		ret.append("]");
		return ret.toString();
	}

	/**
	 * given ex. an array [A, B, C] this returns "A, B, and C"
	 * [] → ""
	 * [A] → "A"
	 * [A, B] → "A and B"
	 * [A, B, C, D] → "A, B, C, and D"
	 */
	public static <T> String englishToString(Iterable<T> ita) {
		StringBuilder ret = new StringBuilder();
		Iterator<T> itr = ita.iterator();
		int count = 0;
		while(itr.hasNext()) {
			T t = itr.next();
			if(!itr.hasNext()) {
				if(count == 1) {
					// only 2 elements in the list; slice a
					// comma
					sliceEnd(ret, 2);
					ret.append(" and ");
				} else if(count > 1) {
					ret.append("and ");
				}
				// we're before the last element in the list
				ret.append(t);
				break;
			} else {
				ret.append(t);
				ret.append(", ");
			}
			count++;
		}
		return ret.toString();
	}

	/**
	 * finds the minimum element in an iterable
	 * @throws NoSuchElementException if ita is empty
	 */
	public static <T> T min(Iterable<T> ita, Comparator<T> comp) {
		Iterator<T> itr = ita.iterator();
		T min = itr.next();
		while(itr.hasNext()) {
			T t = itr.next();
			if(comp.compare(t, min) < 0) {
				// t < min
				min = t;
			}
		}
		return min;
	}

	/**
	 * finds the minimum element in an iterable
	 * @throws NoSuchElementException if ita is empty
	 */
	public static <T extends Comparable<T>> T min(Iterable<T> itr) {
		return min(itr, Comparator.naturalOrder());
	}

	/**
	 * finds the maximum element in an iterable
	 * @throws NoSuchElementException if ita is empty
	 */
	public static <T> T max(Iterable<T> ita, Comparator<T> comp) {
		// if we reverse the ordering, we can try to find the 'minimum'
		// and really get the maximum
		return min(ita, comp.reversed());
	}

	/**
	 * finds the maximum element in an iterable
	 * @throws NoSuchElementException if ita is empty
	 */
	public static <T extends Comparable<T>> T max(Iterable<T> itr) {
		return min(itr, Comparator.reverseOrder());
	}
}
