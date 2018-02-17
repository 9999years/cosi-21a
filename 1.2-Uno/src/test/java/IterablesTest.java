import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;

public class IterablesTest {
	<T> String anyToString(T... ts) {
		return Iterables.toString(Arrays.asList(ts));
	}

	@Test
	void simpleTest() {
		assertEquals("[]", anyToString());
		assertEquals("[1]", anyToString(1));
		assertEquals("[1, 2]", anyToString(1, 2));
		assertEquals("[1, 2, 3]", anyToString(1, 2, 3));
		assertEquals("[1, null, 3]", anyToString(1, null, 3));
	}
}
