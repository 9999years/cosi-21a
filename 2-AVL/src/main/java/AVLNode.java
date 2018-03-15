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

	/**
	 * O(1)
	 */
	public AVLNode(T data, double value) {
		Objects.requireNonNull(data);
		this.data = data;
		this.value = value;
	}

	/**
	 * O(1)
	 */
	protected boolean hasLeftChild() {
		return leftChild != null;
	}

	/**
	 * O(1)
	 */
	protected boolean hasRightChild() {
		return rightChild != null;
	}

	/**
	 * O(1)
	 */
	protected boolean hasParent() {
		return parent != null;
	}

	/**
	 * O(1)
	 */
	protected AVLNode<T> getLeftChild() {
		return leftChild;
	}

	/**
	 * O(1)
	 */
	protected AVLNode<T> getRightChild() {
		return rightChild;
	}

	/**
	 * O(1)
	 */
	protected void setLeftChild(AVLNode<T> n) {
		leftChild = n;
		if (n != null) {
			n.parent = this;
		}
	}

	/**
	 * O(1)
	 */
	protected void setRightChild(AVLNode<T> n) {
		rightChild = n;
		if (n != null) {
			n.parent = this;
		}
	}

	/**
	 * O(1)
	 */
	protected void setParent(AVLNode<T> newParent) {
		if (newParent != null) {
			if (isLeftChild()) {
				newParent.leftChild = this;
			} else {
				newParent.rightChild = this;
			}
		}
		if (hasParent()) {
			if (isLeftChild()) {
				parent.leftChild = null;
			} else {
				parent.rightChild = null;
			}
		}
		parent = newParent;
	}

	/**
	 * whereas setParent ensures the parent points to the correct child (e.g. if
	 * this is a left child parent.leftChild is set to this), this sets another
	 * node's parent to this node's parent while ensuring this node's
	 * parent/child directionality (rather than the new node's)
	 *
	 * O(1)
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

	/**
	 * O(1)
	 */
	protected AVLNode<T> getParent() {
		return parent;
	}

	/**
	 * O(1)
	 */
	protected boolean isRoot() {
		return parent == null;
	}

	/**
	 * O(log n)
	 */
	public AVLNode<T> getRoot() {
		if (isRoot()) {
			return this;
		} else {
			return parent.getRoot();
		}
	}

	/**
	 * O(1)
	 */
	protected boolean isRightChild() {
		return !isRoot() && parent.rightChild == this;
	}

	/**
	 * O(1)
	 */
	protected boolean isLeftChild() {
		return !isRoot() && parent.leftChild == this;
	}

	/**
	 * O(1)
	 */
	public T getData() {
		return data;
	}

	/**
	 * O(1)
	 */
	public double getValue() {
		return value;
	}

	/**
	 * O(1)
	 */
	public boolean isLeaf() {
		return leftChild == null && rightChild == null;
	}

	/**
	 * O(1)
	 */
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

	/**
	 * O(1)
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * O(log n)
	 */
	private AVLNode<T> min() {
		return hasLeftChild() ? leftChild.min() : this;
	}

	/**
	 * O(log n)
	 */
	private AVLNode<T> max() {
		return hasRightChild() ? rightChild.max() : this;
	}

	/**
	 * O(log n)
	 */
	protected AVLNode<T> successor() {
		return hasRightChild() ? rightChild.min() : null;
	}

	/**
	 * O(log n)
	 */
	protected AVLNode<T> predecessor() {
		return hasLeftChild() ? leftChild.max() : null;
	}

	/**
	 * O(log n)
	 */
	private int getLeftHeight() {
		return hasLeftChild() ? leftChild.getHeight() : 0;
	}

	/**
	 * O(log n)
	 */
	private int getRightHeight() {
		return hasRightChild() ? rightChild.getHeight() : 0;
	}

	/**
	 * O(1)
	 */
	protected AVLNode<T> higherChild() {
		return getLeftHeight() > getRightHeight()
			? leftChild : rightChild;
	}

	/**
	 * O(log n)
	 */
	private void updateHeightsTo(AVLNode<T> stopAt) {
		if (this == stopAt) {
			return;
		}
		updateHeight();
		if (hasParent()) {
			parent.updateHeightsTo(stopAt);
		}
	}

	/**
	 * O(1)
	 */
	private void updateHeight() {
		height = 1 + Math.max(getLeftHeight(), getRightHeight());
	}

	/**
	 * O(1)
	 */
	public boolean isUnbalanced() {
		int balanceFactor = getBalanceFactor();
		return balanceFactor < -1 || balanceFactor > 1;
	}

	/**
	 * O(1)
	 */
	private boolean isLeftHeavy() {
		return getBalanceFactor() > 0;
	}

	/**
	 * O(1)
	 */
	private boolean isRightHeavy() {
		return getBalanceFactor() < 0;
	}

	/**
	 * O(1)
	 */
	private boolean isLeftUnbalanced() {
		return getBalanceFactor() > 1;
	}

	/**
	 * O(1)
	 */
	private boolean isRightUnbalanced() {
		return getBalanceFactor() < -1;
	}

	/**
	 * O(log n)
	 */
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
	 *
	 * O(log n)
	 */
	public AVLNode<T> insert(T data, double value) {
		return insert(new AVLNode<>(data, value));
	}

	/**
	 * deletes this node from the tree and returns the tree's new root
	 *
	 * O(1)
	 */
	private AVLNode<T> standardDelete() {
		AVLNode<T> newThis = this;
		if (hasLeftChild() != hasRightChild()) {
			// one child
			newThis = hasLeftChild() ? leftChild : rightChild;
			swapThisInParent(newThis);
		} else if (!hasLeftChild() && !hasRightChild()) {
			// https://www.youtube.com/watch?v=Vm-NW1RwPY8
			if (hasParent()) {
				newThis = parent;
				setParent(null);
			} else {
				// no parent & no children means we're deleting the last node
				// in a 1-node tree
				newThis = null;
			}
		} else {
			// 2 children
			newThis = successor();
			// delete successor (i.e. cut it's parent links)
			newThis.delete(this);
			swapThisInParent(newThis);
			// patch successor back in
			newThis.setLeftChild(leftChild);
			newThis.setRightChild(rightChild);
		}
		return newThis;
	}

	/**
	 * O(log n)
	 */
	private AVLNode<T> delete(AVLNode<T> root) {
		AVLNode<T> newThis = standardDelete();
		return newThis == null ? null : newThis.rebalance(root);
	}

	/**
	 * This should return the new root of the tree
	 * remember to update the right weight
	 *
	 * O(log n)
	 */
	public AVLNode<T> delete(double value) {
		AVLNode<T> n = get(value);
		if (n == null) {
			// not found
			return this;
		} else {
			return n.delete(this);
		}
	}

	/**
	 * returns the new root of the tree rooted in `root`, i.e. NOT the new root of
	 * this subtree
	 *
	 * O(log n)
	 *
	 * @return null if this isnt a member of the tree rooted in `root`
	 */
	private AVLNode<T> rebalance(AVLNode<T> root) {
		AVLNode<T> newThis = this;
		updateHeight();

		if (isLeftUnbalanced()) {
			// left case
			if (leftChild.isRightHeavy()) {
				// bf < 0
				// left right
				leftChild.rotateLeft();
				newThis = rotateRight();
			} else {
				// left left
				newThis = rotateRight();
			}
		} else if(isRightUnbalanced()) {
			if (rightChild.isLeftHeavy()) {
				// right bf >= 1
				// right left
				rightChild.rotateRight();
				newThis = rotateLeft();
			} else {
				// right bf <= 0
				// right right
				newThis = rotateLeft();
			}
		}

		AVLNode<T> newParentRoot = null;
		if (hasParent()) {
			newParentRoot = parent.rebalance(root);
		}

		if (this == root || isRoot()) {
			return newThis;
		} else {
			return newParentRoot;
		}
	}

	/**
	 * searches for a value in the tree such that |node.value - value| < EPSILON
	 * O(log n)
	 * @param value the value to search for
	 * @return null if value not found in the tree; the node otherwise
	 */
	public AVLNode<T> get(double value) {
		if(Doubles.equals(this.value, value, EPSILON)) {
			return this;
		} else if (hasLeftChild() && value <= this.value) {
			return leftChild.get(value);
		} else if (hasRightChild() && value >= this.value) {
			return rightChild.get(value);
		} else {
			return null;
		}
	}

	/**
	 * can only be called if hasLeftChild()
	 * <p>
	 * updates subtree heights but updating the heights of parent nodes i.e.
	 * above root / pivot is on you
	 *
	 * O(1)
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
	 *
	 * O(1)
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
	 *
	 * O(n)
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
	 *
	 * O(1)
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

	/**
	 * O(1)
	 */
	@Override
	public int hashCode() {
		return Objects.hash(data, value);
	}

	/**
	 * O(1)
	 */
	@Override
	public String toString() {
		return "AVLNode[" + value + " -> " + data
				+ ", BF=" + getBalanceFactor() + ", Height=" + height + "]";
	}
}
