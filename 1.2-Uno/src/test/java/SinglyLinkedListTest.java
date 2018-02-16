import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * size is tested throughout these; everything else is tested explicitly
 * but i check that invariants (such as size) are held throughout the tests
 */
public class SinglyLinkedListTest {
	public static final int[] nums1 = new int[] {
		28, 5, 44, 30, 16, 1, 47, 3, 5, 48, 6, 9, 15, 16, 18, 23, 22,
		4, 2, 47, 48, 13, 6, 23, 19, 19, 39, 33, 10, 8, 11, 10, 13, 42,
		47, 20, 17, 8, 0, 31, 45, 19, 34, 40, 23, 48, 3, 26, 26, 45,
		39, 7, 46, 48, 37, 48, 9, 23, 41, 28, 45, 8, 38, 18, 35, 32,
		32, 1, 19, 11, 39, 42, 3, 30, 20, 6, 18, 12, 14, 33, 44, 33, 0,
		1, 12, 6, 26, 22, 17, 40, 26, 1, 12, 8, 34, 3, 32, 10, 41, 8,
		33 };

	// no duplicates
	public static final int[] nums2 = new int[] {
		28, 5, 44, 30, 16, 1, 47, 3, 48, 6, 9, 15, 18, 23, 22 };

	/**
	 * add a bunch of numbers via .regularInsert() and ensure that they're
	 * all added in the right position;
	 */
	@Test
	public void regularInsertTest() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

		for(int i : nums1) {
			list.regularInsert(i);
		}

		int inx = 0;
		for(int i : list) {
			assertEquals(nums1[inx], i,
				"Element at position " + inx);
			inx++;
		}

		assertEquals(nums1.length, list.size());
		// last index is n - 1 but we increment and then fail the counter
		// so we need the whole size
		assertEquals(nums1.length, inx);
	}

	@Test
	public void getHeadTest() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
		list.regularInsert(100);
		assertEquals(100, (int) list.getHead().getData());
		assertEquals(null, list.getHead().getNext());
	}

	/**
	 * O(n^2) performance so we keep our number list short; we also set a
	 * custom seed in the list's rng
	 */
	@Test
	public void randomInsertTest() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
		list.rand = new Random(637275);

		for(int i : nums2) {
			list.randomInsert(i);
		}

		assertEquals(nums2.length, list.size(), "list size is as expected");

		for(int i : nums2) {
			boolean found = false;
			for(int j : list) {
				if(j == i) {
					// found our number!
					found = true;
					break;
				}
			}
			assertTrue(found, i + " not found in list!");
		}
	}

	@Test
	public void removeTest() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
		// add some stuff
		list.regularInsert(100);
		list.regularInsert(10);
		list.regularInsert(1);
		// [100, 10, 1]

		assertEquals(3, list.size());

		// removal of nonexistent item
		list.remove(9);
		assertEquals(3, list.size());

		// -> [100, 1]
		list.remove(10);

		// uhh maybe we should have a get(index) method but
		// i'm really working on not doing a ton more than the
		// assignment asks for no reason. so we're doing this
		assertEquals(100, (int) list.getHead().getData());
		assertEquals(1, (int) list.getHead().getNext().getData());

		assertEquals(2, list.size());

		// removal at end
		list.remove(1);
		assertEquals(1, list.size());

		// removal at beginning / size 1 list
		list.remove(100);
		assertEquals(0, list.size());

		// removal of nonexistent item on empty list
		list.remove(9);
		assertEquals(0, list.size());
	}

	public void toStringTest() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

		assertEquals("[]", list.toString());

		list.regularInsert(1);

		assertEquals("[1]", list.toString());

		list.remove(1);

		for(int i : nums2) {
			list.regularInsert(i);
		}

		assertEquals("[28, 5, 44, 30, 16, 1, 47, 3, 48, 6, 9, 15, 18, 23, 22]", list.toString());
	}

	@Test
	public void removeHeadTest() {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
		for(int i : nums1) {
			list.regularInsert(i);
		}

		int i = 0;
		while(!list.isEmpty()) {
			assertTrue(i < nums1.length, "popped more than added");
			assertEquals(nums1[i], (int) list.removeHead());
			i++;
		}

		// repeated because i had a bug where the size wasn't
		// decremented, causing an NPE on the *second* call of
		// removeHead on an empty list
		assertEquals(null, list.removeHead(), "null when empty");
		assertEquals(null, list.removeHead(), "null when empty");
	}
}
