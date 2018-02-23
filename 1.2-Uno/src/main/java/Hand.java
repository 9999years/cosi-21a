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
	 * found and null if it wasnt
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
	 * card if it was found and return null if it wasnt
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
	 * returns the first card in this hand
	 */
	public UnoCard first() {
		return cards.getHead();

	}

	/**
	 * removes and returns the given card
	 */
	public UnoCard remove(String card) {
		throw new UnsupportedOperationException();
	}

	public void add(UnoCard c) {
		cards.insert(c);
	}

	public void clearHand() {
		cards.clear();
	}

	public UnoCard draw(UnoDeck deck) {
		UnoCard card = deck.drawCard();
		cards.insert(card);
		return card;
	}

	public void reset(UnoDeck deck) {
		cards.clear();
		for(int i = 0; i < UnoGame.UNO_STARTING_HAND_SIZE; i++) {
			add(deck.drawCard());
		}
	}

	/**
	 * if there are owed cards, pick them up
	 */
	public void pickUpOwed(UnoDeck deck) {
		while(deck.cardsOwed() > 0) {
			add(deck.drawOwedCard());
		}
	}

	public void removeFromHand(int index) {
		//you have to implement this
	}

	public int size() {
		return cards.size();
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * string representation of the player's hand
	 */
	public String toString() {
		return Iterables.englishToString(cards);
	}
}
