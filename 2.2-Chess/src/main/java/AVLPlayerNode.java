/**
 * Your code goes in this file
 * fill in the empty methods to allow for the required
 * operations. You can add any fields or methods you want
 * to help in your implementations.
 *
 * @author Solomon Garber, Guillermo Narvaez
 */
public class AVLPlayerNode extends AVLNode<Player> {
	private int rank;

	public AVLPlayerNode(Player data, double value) {
		super(data, value);
	}

	//This should return the new root of the tree
	//make sure to update the balance factor and right weight
	//and use rotations to maintain AVL condition
	@Override
	public AVLPlayerNode insert(Player newGuy, double value) {
		return (AVLPlayerNode) super.insert(newGuy, value);
	}

	//This should return the new root of the tree
	//remember to update the right weight
	@Override
	public AVLPlayerNode delete(double value) {
		return (AVLPlayerNode) super.delete(value);
	}

	//this should return the Player object stored in the node with this.value == value
	public Player getPlayer(double value) {
		return get(value).getData();
	}

	//this should return the rank of the node with this.value == value
	public int getRank(double value) {
		return rank;
	}

	//this should return the tree of names with parentheses separating subtrees
	//eg "((bob)alice(bill))"
	@Override
	public String treeString() {
		return super.treeString();
	}

	//this should return a formatted scoreboard in descending order of value
	//see example printout in the pdf for the command L
	public String scoreboard() {
		// TODO
		return "";
	}
}
