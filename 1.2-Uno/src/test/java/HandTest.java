import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class HandTest {
	Hand simpleHand() {
		Hand ret = new Hand();
		ret.add(new UnoCard(UnoCard.Color.Green, 2));
		ret.add(new UnoCard(UnoCard.Color.Green, 9));
		ret.add(new UnoCard(UnoCard.Color.Yellow, 9));
		ret.add(new UnoCard(UnoCard.Color.Red, UnoCard.Special.Reverse));
		ret.add(new UnoCard(UnoCard.Color.Blue, UnoCard.Special.Skip));
		ret.add(new UnoCard(UnoCard.Color.Wild, UnoCard.Special.None));
		ret.add(new UnoCard(UnoCard.Color.Wild, UnoCard.Special.DrawFour));
	}

	@Test
	void playableTest() {
		Hand hand = simpleHand();
	}
}
