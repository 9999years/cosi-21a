import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class DoublyLinkedOrderedListTest {
	// super low quality random numbers
	static Integer[] numbers1 = new Integer[] {
		46, 82, 11, 81, 33, 55, 97, 48, 75, 5, 70, 16, 1, 13, 45,
		69, 97, 81, 7, 87, 99, 87, 92, 67, 11, 97, 2, 32,
		79, 2, 60, 26, 42, 0, 53, 9, 28, 70, 93, 72, 92, 59
	};

	static Integer[] numbers2 = new Integer[] {
		46, 82, 11, 81, 33, 55, 97, 48, 75, 5, 70, 16, 1, 13, 45,
		69, 97, 81, 7, 87, 99, 87, 92, 67, 11, 97, 2, 32, 79, 2, 60,
		26, 42, 0, 53, 9, 28, 70, 93, 72, 92, 59, 68, 6, 74, 20, 42,
		35, 2, 39, 77, 97, 65, 0, 21, 53, 80, 77, 40, 14, 32, 42,
		45, 34, 70, 16, 26, 97, 0, 58, 21, 22, 79, 96, 77, 60, 5,
		62, 40, 22, 33, 22, 39, 53, 88, 82, 70, 84, 24, 1, 95, 31,
		19, 52, 7, 14, 89, 52, 2, 98, 88, 60, 56, 17, 76, 23, 13,
		53, 93, 66, 49, 8, 57, 21, 61, 47, 73, 19, 27, 45, 99, 28,
		49, 11, 79, 18, 90, 84, 72, 96, 13, 52, 13, 51, 22, 5, 1,
		85, 11, 31, 95, 18, 99, 75, 44, 44, 17, 17, 86, 12, 38, 21,
		87, 38, 36, 4, 20, 88, 53, 9, 15, 40, 34, 64, 75, 11, 43,
		98, 31, 80, 18, 6, 69, 84, 77, 80, 90, 29, 42, 50, 25, 10,
		34, 17, 93, 37, 64, 40, 72, 28, 88, 96, 14, 65, 50, 35, 0,
		1, 1, 9, 71, 23, 33, 61, 17, 38, 88, 35, 35, 9, 20, 64, 73
	};

	static Integer[] numbers3 = new Integer[] { Integer.MAX_VALUE };
	static Integer[] numbers4 = new Integer[] { };
	static Integer[] numbers5 = new Integer[] { 0, 1, 2, 9, -1929 };
	static Integer[] numbers6 = new Integer[] { 4574 };

	// large
	static Integer[] numbers7 = new Integer[] {
		-47213, -2720, -70973, -13345, 78590, 24782, 95603, -47864,
		-8482, 33537, 48241, -26363, 31811, -79587, 89727, 92298,
		-75600, -28021, 15496, 49950, -14363, -55650, 24166, -97619,
		-31501, -39970, -16778, -78949, 53814, -69167, 2867, 20262,
		12979, 96138, -28348, 37226, -93433, 50414, -59573, 82982,
		-12107, -50518, -55096, 90316, 92861, -30850, -34047,
		-18991, -4661, -25659, -5689, 78391, -1030, -27725, 50057,
		-267, -10340, -83383, -48850, -24068, -51806, 73706, -67109,
		15743, 13615, 15688, 46265, -68742, -17744, -62110, -73090,
		21011, 42262, -97074, 22240, -68063, -84325, -82278, -41580,
		-88011, 67423, -74362, -37781, -38371, -71326, 69009,
		-62642, 89723, -87227, 4306, 73450, 99339, 75052, 26276,
		9195, -83976, -66808, 37444, 40150, 36455, -63222
	};

	// HUGE
	static Integer[] numbers8 = new Integer[] {
		2141189727, 1963605963, 783498319, 1701260636, 1765489994,
		1933443289, 258168141, 1975481639, 418860235, 1017993252,
		1501404128, 227608783, 1022964812, 521546890, 97948355,
		1407375095, 549721842, 310321518, 1975664599, 282684906,
		534392402, 1228024760, 1765035350, 1193337125, 1902366823,
		389091032, 1950610549, 1257321748, 1923648557, 370327224,
		950394968, 1287301870, 64725458, 1579091381, 22437409,
		1817697516, 79444263, 1829667893, 977305537, 1964229413,
		1116992745, 807022108, 52159024, 1087620160, 802096366,
		1422548673, 1448413065, 1082077687, 1985041078, 526196629,
		1841046260, 1812339384, 778222411, 345280994, 52399747
	};

	static Integer[][] numbers = new Integer[][] {
		numbers1,
		numbers2,
		numbers3,
		numbers4,
		numbers5,
		numbers6,
		numbers7,
		numbers8
	};

	Integer[] boxed(int[] ints) {
		return IntStream.of(ints).boxed().toArray(Integer[]::new);
	}

	static Stream<List<Integer>> numbersProvider() {
		return Arrays.stream(numbers).map(t -> Arrays.asList(t));
	}

	/**
	 * basic operations
	 */
	@Test
	void simpleTest1() {
		DoublyLinkedOrderedList<Integer> list = new DoublyLinkedOrderedList<>();
		assertEquals((Object) 0, list.size());
		assertSame(list.tail.prev, list.head,
			"asserting head and tail point towards each other");
		assertSame(list.tail, list.head.next,
			"asserting head and tail point towards each other");
		Integer el = 100;
		list.insert(el);
		assertEquals(1, list.size(), "size");
		assertEquals((Object) el, list.head.next.data, "head.next");
		assertEquals((Object) el, list.tail.prev.data, "tail.prev");
	}

	@ParameterizedTest
	@MethodSource("numbersProvider")
	void addRemoveTest(List<Integer> input) {
		DoublyLinkedOrderedList<Integer> list = new DoublyLinkedOrderedList<>();
		for(int i : input) {
			list.insert(i);
		}
		assertEquals(input.size(), list.size());
		for(int i : input) {
			list.delete(i);
		}
		assertEquals(0, list.size());
	}

	@ParameterizedTest
	@MethodSource("numbersProvider")
	void clearTest(List<Integer> input) {
		DoublyLinkedOrderedList<Integer> list = new DoublyLinkedOrderedList<>();
		for(int i : input) {
			list.insert(i);
		}
		list.clear();
		assertEquals(0, list.size(), "list size after clear");
		assertEquals(list.head.next, list.tail,
			"list head -> tail");
		assertEquals(list.tail.prev, list.head,
			"list tail -> head");
	}

	@Test
	void toStringTest() {
		DoublyLinkedOrderedList<String> list = new DoublyLinkedOrderedList<>();

		List<String> letters = Arrays.asList("d", "b", "e", "a", "c");
		for(String s : letters) {
			list.insert(s);
		}

		assertEquals("[a, b, c, d, e]", list.toString());
	}

	<T extends Comparable<T>> void
	ensureSorted(DoublyLinkedOrderedList<T> list) {
		T prev = null;
		for(DoublyLinkedNode<T> n : list.nodeIterator()) {
			assertTrue(n.compareTo(prev) >= 0);
			prev = n.data;
		}
	}

	@ParameterizedTest
	@MethodSource("numbersProvider")
	void sortingTest(List<Integer> input) {
		DoublyLinkedOrderedList<Integer> list = new DoublyLinkedOrderedList<>();
		for(int i : input) {
			list.insert(i);
			ensureSorted(list);
		}
		for(int i : input) {
			list.delete(i);
			ensureSorted(list);
		}
	}
}
