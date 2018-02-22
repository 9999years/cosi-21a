import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;

public class CircularListTest {
	public static final int[] nums1 = new int[] {
		28, 5, 44, 30, 16, 1, 47, 3, 5, 48, 6, 9, 15, 16, 18, 23, 22,
		4, 2, 47, 48, 13, 6, 23, 19, 19, 39, 33, 10, 8, 11, 10, 13, 42,
		47, 20, 17, 8, 0, 31, 45, 19, 34, 40, 23, 48, 3, 26, 26, 45,
		39, 7, 46, 48, 37, 48, 9, 23, 41, 28, 45, 8, 38, 18, 35, 32,
		32, 1, 19, 11, 39, 42, 3, 30, 20, 6, 18, 12, 14, 33, 44, 33, 0,
		1, 12, 6, 26, 22, 17, 40, 26, 1, 12, 8, 34, 3, 32, 10, 41, 8,
		33 };

	List<Integer> boxed(int[] ints) {
		return Arrays.asList(
			IntStream.of(ints).boxed().toArray(Integer[]::new));
	}

	/**
	 * tests:
	 * size, isEmpty, toString, get(index), getFront, remove(data), add,
	 * removeFront
	 */
	@Test
	void simpleTest() {
		CircularList<Integer> list = new CircularList<>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		assertEquals("[]", list.toString());
		list.add(100);
		assertEquals("[100]", list.toString());
		assertEquals(1, list.size());
		assertEquals(100, (int) list.get(0));
		assertEquals(100, (int) list.getFront());
		list.add(200);
		assertEquals("[100, 200]", list.toString());
		assertEquals(2, list.size());
		assertEquals(100, (int) list.getFront());
		assertEquals(100, (int) list.get(0));
		assertEquals(200, (int) list.get(1));
		assertEquals(200, (int) list.get(-1));
		assertTrue(list.remove((Integer) 100));
		assertEquals("[200]", list.toString());
		assertFalse(list.remove((Integer) 100));
		assertEquals("[200]", list.toString());
		assertEquals(1, list.size());
		assertEquals(200, (int) list.getFront());
		list.removeFront();
		assertEquals(0, list.size());
		assertEquals("[]", list.toString());
	}

	/**
	 * tests:
	 * addAll, getFront, size, iterator, get(index)
	 */
	@Test
	void addAllTest() {
		CircularList<Integer> list = new CircularList<>();
		list.addAll(boxed(nums1));
		assertEquals(nums1.length, list.size());
		assertEquals(nums1[0], (int) list.getFront());
		int i = 0;
		for(int x : list) {
			assertEquals(nums1[i], (int) list.get(i));
			assertEquals(nums1[i], x);
			i++;
		}
	}

	@Test
	void infiniteIteratorTest() {
		CircularList<Integer> list = new CircularList<>();
		list.addAll(boxed(nums1));
		Iterator<Integer> itr = list.infiniteIterator();
		for(int i = 0; i < nums1.length * 10; i++) {
			assertEquals(nums1[i % nums1.length], (int) itr.next());
		}
	}

	@Test
	void indexTests() {
		CircularList<Integer> list = new CircularList<>();
		for(int i = 0; i < 100; i++) {
			list.add(0);
		}
		// list is now 100 copies of 0
		list.add(1, 31);
		assertEquals(1, (int) list.get(31));
		list.add(1, 0);
		assertEquals(1, (int) list.get(0));
		assertEquals(1, (int) list.remove(0));
		assertEquals(1, (int) list.remove(31));
		assertEquals(0, (int) list.get(0));
		assertEquals(0, (int) list.get(31));
	}

	@ParameterizedTest
	@MethodSource("numbersProvider")
	void listIteratorTest(List<Integer> input) {
		CircularList<Integer> list = new CircularList<>();
		list.addAll(input);
		ListIterator<Integer> itr = list.listIterator();
		assertFalse(itr.hasPrevious(), "no hasPrevious at start");
		assertEquals(-1, itr.previousIndex(), "previousIndex at start");
		assertEquals(0, itr.nextIndex(), "nextIndex at start");

		if(list.size() > 0) {
			assertTrue(itr.hasNext(), "hasNext at start (size > 0)");
		} else {
			assertFalse(itr.hasNext(), "no hasNext at start (size == 0)");
			// quit early; nothing to do
			return;
		}

		// traverse forwards
		int i = 0;
		while(itr.hasNext()) {
			assertEquals(i, itr.nextIndex(),
				"index (forward traversal)");
			assertEquals((Object) input.get(i), itr.next(),
				"element (forward traversal)");
			i++;
		}
		// index is actually past the end of the list (last index is
		// list.size() - 1 but we've incremented again)
		assertEquals(list.size(), i, "index == size after forward traversal");
		assertFalse(itr.hasNext(), "no hasNext at end");
		assertTrue(itr.hasPrevious(), "hasPrevious at end");

		// and backwards!
		i = list.size() - 1;
		while(itr.hasPrevious()) {
			assertEquals(i, itr.previousIndex(),
				"index (backwards traversal)");
			assertEquals((Object) input.get(i), itr.previous(),
				"element (backward traversal)");
			i--;
		}
		assertEquals(-1, i, "index after backwards traversal");
		assertFalse(itr.hasPrevious(),
			"no hasPrevious at start after backwards traversal");
		assertTrue(itr.hasNext(),
			"hasNext at start after backwards traversal");
	}

}
