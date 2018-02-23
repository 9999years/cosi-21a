import java.lang.IllegalArgumentException;
import java.lang.Comparable;

/**
 * Rebecca Turner, 2018
 */
public class UnoCard implements Comparable<UnoCard> {
	public enum Color {
		Blue, Green, Red, Yellow, Wild;
	}

	public enum Special {
		None     (""),
		DrawTwo  ("draw two"),
		DrawFour ("draw four"),
		Reverse  ("reverse"),
		Skip     ("skip");

		public final String display;

		Special(String display) {
			this.display = display;
		}

		public String toString() {
			return display;
		}
	}

	public final Color color;
	public final Special special;
	public final int number;

	public static final int NO_NUMBER = -1;

	protected UnoCard(Color color, int number, Special special) {
		this.color = color;
		this.number = number;
		this.special = special;

		if(special == Special.DrawFour && color != Color.Wild) {
			throw new IllegalArgumentException();
		} else if(color == Color.Wild && number != NO_NUMBER) {
			// construct wild cards with the other constructor!
			// also, they can't have a number!
			throw new IllegalArgumentException();
		} else if(number < -1 || number > 9) {
			// no numbers > 9 in an uno deck
			throw new IllegalArgumentException();
		}
	}

	public UnoCard(Color color, int number) {
		this(color, number, Special.None);
	}

	public UnoCard(Color color, Special special) {
		this(color, NO_NUMBER, special);
	}

	public boolean canBePlacedOn(UnoCard other){
		if(other == null) {
			return false;
		} else if(color == other.color || color == Color.Wild
				|| other.color == Color.Wild) {
			// same color is OK, wild can be placed on anything
			return true;
		} else if(special != Special.None) {
			// number == NO_NUMBER, same special is OK (diff colors)
			return special == other.special;
		} else {
			// same number is OK
			return number == other.number;
		}
	}

	/**
	 * examples: "Green draw two", "Wild draw four", "Blue skip", "Red 9"
	 */
	public String toString(){
		String ret = color + " ";
		if(special != Special.None) {
			ret += special;
		} else if(number != NO_NUMBER) {
			ret += number;
		}
		return ret;
	}

	public boolean equals(Object o){
		if(o instanceof UnoCard) {
			UnoCard card = (UnoCard) o;
			return card.color == color
				&& card.special == special
				&& card.number == number;
		} else {
			return false;
		}
	}

	public int compareTo(UnoCard other) {
		int ret = color.compareTo(other.color);
		if(ret != 0) { return ret; }
		ret = special.compareTo(other.special);
		if(ret != 0) { return ret; }
		return number - other.number;
	}
}
