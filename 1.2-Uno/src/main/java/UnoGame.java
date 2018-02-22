import java.util.Scanner;
import java.util.Iterator;

public class UnoGame {
	public static final String NAME_QUERY = "What's your name? ";
	public static final String INTRO = "Welcome to Uno!\nPlease enter the player names --- one per line, and a blank line to stop.";
	public static final String LESS_THAN_2_PLAYERS = "You need at least 2 players for Uno! Add some more players!";
	public static final String CHOOSE_CARD = "Which card would you like to play? (Press enter for first choice) ";
	public static final String BAD_CARD_CHOICE = "You don't have that card or misspelled its name! Try again?";
	public static final String NO_PLAYABLE_CARDS = "You don't have any cards to play! Drawing a card and skipping your turn.";
	public static final int MIN_PLAYERS_IN_CIRCLE = 2;
	public static final int MAX_PLAYERS_IN_CIRCLE = 5;

	public static final int UNO_STARTING_HAND_SIZE = 7;

	protected enum Action {
		None, Skip, Reverse, Win
	}

	protected static Scanner in = new Scanner(System.in);
	protected static PlayerCircle players = new PlayerCircle();
	protected static Queue<Player> extraPlayers = new Queue<Player>();
	protected static UnoDeck deck;
	protected static int turns;

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

	public static String playing() {
		String ret = Iterables.englishToString(players)
			+ " are playing";
		if(!extraPlayers.isEmpty()) {
			ret += " and "
				+ Iterables.englishToString(extraPlayers)
				+ " are waiting to play.";
		} else {
			ret += ".";
		}
		return ret;
	}

	/**
	 * step 4.b and 4.c
	 */
	public static UnoCard getCard(Player p) {
		UnoCard ret = null;
		Hand playable = p.hand.playable(deck.getLastDiscarded());
		if(playable.isEmpty()) {
			System.out.println(NO_PLAYABLE_CARDS);
			System.out.println("You picked up a " + p.hand.draw(deck));
			return null;
		}
		while(true) {
			System.out.println("You can play any of " + playable);
			System.out.print(CHOOSE_CARD);
			String choice = in.nextLine();

			if(choice.isEmpty()) {
				return playable.first();
			} else {
				ret = p.hand.get(choice);
			}

			if(ret == null) {
				System.out.println(BAD_CARD_CHOICE);
			} else {
				break;
			}
		}
		return ret;
	}

	/**
	 * processes step 4 from p. 2 of the spec
	 */
	public static Action play(Player p) {
		System.out.println("It is " + p + "'s turn! They have "
			+ p.handSize() + " cards left");
		// pick up any cards from a previous draw 2 or draw 4 card
		// 4.a
		p.hand.pickUpOwed(deck);
		// 4.b and 4.c
		UnoCard card = getCard(p);
		Action ret = Action.None;
		if(card != null) {
			// 4.d
			deck.discardCard(p.hand.remove(card));
			// state to return; actions for the next player
			if(card.special == UnoCard.Special.Skip) {
				ret = Action.Skip;
			} else if(card.special == UnoCard.Special.Reverse) {
				ret = Action.Reverse;
			}
		}
		if(p.winner()) {
			System.out.println(p + " won!");
			return Action.Win;
		}
		return ret;
	}

	public static Action processTurn() {
		Iterator<Player> ps = players.iterator();
		while(ps.hasNext()) {
			Player p = ps.next();
			Action act = p.play();
			if(act == Action.win) {
				return Action.Win;
			}
			if(act == Action.Skip) {
				p.next();
			} else if(act == Action.Reverse) {
				// reverse order
			}
		}
		return Action.None;
	}

	public static void processRound() {
		turns = 0;
		deck = new UnoDeck();

		// step 2
		System.out.println("The hands are as follows:");
		for(Player p : players) {
			p.drawHand(deck);
			System.out.println(p + ": " + p.handString());
		}
		System.out.println();
		//end step 2

		// step 3
		deck.discardCard();
		System.out.println("The top card is a" + deck.lastDiscarded());
		// end step 3

		while(true) {
			turns++;
			processTurn();
		}
	}

	public static void setup() {
		// step 1
		System.out.println(INTRO);
		getPlayers();
		System.out.println(playing());
		System.out.println();
		// end step 1
	}

	public static void main(String[] args) {
		setup();
	}
}
