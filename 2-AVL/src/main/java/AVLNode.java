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

import java.util.Objects;

public class AVLNode<T> {
	private T data;
	private double value;
	private AVLNode<T> parent;
	private AVLNode<T> leftChild;
	private AVLNode<T> rightChild;
	// ???
	private int rightWeight;
	private int balanceFactor;

	public AVLNode(T data, double value) {
		Objects.requireNonNull(data);
		this.data = data;
		this.value = value;
	}

	protected boolean hasLeftChild() {
		return leftChild != null;
	}

	protected boolean hasRightChild() {
		return rightChild != null;
	}

	protected boolean hasParent() {
		return parent != null;
	}

	protected AVLNode<T> getLeftChild() {
		return leftChild;
	}

	protected AVLNode<T> getRightChild() {
		return rightChild;
	}

	protected void setLeftChild(AVLNode<T> n) {
		Objects.requireNonNull(n);
		leftChild = n;
		n.parent = this;
	}

	protected void setRightChild(AVLNode<T> n) {
		Objects.requireNonNull(n);
		rightChild = n;
		n.parent = this;
	}

	protected AVLNode<T> getParent() {
		return parent;
	}

	protected boolean isRightChild() {
		return parent.rightChild == this;
	}

	protected boolean isLeftChild() {
		return parent.leftChild == this;
	}

	public T getData() {
		return data;
	}

	public boolean isLeaf() {
		return leftChild == null && rightChild == null;
	}

	/**
	 * a lower bound on the tree's height; actual height of bottom leaves may be
	 * greater than this by 1
	 * <p>
	 * O(log n) time because there's no way in hell i'm adding another field to
	 * this bloated class
	 */
	public int approximateHeight() {
		return 1 + leftChild.approximateHeight();
	}

	/**
	 * the tree's height; O(n) time
	 */
	public int height() {
		return isLeaf()
				? 0
				: Math.max(leftChild.height(), rightChild.height());
	}

	public int getBalanceFactor() {
		// TODO actually implement the BF
		if(isLeaf()) {
			return 0
		}
		int leftHeight = 0;
		if(hasLeftChild()) {
			leftHeight = leftChild.height();
		}
		if(hasRightChild()) {
			leftHeight -= rightChild.height();
		}
		return isLeaf()
			? 0
			: leftChild.height() - rightChild.height();
	}

	public AVLNode<T> insert(AVLNode<T> n) {
		if (n.value < value) {
			if (hasLeftChild()) {
				// recurse
				leftChild.insert(n);
			} else {
				// no left child
				setLeftChild(n);
			}
		} else {
			// newValue >= value
			if (hasRightChild()) {
				// recurse
				rightChild.insert(n);
			} else {
				// no left child
				setRightChild(n);
			}
		}
		return this;
	}

	/**
	 * This should return the new root of the tree
	 * make sure to update the balance factor and right weight
	 * and use rotations to maintain AVL condition
	 */
	public AVLNode<T> insert(T data, double value) {
		return insert(new AVLNode<T>(data, value));
	}

	/**
	 * This should return the new root of the tree
	 * remember to update the right weight
	 */
	public AVLNode<T> delete(double value) {
		//TODO: write standard vanilla BST delete method
		//Extra Credit: use rotations to maintain the AVL condition
		return null;
	}

	/**
	 * remember to maintain rightWeight
	 *
	 * @return true if the tree's root is changed
	 * @throws IllegalStateException if this is not a left child
	 */
	private boolean rotateRight() {
		Parameters.checkState(isLeftChild());

		/* cases to consider:
		 * 1. pivot is root
		 * 2. par is root
		 * 3. par is null
		 * 4. par.rightchild is null
		 */
		boolean ret = false;
		AVLNode<T> left = leftChild;

		// move rightChild of left to be this node's new
		// left child
		leftChild = left.rightChild;
		// move left into this node's position
		if(left.rightChild != null) {
			left.rightChild.parent = this;
		}
		left.parent = parent;
		if(parent == null) {
			 ret = true;
		} else if(this == parent.rightChild) {
			parent.rightChild = left;
		} else {
			parent.leftChild = left;
		}
		// move this node to become the rightChild of left
		left.rightChild = this;
		parent = left;
		return ret;
	}

	/**
	 * remember to maintain rightWeight
	 */
	private void rotateLeft() {
		//TODO: rotateLeft method
	}

	/**
	 * this should return the tree of names with parentheses separating subtrees
	 * eg "((bob)alice(bill))"
	 * <p>
	 * implementation note: this allocates and immediately destroys a whole
	 * BUNCHA strings
	 */
	public String treeString() {
		String ret = "";
		if (leftChild != null) {
			ret += leftChild.toString();
		}
		ret += Objects.toString(data);
		if (rightChild != null) {
			ret += rightChild.toString();
		}
		return ret;
	}

	/**
	 * @return "value -> data" (quotes are included in result string)
	 */
	private String dotNodeString() {
		return "\"" + value + " &#x2192; " + data + "\"";
	}

	/**
	 * returns the "unwrapped" (no `digraph { ... }` boilerplate) GraphViz dot
	 * representation of this node
	 * <p>
	 * headline coming soon to a paper near you: rebecca turner arrested for
	 * Ternary Crimes
	 */
	private String dotStringNoWrapper() {
		StringBuilder sb = new StringBuilder();
		if(hasLeftChild()) {
			sb.append(dotNodeString()).append(" -> ")
					.append(leftChild.dotNodeString()).append(";\n");
		}
		if(hasRightChild()) {
			sb.append(dotNodeString()).append(" -> ")
					.append(rightChild.dotNodeString()).append(";\n");
		}
		if(hasLeftChild()) {
			sb.append(leftChild.dotStringNoWrapper());
		}
		if(hasRightChild()) {
			sb.append(rightChild.dotStringNoWrapper());
		}
		return sb.toString();
	}

	/**
	 * creates a GraphViz dot representation of this tree
	 */
	public String toStringDot() {
		return "digraph {\nnode[shape=box];\n"
				+ dotStringNoWrapper()
				+ "}\n";
	}

	/**
	 * uses a fairly large epsilon for comparing `data` fields
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AVLNode)) {
			return false;
		}
		AVLNode<?> n = (AVLNode<?>) o;
		return Objects.equals(data, n.data)
				&& Doubles.equals(value, n.value, 0.000001);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, value);
	}

	@Override
	public String toString() {
		return "AVLNode[" + value + " -> " + data + "]";
	}
}
