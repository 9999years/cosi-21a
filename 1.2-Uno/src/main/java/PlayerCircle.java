/**
 * Rebecca Turner (rebeccaturner@brandeis.edu)
 * license: GPLv3
 */

import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;

import java.util.stream.StreamSupport;

import java.lang.IllegalStateException;

public class PlayerCircle implements Iterable<Player> {
	protected CircularOrderedList<Player> players = new CircularOrderedList<>();
	protected ListIterator<Player> itr;
	/**
	 * amount of turns taken so far; because Uno allows order reversal and
	 * skipping, "count of times play has passed the first player" is a
	 * fairly useless metric
	 */
	protected int turnsTaken = 0;
	protected boolean reversed = false;

	protected void throwIfEmpty() {
		if(isEmpty()) {
			throw new IllegalStateException();
		}
	}

	/**
	 * @throws IllegalStateException if the circle is empty
	 */
	public Player getFirstPlayer() {
		throwIfEmpty();
		return players.getFront();
	}

	/**
	 * O(n) time to maintain the circle's asciibetical sortedness
	 */
	public void addToCircle(Player p) {
		itr = null;
		players.add(p);
	}

	/**
	 * O(n) time to maintain the circle's asciibetical sortedness
	 */
	public void addToCircle(String name) {
		itr = null;
		players.add(new Player(name));
	}

	/**
	 * returns player with most cards; O(n) time
	 * @throws IllegalStateException if the circle is empty
	 */
	public Player loser() {
		return StreamSupport.stream(players.spliterator(), false)
			.max(Player.HAND_SIZE_COMPARATOR)
			.orElseThrow(IllegalStateException::new);
	}

	/**
	 * remove the loser from the circle and add newPlayer in their place;
	 * note that the player circle is still alphabetically ordered so the
	 * placement might not be the same; this restarts the game. O(n) time
	 * @return the loser
	 */
	public Player swapLoser(Player newPlayer) {
		Player loser = loser();
		players.remove(loser);
		players.add(newPlayer);
		itr = null;
		return loser;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * conformant with every other java api in the history of the world.
	 * O(1) time
	 */
	public int size() {
		return players.size();
	}

	/**
	 * O(n) time
	 */
	public String toString() {
		return Iterables.englishToString(players);
	}

	/**
	 * ensures the iterator is valid
	 */
	protected void ensureIterator() {
		throwIfEmpty();
		if(itr == null) {
			itr = players.infiniteIterator();
		}
	}

	/**
	 * O(1) time
	 * @throws IllegalStateException if the circle is empty
	 */
	public int nextIndex() {
		ensureIterator();
		return reversed ? itr.previousIndex() : itr.nextIndex();
	}

	/**
	 * gets the next player; O(1) time
	 * @throws IllegalStateException if the circle is empty
	 */
	public Player advance() {
		ensureIterator();
		turnsTaken++;
		return reversed ? itr.previous() : itr.next();
	}

	/**
	 * true if the next player returned by advance() is the first player in
	 * the circle. O(1) time
	 * @throws IllegalStateException if the circle is empty
	 */
	public boolean nextIsFirst() {
		throwIfEmpty();
		return nextIndex() == 0;
	}

	/**
	 * skips the next player; O(1) time
	 * @throws IllegalStateException if the circle is empty
	 */
	public void skip() {
		throwIfEmpty();
		// nullify the increment in advance(); nobody's actually played
		turnsTaken--;
		advance();
	}

	/**
	 * reverses the direction of play; O(1) time
	 */
	public void reverse() {
		reversed = !reversed;
		turnsTaken--;
		advance();
	}

	/**
	 * the average amount of turns each player has taken
	 */
	public float turnsPerPlayer() {
		return (float) turnsTaken / players.size();
	}

	public Iterator<Player> iterator() {
		return players.iterator();
	}
}
