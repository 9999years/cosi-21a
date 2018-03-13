/*
 * Copyright 2018 Rebecca Turner (rebeccaturner@brandeis.edu)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Your code goes in this file
 * fill in the empty methods to allow for the required
 * operations. You can add any fields or methods you want
 * to help in your implementations.
 */
//add leftheight!!!!
public class RAVLNode<T> {
	//I WANT TO ADD A SEARCH METHOD I JUST HAVE TO FIGURE OUT WHAT I WANT IT TO RETURN
	private T data;
	private double value;
	private RAVLNode<T> parent;
	private RAVLNode<T> leftChild;
	private RAVLNode<T> rightChild;
	private int rightWeight;
	private int leftWeight;
	private int balanceFactor;

	public RAVLNode(T data, double value) {
		this.data = data;
		this.value = value;
		// REBECCA: everything from here down is unnecessary
		this.parent = null;
		this.leftChild = null;
		this.rightChild = null;
		this.rightWeight = 0;
		this.balanceFactor = 0;
		this.leftWeight = 0;
	}

	protected RAVLNode<T> getParent() {
		return parent;
	}

	protected RAVLNode<T> getLeftChild() {
		return leftChild;
	}

	protected RAVLNode<T> getRightChild() {
		return rightChild;
	}

	/**
	 * This should return the new root of the tree
	 * make sure to update the balance factor and right weight
	 * and use rotations to maintain AVL condition
	 */
	public RAVLNode<T> insert(T newData, double value) {//what should we do with duplicates
		RAVLNode<T> inserter = new RAVLNode<T>(newData, value);
		RAVLNode<T> root = this;
		RAVLNode<T> comparer = this;
		while (comparer != null) {
			if (value > comparer.value) {
				comparer.rightWeight++;
				if (comparer.rightChild == null) {
					inserter.parent = comparer;
					comparer.rightChild = inserter;
					System.out.println("Inserting " + inserter.data + " at " + comparer.data);
					// REBECCA: this seems redundant; if you explicitly break the loop, why use a while() with some conditional that's not just `true` in the first place?
					break;
				} else {
					comparer = comparer.rightChild;
				}
			} else {
				comparer.leftWeight++;
				if (comparer.leftChild == null) {
					inserter.parent = comparer;
					comparer.leftChild = inserter;
					System.out.println("Going to left");
					System.out.println("Inserting " + inserter.data + " at " + comparer.data);
					break;
				} else {
					comparer = comparer.leftChild;
				}
			}

		}
		comparer = inserter;
		while (comparer != null) {
			if (checkRotation(comparer, inserter)) {
				break;
			} else {
				comparer = comparer.parent;
			}
		}
		//System.out.println(root.data);
	/*	if(root.rightChild!= null){
			System.out.println(root.rightChild.data);
		}
		if(root.leftChild != null){
			System.out.println(root.leftChild.data);
		}*/

		//MAKE SURE YOU REASSIGN ROOT AND CHANGE RIGHT BALANCE AND THE BALANCE FACTOR
		return root;
	}

