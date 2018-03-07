import java.util.Iterator;
import java.util.function.Function;

public class Iterators {
	private Iterators() {}

	public static <T, R> Iterator<R> map(
			Iterator<T> itr, Function<T, R> mapper) {
		return new Iterator<R>() {
			public boolean hasNext() {
				return itr.hasNext();
			}

			public void remove() {
				itr.remove();
			}

			public R next() {
				return mapper.apply(itr.next());
			}
		};
	}
}
