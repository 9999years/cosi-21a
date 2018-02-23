/**
 * Rebecca Turner (rebeccaturner@brandeis.edu)
 * license: GPLv3
 */

import java.lang.Comparable;
import java.util.Comparator;

import java.lang.IllegalStateException;

import java.lang.Iterable;
import java.util.Iterator;

/**
 * represents a hand in a game of uno, backed by a doubly-linked sorted list
 */
public class Hand implements Iterable<UnoCard> {
	protected DoublyLinkedOrderedList<UnoCard> cards
		= new DoublyLinkedOrderedList<>();

	public Iterator<UnoCard> iterator() {
		return cards.iterator();
	}

	/**
	 * O(n) time
	 */
	public Hand playable(UnoCard top) {
		Hand ret = new Hand();
		for(UnoCard c : cards) {
			if(c.canBePlacedOn(top)) {
				ret.add(c);
			}
		}
		return ret;
	}

	/**
	 * check if the hand contains the given card; return the card if it was
	 * found and null if it wasnt. O(n) time
	 */
	public UnoCard get(String card) {
		String trimmed = card.trim();
		for(UnoCard c : this) {
			if(c.toString().trim().compareToIgnoreCase(trimmed) == 0) {
				return c;
			}
		}
		return null;
	}

	/**
	 * check if the hand contains the given card; remove and return the
	 * card if it was found and return null if it wasnt. O(n) time
	 */
	public UnoCard remove(UnoCard card) {
		Iterator<UnoCard> hand = cards.iterator();
		while(hand.hasNext()) {
			UnoCard c = hand.next();
			if(c.equals(card)) {
				hand.remove();
				return c;
			}
		}
		return null;
	}

	/**
	 * returns the first card in this hand. O(1) time
	 */
	public UnoCard first() {
		return cards.getHead();

	}

	/**
	 * removes and returns the given card. O(n) time
	 */
	public UnoCard remove(String card) {
		throw new UnsupportedOperationException();
	}

	/**
	 * the hand is sorted so this is, perhaps surprisingly, O(n) amortized
	 * time
	 */
	public void add(UnoCard c) {
		cards.insert(c);
	}

	/**
	 * O(1) time
	 */
	public void clearHand() {
		cards.clear();
	}

	/**
	 * O(n) time
	 * @see add
	 */
	public UnoCard draw(UnoDeck deck) {
		UnoCard card = deck.drawCard();
		cards.insert(card);
		return card;
	}

	/**
	 * clears the hand and draws 7 from the deck; called to reset the hand
	 * after a round or before a round. O(n) time, kinda (really 7*n for 7
	 * cards * n insertion)
	 */
	public void reset(UnoDeck deck) {
		cards.clear();
		for(int i = 0; i < UnoGame.UNO_STARTING_HAND_SIZE; i++) {
			add(deck.drawCard());
		}
	}

	/**
	 * if there are owed cards from a draw two or draw four, pick them up
	 */
	public void pickUpOwed(UnoDeck deck) {
		while(deck.cardsOwed() > 0) {
			add(deck.drawOwedCard());
		}
	}

	/**
	 * O(1) time
	 */
	public int size() {
		return cards.size();
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * string representation of the player's hand
	 * O(n) time
	 */
	public String toString() {
		return Iterables.englishToString(cards);
	}
}
