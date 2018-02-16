import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UnoCardTest {
	@Test
	void toStringTest() {
		assertEquals("Blue 1", new UnoCard(UnoCard.Color.Blue, 1).toString());
		assertEquals("Green draw two", new UnoCard(UnoCard.Color.Green,
			UnoCard.Special.DrawTwo).toString());
		assertEquals("Wild draw four", new UnoCard(UnoCard.Color.Wild,
			UnoCard.Special.DrawFour).toString());
		assertEquals("Red reverse", new UnoCard(UnoCard.Color.Red,
			UnoCard.Special.Reverse).toString());
		assertEquals("Yellow skip", new UnoCard(UnoCard.Color.Yellow,
			UnoCard.Special.Skip).toString());
	}
}
