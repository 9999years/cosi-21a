import java.lang.Iterable;
import java.util.Iterator;

public class PlayerCircle implements Iterable<Player> {
	protected CircularOrderedList<Player> players = new CircularOrderedList<>();
	protected ListIterator<Player> itr = players.infiniteIterator();
	protected boolean reversed = false;

	public Player getFirstPlayer() {
		return players.getFront();
	}

	public void addToCircle(Player p) {
		players.add(p);
	}

	public void addToCircle(String name) {
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
	 * placement might not be the same
	 */
	public void swapLoser(Player newPlayer) {
		players.remove(loser());
		players.add(newPlayer);
	}

	public int getSize() {
		return players.size();
	}

	public String toString() {
		return players.toString();
	}

	public Iterator<Player> iterator() {
		return players.iterator();
	}

	/**
	 * gets the next player
	 */
	public Player advance() {
		if(reversed) {
			return itr.previous();
		} else {
			return itr.next();
		}
	}

	/**
	 * skips the next player
	 */
	public void skip() {
		advance();
	}

	/**
	 * reverses the direction of play
	 */
	public void reverse() {
		reversed = !reversed;
	}
}
