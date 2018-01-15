import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayQueueTest {
	@Test
	void integrationTest() {
		// ok i had to check out a bunch of internals-checking stuff
		// because of... casting errors (generic arrays arent really a
		// thing which sucks etc etc)

		ArrayQueue<Integer> q = new ArrayQueue<Integer>(2);
		// ok, we're mucking around in the internals. but the internals
		// are important!
		//assertEquals((int) 2, q.arr.length);

		q.add(3);
		q.add(4);
		assertEquals((Integer) 3, q.remove());
		assertEquals((Integer) 4, q.remove());

		q.add(2);
		q.add(9);
		q.add(3);

		// ok, we're mucking around in the internals. but the internals
		// are important!
		q.expand();
		//assertEquals(4, q.arr.length);
		//assertEquals((Integer) 2, (Integer) q.arr[0]);
		//assertEquals((Integer) 9, (Integer) q.arr[1]);
		//assertEquals((Integer) 3, (Integer) q.arr[2]);
		assertEquals(3, q.size());

		assertEquals((Integer) 2, q.remove());
		assertEquals(2, q.size());
		assertEquals((Integer) 9, q.remove());
		assertEquals(1, q.size());
		assertEquals((Integer) 3, q.remove());
		assertEquals(0, q.size());
	}
}
