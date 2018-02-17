import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

public class IterablesTest {
	<T> Iterable<T> itr(T... ts) {
		// lol
		return Arrays.asList(ts);
	}

	<T> String anyToString(T... ts) {
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
	void minTest() {
		assertEquals(1, (int) Iterables.min(itr(1)));
		assertEquals(1, (int) Iterables.min(itr(1, 2, 10)));
		assertEquals(-111, (int) Iterables.min(itr(1, -111, 10)));
		assertEquals(-200, (int) Iterables.min(itr(1, -111, -200)));
	}
}
