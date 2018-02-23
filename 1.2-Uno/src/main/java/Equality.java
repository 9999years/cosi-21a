/**
 * Rebecca Turner (rebeccaturner@brandeis.edu)
 * license: GPLv3
 */

/**
 * now *I* think this kind of thing --- a one-off class to factor out a tiny
 * piece of code --- is completely ridiculous. however, have you seen any
 * real-world java projects? they're all filled with this stuff. anyways, in
 * the Real World people use Guava or something (right?) for this kind of thing
 * (zipping iterators, etc). but this isn't the real world, so here we are!
 *
 * oh this is used by CircularList and SinglyLinkedList, but it'll come up a
 * lot in any generic data structure
 */
public class Equality {
	/**
	 * makes the class uninstantiatable
	 */
	private Equality() { }

	public static boolean nullableEquals(Object a, Object b) {
		return a == null ? b == null : a.equals(b);
	}
}
