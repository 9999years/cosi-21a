import java.util.Scanner;

public class UnoGame {
	public static final String NAME_QUERY = "What's your name? ";
	public static final String INTRO = "Welcome to Uno!\nPlease enter the player names --- one per line, and a blank line to stop.";
	public static Scanner in = new Scanner(System.in);

	public static String getName() {
		System.out.print(NAME_QUERY);
		return in.nextLine();
	}

	public static void main(String[] args) {
		System.out.println(INTRO);
		while(true) {
		}
	}
}
