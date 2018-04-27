/**
 * an entry within a PriorityQueue
 * @param <P> the type of the priority in the entry
 */
public interface PriorityQueueEntry<P extends Comparable<P>> {
	P getPriority();
	void setPriority(P newPriority);
}
