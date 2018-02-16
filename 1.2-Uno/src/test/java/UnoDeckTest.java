import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class UnoDeckTest {
	/**
	 * this draws 500 cards, adding them to a hand, and discards them
	 * whenever possible
	 */
	@Test
	void continualDrawTest() {
		UnoDeck deck = new UnoDeck();
		List<UnoCard> hand = new ArrayList<>();
		for(int i = 0; i < 500; i++) {
			if(!deck.empty()) {
				hand.add(deck.drawCard());
			}
			// discard any cards we can
			Iterator<UnoCard> itr = hand.iterator();
			while(itr.hasNext()) {
				UnoCard card = itr.next();
				if(card.canBePlacedOn(deck.getLastDiscarded())) {
					deck.discardCard(card);
					itr.remove();
				}
			}
		}
	}
}
