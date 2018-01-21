import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HashMapTest {
	@Test
	void integrationTest() {
		HashMap<String, String> map = new HashMap<>();
		map.put("abc", "def");
		map.put("a", null);
		map.put(null, "qaz");

		assertTrue(map.contains("abc"));
		assertTrue(map.contains("a"));
		assertTrue(map.contains(null));

		assertFalse(map.contains("adc"));
		assertFalse(map.contains("b"));
		assertFalse(map.contains("\n"));

		assertEquals("def", map.get("abc"));
		assertEquals(null, map.get("a"));
		assertEquals("qaz", map.get(null));
	}
}
