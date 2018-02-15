//public class DoublyLinkedOrderedList<T> {
	// implementation notes:
	// if you ensure that the head and tail are always-extant empty nodes
	// and never let them leak, you can simplify a lot of the logic; by
	// ensuring that (as long as it's not the tail) a node's .next will
	// never be null, you can cut back on a lot of `if`s. however, this
	// implementation requires insertion at the *end* of the list, and
	// because it's not a doubly-linked list that makes things a bit
	// trickier, because there'd be no way to get from an empty tail node
	// to the last real node in the list. one horrible and unmaintainable
	// solution would be to add another field for the last real node in the
	// list and just keep the tail node around for book-keeping purposes.
	// ANOTHER solution is, because this linked 'list' interface requires
	// no insertion / removal at the front of the list (which really
	// defeats the purpose of using a linked list, also see [1]), is to
	// insert the elements in backwards order and then just pretend the
	// head is the tail.
	//
	// [1]: Alexis Beingessner calls array-based deques and stacks
	// "blatantly superior data structures for most workloads due to less
	// frequent allocation, lower memory overhead, true random access, and
	// cache locality" in contrast with linked lists. See:
	// http://cglab.ca/~abeinges/blah/too-many-lists/book/
	// I know what you're thinking, we need the O(1) random removal of a
	// linked list! but removing a random element in a linked list takes
	// O(n) time if you account for actually getting your hands on the
	// node; N swaps are certainly no more expensive than N compares,
	// especially if an equality method is non-trivial.
//}
