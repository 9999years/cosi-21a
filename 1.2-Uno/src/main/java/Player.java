public class Player {
	public final String name;
	protected Player nextPlayer;
	protected Player prevPlayer;
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
		return name;
	}


}
