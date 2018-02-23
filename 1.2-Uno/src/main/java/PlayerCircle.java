import java.lang.Iterable;
import java.util.Iterator;
import java.util.ListIterator;

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

	public Player getFirstPlayer() {
		throwIfEmpty();
		return players.getFront();
	}

	public void addToCircle(Player p) {
		itr = null;
		players.add(p);
	}

	public void addToCircle(String name) {
		itr = null;
		players.add(new Player(name));
	}

	/**
	 * returns player with most cards
	 */
	public Player loser() {
		return Iterables.max(players, Player.handSizeComparator());
	}

	/**
	 * remove the loser from the circle and add newPlayer in their place;
	 * note that the player circle is still alphabetically ordered so the
	 * placement might not be the same; this restarts the game
	 * @return the loser
	 */
	public Player swapLoser(Player newPlayer) {
		Player loser = loser();
		players.remove(loser);
		players.add(newPlayer);
		itr = null;
		return loser;
	}

	protected void throwIfEmpty() {
		if(isEmpty()) {
			throw new IllegalStateException();
		}
	}

	public boolean isEmpty() {
		return getSize() == 0;
	}

	public int getSize() {
		return players.size();
	}

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

	public int nextIndex() {
		ensureIterator();
		return reversed ? itr.previousIndex() : itr.nextIndex();
	}

	/**
	 * gets the next player
	 * @throws IllegalStateException if the circle is empty
	 */
	public Player advance() {
		ensureIterator();
		turnsTaken++;
		return reversed ? itr.previous() : itr.next();
	}

	/**
	 * true if the next player returned by advance() is the first player in
	 * the circle
	 */
	public boolean nextIsFirst() {
		throwIfEmpty();
		return nextIndex() == 0;
	}

	/**
	 * skips the next player
	 */
	public void skip() {
		throwIfEmpty();
		// nullify the increment in advance(); nobody's actually played
		turnsTaken--;
		advance();
	}

	/**
	 * reverses the direction of play
	 */
	public void reverse() {
		reversed = !reversed;
		turnsTaken--;
		advance();
	}

	public float turnsPerPlayer() {
		return (float) turnsTaken / players.size();
	}

	public Iterator<Player> iterator() {
		return players.iterator();
	}
}
