import java.util.Scanner;

public class UnoGame {
	public static final String NAME_QUERY = "What's your name? ";
	public static final String INTRO = "Welcome to Uno!\nPlease enter the player names --- one per line, and a blank line to stop.";
	public static final String LESS_THAN_2_PLAYERS = "You need at least 2 players for Uno! Add some more players!";
	public static final int MIN_PLAYERS_IN_CIRCLE = 2;
	public static final int MAX_PLAYERS_IN_CIRCLE = 5;

	public static final int UNO_STARTING_HAND_SIZE = 7;

	public static Scanner in = new Scanner(System.in);
	public static PlayerCircle players = new PlayerCircle();
	public static Queue<Player> extraPlayers = new Queue<Player>();

	public static String getName() {
		System.out.print(NAME_QUERY);
		return in.nextLine();
	}

	/**
	 * we're mucking with the fields anyways so this operates via
	 * side-effects. sorry!
	 */
	public static void getPlayers() {
		// get names and add them to the circle while the name exists
		// & there's <= 5 players in the circle
		// strict less than because we already call getName outside the
		// loop
		String name = getName();
		while(!name.isEmpty() &&
				players.getSize() < MAX_PLAYERS_IN_CIRCLE) {
			players.addToCircle(name);
			name = getName();
		}

		// < 2 players? we need to get more!
		if(players.getSize() < MIN_PLAYERS_IN_CIRCLE) {
			System.out.println(LESS_THAN_2_PLAYERS);
			getPlayers();
		}

		// > 5 players; add to the aux. queue
		// we already have another name in the `name` variable! if it's
		// empty we never enter this loop
		while(!name.isEmpty()) {
			extraPlayers.enqueue(new Player(name));
			name = getName();
		}
	}

	public static void main(String[] args) {
		System.out.println(INTRO);
		getPlayers();
		System.out.println("Players: " + players);
		System.out.println("Backups: " + extraPlayers);
	}
}
