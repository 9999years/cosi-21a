import java.lang.IllegalArgumentException;

/*
 * Uno deck
 */
public class UnoDeck {
	protected SinglyLinkedList<UnoCard> deck = new SinglyLinkedList<>();
	protected SinglyLinkedList<UnoCard> discard = new SinglyLinkedList<>();
	protected UnoCard lastDiscarded;

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
	public boolean empty() {
		return deck.empty() && discard.empty();
	}

	public UnoCard getLastDiscarded() {
		return lastDiscarded;
	}

	public UnoCard drawCard() {
		if(deck.empty()) {
			if(discard.empty()) {
				// uh oh -- someone drew all the cards from the
				// deck and didn't discard any
				return null;
			}
			// no reason to actually move 108 cards around when we
			// can just move the lists; one is completely empty and
			// one is completely full, so a simple swap is all we
			// need!
			SinglyLinkedList<UnoCard> oldDiscard = discard;
			discard = deck;
			deck = oldDiscard;
		}
		return deck.popHead();
	}

	public void discardCard(UnoCard c) {
		if(!c.canBePlacedOn(lastDiscarded)) {
			throw new IllegalArgumentException();
		}

		discard.randomInsert(c);
		lastDiscarded = c;
	}

	/**
	 * draws a card from the deck and places it on the discard pile; can be
	 * used for starting a game
	 */
	public void discardCard() {
		discardCard(drawCard());
	}

	public String toString() {
		return "UnoDeck[lastDiscarded=" + lastDiscarded + ", "
			+ "discard=" + discard.size() + ", "
			+ "deck=" + deck.size() + "]";
	}
}
