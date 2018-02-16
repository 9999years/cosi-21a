
public class Player {
	private String name;
	private Player nextPlayer=null;
	private Player prevPlayer=null;
	// private SinglyLinkedList<UnoCard> hand; 
	// you need to add that ^
	
	public Player(String name){
		this.name = name;
	}
	
	public void addToHand(UnoCard c){
		//you have to implement this
	}
	
	public void removeFromHand(int index){
		//you have to implement this
	}
	
	public boolean winner(){
		// return true when your hand has nothing left. 
		// you have to implement this
		return false;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public Player getPrevPlayer() {
		return prevPlayer;
	}

	public void setPrevPlayer(Player prevPlayer) {
		this.prevPlayer = prevPlayer;
	}

	public String toString() {
		return "Player [name=" + name + "]";
	}
	
	
}
