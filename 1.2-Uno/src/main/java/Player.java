import java.lang.Comparable;
import java.util.Comparator;

import java.lang.IllegalStateException;

public class Player implements Comparable<Player> {
	public final String name;
	protected DoublyLinkedOrderedList<UnoCard> hand
		= new DoublyLinkedOrderedList<>();

	public Player(String name) {
		this.name = name;
	}

	public void clearHand() {
		hand.clear();
	}

	public void addToHand(UnoCard c) {
		hand.insert(c);
	}

	/**
	 * @throws IllegalStateException if the player doesn't have an empty
	 * hand
	 */
	public void getHand(UnoDeck deck) {
		if(!hand.isEmpty()) {
			throw new IllegalStateException();
		}

		for(int i = 0; i < UnoGame.UNO_STARTING_HAND_SIZE; i++) {
			addToHand(deck.drawCard());
		}
	}

	public void removeFromHand(int index) {
		//you have to implement this
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
