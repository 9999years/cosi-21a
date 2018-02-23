import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

/*
 * Uno deck
 */
public class UnoDeck {
	protected SinglyLinkedList<UnoCard> deck = new SinglyLinkedList<>();
	protected SinglyLinkedList<UnoCard> discard = new SinglyLinkedList<>();

	protected UnoCard lastDiscarded;
	/**
	 * non-zero if the next player needs to pick up extra cards ie a draw 2
	 * or draw 4 card
	 */
	protected int cardsOwed = 0;

	/**
	 * There are 108 cards in a Uno deck.  There are four suits, Red,
	 * Green, Yellow and Blue, each consisting of one 0 card, two 1 cards,
	 * two 2s, 3s, 4s, 5s, 6s, 7s, 8s and 9s; two Draw Two cards; two Skip
	 * cards; and two Reverse cards.  In addition there are four Wild cards
	 * and four Wild Draw Four cards.
	 *
	 * @url http://play-k.kaserver5.org/Uno.html
	 */
	public UnoDeck() {
		initDeck();
	}

	protected void addTwice(UnoCard card) {
		deck.randomInsert(card);
		deck.randomInsert(card);
	}

	protected void add(UnoCard card) {
		deck.randomInsert(card);
	}

	protected void initDeck() {
		for(UnoCard.Color color : UnoCard.Color.values()) {
			if(color == UnoCard.Color.Wild) {
				continue;
			}
			add(new UnoCard(color, 0));
			for (int i = 1; i <= 9; i++){
				addTwice(new UnoCard(color, i));
				addTwice(new UnoCard(color, UnoCard.Special.DrawTwo));
				addTwice(new UnoCard(color, UnoCard.Special.Skip));
				addTwice(new UnoCard(color, UnoCard.Special.Reverse));
			}

		}

		// 4 wild and 4 wild draw 4
		addTwice(new UnoCard(UnoCard.Color.Wild, UnoCard.Special.None));
		addTwice(new UnoCard(UnoCard.Color.Wild, UnoCard.Special.None));
		addTwice(new UnoCard(UnoCard.Color.Wild, UnoCard.Special.DrawFour));
		addTwice(new UnoCard(UnoCard.Color.Wild, UnoCard.Special.DrawFour));
	}

	// all cards are out of the deck and in hands
	public boolean isEmpty() {
		return deck.isEmpty() && discard.isEmpty();
	}

	public UnoCard getLastDiscarded() {
		return lastDiscarded;
	}

	/**
	 * @throws IllegalStateException if the deck is empty, ie all the cards
	 * have been drawn and none have been discarded back into the deck
	 */
	public UnoCard drawCard() {
		if(deck.isEmpty()) {
			if(discard.isEmpty()) {
				// uh oh -- someone drew all the cards from the
				// deck and didn't discard any
				throw new IllegalStateException();
			}
			// no reason to actually move 108 cards around when we
			// can just move the lists; one is completely empty and
			// one is completely full, so a simple swap is all we
			// need!
			SinglyLinkedList<UnoCard> oldDiscard = discard;
			discard = deck;
			deck = oldDiscard;
		}
		return deck.removeHead();
	}

	/**
	 * cards owed to the next player as the result of a draw 2 or draw 4
	 */
	public int cardsOwed() {
		return cardsOwed;
	}

	/**
	 * draw an owed card and decrease the owed card counter
	 * @throws IllegalStateException if there are no owed cards to draw
	 */
	public UnoCard drawOwedCard() {
		if(cardsOwed == 0) {
			throw new IllegalStateException();
		}
		UnoCard card = drawCard();
		cardsOwed--;
		return card;
	}

	public void discardCard(UnoCard c) {
		if(!c.canBePlacedOn(lastDiscarded)) {
			throw new IllegalArgumentException();
		}

		if(c.special == UnoCard.Special.DrawTwo) {
			cardsOwed += 2;
		} else if(c.special == UnoCard.Special.DrawFour) {
			cardsOwed += 4;
		}

		discard.randomInsert(c);
		lastDiscarded = c;
	}

	/**
	 * draws a card from the deck and places it on the discard pile; can be
	 * used for starting a game
	 */
	public void discardCard() {
		// circumvent our regular discardCard method to avoid
		// IllegalArgumentExceptions
		lastDiscarded = drawCard();
		discard.randomInsert(lastDiscarded);
	}

	public String toString() {
		return "UnoDeck[lastDiscarded=" + lastDiscarded + ", "
			+ "discard=" + discard.size() + ", "
			+ "deck=" + deck.size() + "]";
	}
}
