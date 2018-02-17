import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

public class CircularOrderedListTest {
	static Integer[] nums = new Integer[] {
		46, 82, 11, 81, 33, 55, 97, 48, 75, 5, 70, 16, 1, 13, 45,
		69, 97, 81, 7, 87, 99, 87, 92, 67, 11, 97, 2, 32,
		79, 2, 60, 26, 42, 0, 53, 9, 28, 70, 93, 72, 92, 59
	};

	@Test
	void toStringTest() {
		CircularOrderedList<String> list = new CircularOrderedList<>();

		List<String> letters = Arrays.asList("d", "b", "e", "a", "c");
		for(String s : letters) {
			list.add(s);
		}

		assertEquals("[a, b, c, d, e]", list.toString());
	}

	<T extends Comparable<T>> void
	ensureSorted(CircularOrderedList<T> list) {
		if(list.size() < 2) {
			return;
		}
		T prev = list.get(0);
		for(T t : list) {
			assertTrue(t.compareTo(prev) >= 0);
			prev = t;
		}
	}

	@Test
	void sortingTest() {
		CircularOrderedList<Integer> list = new CircularOrderedList<>();
		for(int i : nums) {
			list.add(i);
			ensureSorted(list);
		}
		for(int i : nums) {
			list.remove((Integer) i);
			ensureSorted(list);
		}
	}
}
