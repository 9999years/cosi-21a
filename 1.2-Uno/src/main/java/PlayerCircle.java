public class PlayerCircle {
	CircularOrderedList<Player> players = new CircularOrderedList();

	public Player getFirstPlayer() {
		return players.getFront();
	}

	public void addToCircle(Player p) {
		players.add(p);
	}

	public void addToCircle(String name) {
		players.add(new Player(name));
	}

	public void swapLoser(Player newPlayer) {
	}

	public int getSize() {
		return players.size();
	}

	public String toString() {
		return players.toString();
	}
}
