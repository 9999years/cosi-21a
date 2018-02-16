import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DoublyLinkedNodeTest {
	/**
	 * testing stuff like this isn't critical imo because it's so inherent
	 * to the list working that it's not worth explicitly testing; every
	 * list test would fail if any property tested here was broken. but hey
	 * the assignment said to do it so here it is
	 */
	@Test
	void simpleTest() {
		DoublyLinkedNode<Integer> node = new DoublyLinkedNode<>(100);
		assertEquals(100, (int) node.getData());
		DoublyLinkedNode<Integer> node2 = new DoublyLinkedNode<>(10);
		DoublyLinkedNode<Integer> node3 = new DoublyLinkedNode<>(1);
		node.setPrev(node2);
		node.setNext(node3);
		assertEquals(10, (int) node.getPrev().getData());
		assertEquals(1, (int) node.getNext().getData());
		assertEquals("100", node.toString());
		assertTrue(node.compareTo(1) > 0);
		assertTrue(node.compareTo(101) < 0);
		assertTrue(node.compareTo(100) == 0);
	}
}
