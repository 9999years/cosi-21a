import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

public class IterablesTest {
	@SafeVarargs
	@SuppressWarnings("varargs")
	final <T> Iterable<T> itr(T... ts) {
		// lol
		return Arrays.asList(ts);
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	final <T> String anyToString(T... ts) {
		return Iterables.toString(itr(ts));
	}

	@Test
	void simpleToStringTest() {
		assertEquals("[]", anyToString());
		assertEquals("[1]", anyToString(1));
		assertEquals("[1, 2]", anyToString(1, 2));
		assertEquals("[1, 2, 3]", anyToString(1, 2, 3));
		assertEquals("[1, null, 3]", anyToString(1, null, 3));
	}

	@Test
	void simpleEnglishToStringTest() {
		assertEquals("", Iterables.englishToString(Arrays.asList()));
		assertEquals("A", Iterables.englishToString(Arrays.asList("A")));
		assertEquals("A and B", Iterables.englishToString(
			Arrays.asList("A", "B")));
		assertEquals("A, B, and C", Iterables.englishToString(
			Arrays.asList("A", "B", "C")));
		assertEquals("A, B, C, and D", Iterables.englishToString(
			Arrays.asList("A", "B", "C", "D")));
	}

	@Test
	void minTest() {
		assertEquals(1, (int) Iterables.min(itr(1)));
		assertEquals(1, (int) Iterables.min(itr(1, 2, 10)));
		assertEquals(-111, (int) Iterables.min(itr(1, -111, 10)));
		assertEquals(-200, (int) Iterables.min(itr(1, -111, -200)));
	}

	@Test
	void maxTest() {
		assertEquals(1, (int) Iterables.max(itr(1)));
		assertEquals(10, (int) Iterables.max(itr(1, 2, 10)));
		assertEquals(999, (int) Iterables.max(itr(1, 999, 10)));
		assertEquals(200, (int) Iterables.max(itr(1, -111, 200)));
	}
}