	// REBECCA: this should probably be private or at least protected
	public boolean checkRotation(RAVLNode<T> comparer, RAVLNode<T> inserter) {
		RAVLNode<T> root = comparer;
		System.out.println("COMPARER: " + comparer.data + "BF: " + comparer.balanceFactor);
		System.out.println("INSERTER: " + inserter.data + "BF: " + inserter.balanceFactor);
		if (comparer.leftWeight - comparer.rightWeight > 1) {
			RAVLNode<T> temp = comparer;
			while (temp != null && !temp.data.equals(inserter.data)) {//will value always correspond to independent data values?
				System.out.println("COMPARER: " + comparer.data + "BF: " + comparer.balanceFactor);
				System.out.println("INSERTER: " + inserter.data + "BF: " + inserter.balanceFactor);
				if (temp.value < inserter.value) {
					temp.rotateLeft();
					break;
				} else {
					temp = temp.leftChild;
				}
			}
			comparer.rotateRight();
			return true;
		} else if (comparer.leftWeight - comparer.rightWeight < -1) {
			RAVLNode<T> temp = comparer;
			while (temp != null && !temp.data.equals(inserter.data)) {//will value always correspond to independent data values?
				System.out.println("TEMP: " + temp.data + temp.leftWeight + " " + temp.rightWeight);
				if (temp.value > inserter.value) {
					temp.rotateRight();
					System.out.println("TEMP: " + temp.data + temp.leftWeight + " " + temp.rightWeight);
					break;
				} else {
					temp = temp.rightChild;
				}
			}
			System.out.println("THIS IS COMPARER BEFORE I ROTATE: " + comparer.data);
			comparer.rotateLeft();
			System.out.println("TEMP: " + temp.data + temp.leftWeight + " " + temp.rightWeight);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This should return the new root of the tree
	 * remember to update the right weight
	 */
	public RAVLNode<T> delete(double value) {
		RAVLNode<T> comparer = this;
		RAVLNode<T> parent = this;
		if (comparer == null) {
			return null;
		} else {
			while (comparer != null && comparer.value != value) {
				if (value > comparer.value) {
					comparer = comparer.rightChild;
					parent = comparer.parent;
				} else if (value < comparer.value) {
					comparer = comparer.leftChild;
					parent = comparer.parent;
				}
			}
			if (comparer == null) {
				return null;
			} else {
				if (comparer.rightChild == null && comparer.leftChild == null) {
					if (parent.leftChild.data == comparer.data) {
						parent.leftChild = null;
					} else {
						parent.rightChild = null;
					}
				} else if (comparer.leftChild == null) {
					if (parent.leftChild.data == comparer.data) {
						parent.leftChild = comparer.rightChild;
					} else {
						parent.rightChild = comparer.rightChild;
					}
				} else if (comparer.rightChild == null) {
					if (parent.leftChild.data == comparer.data) {
						parent.leftChild = comparer.leftChild;
					} else {
						parent.rightChild = comparer.leftChild;
					}
				} else {
					//remember how to delete with two children

				}
			}
		}
		return comparer;
	}

	//remember to maintain rightWeight
	private void rotateRight() {
		System.out.println("Rotating right at : " + this.data);
		RAVLNode<T> temp = null;
		if (this.leftChild != null) {
			temp = this.leftChild.rightChild;
		}
		if (this.parent != null && this.parent.rightChild.data == this.data) {
			this.parent.rightChild = this.leftChild;
			System.out.println("PARENT: " + this.parent.data + "CHILD: " + this.parent.rightChild);
		} else if (this.parent != null) {
			this.parent.leftChild = this.leftChild;
			System.out.println("PARENT: " + this.parent.data + "CHILD: " + this.parent.leftChild.data);
		}
		this.leftChild.parent = this.parent;
		this.leftChild.rightChild = this;
		this.parent = this.leftChild;
		this.leftChild = temp;
		System.out.println(this.data + " " + this.parent.data);
		if (temp != null)
			this.leftWeight += (temp.leftWeight - temp.rightWeight) - 1;
		else {
			this.leftWeight--;
		}
	}

	//remember to maintain rightWeight
	private void rotateLeft() {
		System.out.println("THIS: " + this.data);
		RAVLNode<T> temp = null;
		if (this.leftChild != null) {
			temp = this.leftChild.rightChild;
		}
		// REBECCA: this.parent.rightChild.data might be an NPE
		if (this.parent != null && this.parent.rightChild.data == this.data) {

			this.parent.rightChild = this.rightChild;
			System.out.println("PARENT: " + this.parent.data + "CHILD: " + this.parent.rightChild.data);
		} else if (this.parent != null) {
			this.parent.leftChild = this.rightChild;
			System.out.println("PARENT: " + this.parent.data + "PARENTS PARENT:  " + this.parent.data);
		}
		// REBECCA: NPE
		this.rightChild.parent = this.parent;
		// REBECCA: NPE
		this.rightChild.leftChild = this;
		this.parent = this.rightChild;
		this.rightChild = temp;
		// REBECCA: add braces; braceless if statements are a big source of errors
		if (temp != null)
			this.rightWeight += (temp.leftWeight - temp.rightWeight) - 1;
		else {
			this.rightWeight--;
		}
		System.out.println("This" + this.data + " Parent " + this.parent.data + "Parent child " + this.parent.rightChild.data);
		//System.out.println("THIS: " + this.data + "RIGHT: " + this.rightChild.data/* + "LEFT: " + this.leftChild.data*/);
	}

	/**
	 * this should return the data object stored in the node with this.value == value
	 */
	public T getData(double value) {
		RAVLNode<T> comparer = this;
		while (comparer != null && comparer.value != value) {
			if (value > comparer.value) {
				System.out.println("going to right child");
				comparer = comparer.rightChild;
			} else {

				comparer = comparer.leftChild;
			}
		}
		if (comparer == null) {
			return null;
		} else {
			System.out.println("Returning data");
			return comparer.data;
		}
	}

	/**
	 * this should return the tree of names with parentheses separating subtrees
	 * eg "((bob)alice(bill))"
	 */
	public String treeString() {
		return inOrderTraversal(this, "");
	}

	public String inOrderTraversal(RAVLNode<T> n, String s) {
		s += "( ";
		if (n.leftChild != null) {
			System.out.println("LEFT OF " + n.data);
			s = inOrderTraversal(n.leftChild, s);
		}
		s += n.data.toString();
		System.out.println(n.data);
		if (n.rightChild != null) {
			System.out.println("RIGHT OF " + n.data);
			s = inOrderTraversal(n.rightChild, s);
		}
		s += " )";
		return s;
	}

}


