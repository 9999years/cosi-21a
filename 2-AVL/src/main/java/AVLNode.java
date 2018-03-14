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
	public static final double EPSILON = 1e-10;

	private T data;
	private double value;
	private AVLNode<T> parent;
	private AVLNode<T> leftChild;
	private AVLNode<T> rightChild;
	/**
	 * this replaces the balanceFactor field (which can be constructed from the
	 * heights of the children) as well as the rightWeight field (which seemed
	 * superfluous)
	 * <p>
	 * a leaf node has a height of 1, analagous to the 'length' of an array
	 */
	private int height = 1;

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
		if (hasLeftChild()) {
			leftChild.parent = null;
		}
		leftChild = n;
		if (n != null) {
			n.parent = this;
		}
	}

	protected void setRightChild(AVLNode<T> n) {
		if (hasRightChild()) {
			rightChild.parent = null;
		}
		rightChild = n;
		if (n != null) {
			n.parent = this;
		}
	}

	protected void setParent(AVLNode<T> newParent) {
		if (newParent != null) {
			if (isLeftChild()) {
				newParent.leftChild = this;
			} else {
				newParent.rightChild = this;
			}
		}
		parent = newParent;
	}

	/**
	 * whereas setParent ensures the parent points to the correct child (e.g. if
	 * this is a left child parent.leftChild is set to this), this sets another
	 * node's parent to this node's parent while ensuring this node's
	 * parent/child directionality (rather than the new node's)
	 */
	protected void swapThisInParent(AVLNode<T> newThis) {
		if (parent != null) {
			if (isLeftChild()) {
				parent.leftChild = newThis;
			} else {
				parent.rightChild = newThis;
			}
		}
		if (newThis != null) {
			newThis.parent = parent;
		}
	}

	protected AVLNode<T> getParent() {
		return parent;
	}

	protected boolean isRoot() {
		return parent == null;
	}

	public AVLNode<T> getRoot() {
		AVLNode<T> root = this;
		while (!root.isRoot()) {
			root = root.parent;
		}
		return root;
	}

	protected boolean isRightChild() {
		return !isRoot() && parent.rightChild == this;
	}

	protected boolean isLeftChild() {
		return !isRoot() && parent.leftChild == this;
	}

	public T getData() {
		return data;
	}

	public double getValue() {
		return value;
	}

	public boolean isLeaf() {
		return leftChild == null && rightChild == null;
	}

	public int getBalanceFactor() {
		int ret = 0;
		if (hasLeftChild()) {
			ret = leftChild.getHeight();
		}
		if (hasRightChild()) {
			ret -= rightChild.getHeight();
		}
		return ret;
	}

	public int getHeight() {
		return height;
	}

	private boolean anyUnbalancedAbove() {
		return isUnbalanced() || (hasParent() && parent.anyUnbalancedAbove());
	}

	private void updateHeight() {
		height = 1 + Math.max(
				hasLeftChild() ? leftChild.getHeight() : 0,
				hasRightChild() ? rightChild.getHeight() : 0);
	}

	public boolean isUnbalanced() {
		int balanceFactor = getBalanceFactor();
		return balanceFactor < -1 || balanceFactor > 1;
	}

	private boolean isLeftHeavy() {
		return getBalanceFactor() > 0;
	}

	private boolean isRightHeavy() {
		return getBalanceFactor() < 0;
	}

	private boolean isLeftUnbalanced() {
		return getBalanceFactor() > 1;
	}

	private boolean isRightUnbalanced() {
		return getBalanceFactor() < -1;
	}

	public AVLNode<T> insert(AVLNode<T> n) {
		boolean onLeft = false;
		if (n.value < value) {
			onLeft = true;
			if (hasLeftChild()) {
				// it only remains an outside case if it was already and we're
				// continuing in the same direction
				leftChild.insert(n);
				// root unchanged
			} else {
				// no left child
				setLeftChild(n);
			}
		} else {
			// newValue >= value
			if (hasRightChild()) {
				rightChild.insert(n);
			} else {
				// no right child
				setRightChild(n);
			}
		}

		updateHeight();
		AVLNode<T> newThis = this;
		if (onLeft && isLeftUnbalanced()) {
			if (n.value < leftChild.value) {
				newThis = rotateRight();
			} else {
				if (leftChild.hasRightChild()) {
					leftChild.rotateLeft();
				}
				newThis = rotateRight();
			}
		} else if(!onLeft && isRightUnbalanced()) {
			// right case
			if (n.value > rightChild.value) {
				newThis = rotateLeft();
			} else {
				if (rightChild.hasLeftChild()) {
					rightChild.rotateRight();
				}
				newThis = rotateLeft();
			}
		}

		return newThis;
	}

	/**
	 * This should return the new root of the tree
	 * make sure to update the balance factor and right weight
	 * and use rotations to maintain AVL condition
	 */
	public AVLNode<T> insert(T data, double value) {
		return insert(new AVLNode<>(data, value));
	}

	/**
	 * This should return the new root of the tree
	 * remember to update the right weight
	 */
	public AVLNode<T> delete(double value) {
		//TODO: write standard vanilla BST delete method
		//Extra Credit: use rotations to maintain the AVL condition
		return this;
	}

	/**
	 * can only be called if hasLeftChild()
	 * <p>
	 * updates subtree heights but updating the heights of parent nodes i.e.
	 * above root / pivot is on you
	 *
	 * @return true if the tree's root is changed
	 * @throws IllegalStateException if this is not a left child
	 */
	private AVLNode<T> rotateRight() {
		//         ?              ?
		//         |              |
		//       root    ==>    pivot
		//      /   \          /    \
		//  pivot   rr       pl    root
		//  /  \    / \     / \    /  \
		// pl  pr  ?  ?    ?  ?   pr  rr
		//
		// root: this
		// unchanged: root  -> rr
		//            pivot -> pl

		Parameters.checkState(hasLeftChild(),
				"right rotate requires a left child!");
		// raises pivot, lowers this
		AVLNode<T> pivot = leftChild;
		setLeftChild(pivot.rightChild);
		// sets parent's child to pivot too;
		// sets pivot's parent to null if it's the new root
		swapThisInParent(pivot);
		// handles setting parent to pivot
		pivot.setRightChild(this);
		updateHeight();
		pivot.updateHeight();
		return pivot;
	}

	/**
	 * can only be called if hasRightChild()
	 */
	private AVLNode<T> rotateLeft() {
		//       ?                    ?
		//       |                    |
		//      root      ==>       pivot
		//     /   \               /    \
		//   rl   pivot          root    pr
		//  /  \   / \           /  \
		// ?   ?  pl  pr        rl  pl
		//
		// root: this
		// unchanged: pivot -> pr
		//            root  -> rl

		Parameters.checkState(hasRightChild(),
				"left rotate requires right child!");
		AVLNode<T> pivot = rightChild;
		setRightChild(pivot.leftChild);
		swapThisInParent(pivot);
		pivot.setLeftChild(this);
		// height of pivot & root have changed (possibly)
		updateHeight();
		pivot.updateHeight();
		return pivot;
	}

	/**
	 * this should return the tree of names with parentheses separating subtrees
	 * eg "((bob)alice(bill))"
	 * <p>
	 * implementation note: this allocates and immediately destroys a whole
	 * BUNCHA strings
	 */
	public String treeString() {
		StringBuilder sb = new StringBuilder("(");
		if (hasLeftChild()) {
			sb.append(leftChild.treeString());
		}
		sb.append(data);
		if (hasRightChild()) {
			sb.append(rightChild.treeString());
		}
		return sb.append(')').toString();
	}

	/**
	 * uses a fairly large epsilon (1e-10) for comparing `data` fields
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AVLNode)) {
			return false;
		}
		AVLNode<?> n = (AVLNode<?>) o;
		return Objects.equals(data, n.data)
				&& Doubles.equals(value, n.value, EPSILON);
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, value);
	}

	@Override
	public String toString() {
		return "AVLNode[" + value + " -> " + data
				+ ", BF=" + getBalanceFactor() + ", Height=" + height + "]";
	}

	/**
	 * a debugging string that includes the value, data, balanceFactor,
	 * rightWeight, parent, and information on both children
	 */
	protected String debug() {
		return this.toString() + ":\n"
				+ "    ID=" + System.identityHashCode(this) + "\n"
				+ "    LC=" + leftChild + "\n"
				+ "    RC=" + rightChild + "\n"
				+ "   PAR=" + parent + "\n";
	}
}
