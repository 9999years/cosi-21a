package org.becca.cosi21a;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HashMapTest {
	@Test
	void integrationTest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("abc", "def");
		map.put("a", null);
		map.put(null, "qaz");

		assertTrue(map.containsKey("abc"));
		assertTrue(map.containsKey("a"));
		assertTrue(map.containsKey(null));

		assertFalse(map.containsKey("adc"));
		assertFalse(map.containsKey("b"));
		assertFalse(map.containsKey("\n"));

		assertEquals("def", map.get("abc"));
		assertEquals(null, map.get("a"));
		assertEquals("qaz", map.get(null));
	}
}
