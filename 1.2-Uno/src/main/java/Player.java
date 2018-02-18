import java.lang.Comparable;
import java.util.Comparator;

import java.lang.IllegalStateException;

import java.lang.Iterable;
import java.util.Iterator;

public class Player implements Comparable<Player>, Iterable<UnoCard> {
	protected static int players = 0;

	public final String name;
	public final int id;
	public final Hand hand = new Hand();

	public Player(String name) {
		this.name = name;
		id = players;
		players++;
	}

	public Iterator<UnoCard> iterator() {
		return hand.iterator();
	}

	public int handSize() {
		return hand.size();
	}

	public boolean winner() {
		return hand.isEmpty();
	}

	public String toString() {
		return name;
	}

	public boolean equals(Player p) {
		return p.id == id;
	}

	/**
	 * compares names alphabetically
	 */
	public int compareTo(Player p) {
		return name.compareTo(p.name);
	}

	/**
	 * if you'd like to eg. find the player with the most or least cards,
	 * you can use this in place of the default alphabetical comparison
	 */
	public static Comparator<Player> handSizeComparator() {
		return Comparator.comparingInt(p -> p.handSize());
	}
}
