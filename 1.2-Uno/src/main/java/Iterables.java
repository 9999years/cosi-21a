import java.lang.Iterable;
import java.util.Comparator;
import java.util.Iterator;

public class Iterables {
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
			ret.delete(ret.length() - 2, ret.length());
			// "[x, y" -> "[x, y]"
		}
		ret.append("]");
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
