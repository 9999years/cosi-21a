import java.lang.Comparable;

public class Player implements Comparable<Player> {
	public final String name;
	protected DoublyLinkedOrderedList<UnoCard> hand;

	public Player(String name){
		this.name = name;
	}

	public void addToHand(UnoCard c){
		hand.insert(c);
	}

	public void removeFromHand(int index){
		//you have to implement this
	}

	public boolean winner(){
		return hand.isEmpty();
	}

	public String toString() {
		return name;
	}
	
	public int compareTo(Player p) {
		return name.compareTo(p.name);
	}
}
