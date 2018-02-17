import java.lang.Iterable;

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
}
