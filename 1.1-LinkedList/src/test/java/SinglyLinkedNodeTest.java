import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SinglyLinkedNodeTest {
	@Test
	void simpleTest() {
		SinglyLinkedNode<Integer> node = new SinglyLinkedNode<>(100);
		assertEquals(100, (int) node.getData());
		SinglyLinkedNode<Integer> node2 = new SinglyLinkedNode<>(10);
		node.setNext(node2);
		assertEquals(10, (int) node.getNext().getData());
		assertEquals("100", node.toString());
	}
}
