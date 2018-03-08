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
import java.util.function.Function;

public class AVLNode<T> {
	private enum Decision {
		Left, Right
	}

	private T data;
	private double value;
	private AVLNode<T> parent;
	private AVLNode<T> leftChild;
	private AVLNode<T> rightChild;
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

	protected boolean isRoot() {
		return parent == null;
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
	 * 0 for a leaf
	 */
	public int height() {
		int leftHeight = 0;
		if (hasLeftChild()) {
			leftHeight = leftChild.height();
		}
		if (hasRightChild()) {
			return Math.max(leftHeight, rightChild.height());
		} else {
			return leftHeight;
		}
	}

	private void updateBalanceFactor() {
		balanceFactor = 0;
		if (hasLeftChild()) {
			balanceFactor = 1 + leftChild.balanceFactor;
		}
		if (hasRightChild()) {
			balanceFactor -= 1 + rightChild.balanceFactor;
		}
		if (hasParent()) {
			parent.updateBalanceFactor();
		}
	}

	public int getBalanceFactor() {
		return balanceFactor;
	}

	public AVLNode<T> insert(
			AVLNode<T> n, Decision decision, boolean inside) {
		boolean inserted = false;
		if (n.value < value) {
			if (hasLeftChild()) {
				// recurse
				if(decision != Decision.Left) {
					inside = false;
				}
				leftChild.insert(n, Decision.Left, inside);
			} else {
				// no left child
				setLeftChild(n);
				inserted = true;
			}
		} else {
			// newValue >= value
			if (hasRightChild()) {
				// recurse
				if(decision != Decision.Right) {
					inside = false;
				}
				rightChild.insert(n, Decision.Right, inside);
			} else {
				// no right child
				setRightChild(n);
				inserted = true;
			}
		}

		if (inserted) {
			n.updateBalanceFactor();
			if(inside) {
				if (decision == Decision.Right) {
					// double rotation
					rotateLeft();
					rotateRight();
				} else {
					rotateRight();
					rotateLeft();
				}
			} else {
				// single rotation; outside
				if(isRightChild()) {
					rotateLeft();
				} else {
					rotateRight();
				}
			}
		}
		return this;
	}


	public AVLNode<T> insert(AVLNode<T> n) {
		return insert(n, null, false);
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
		return null;
	}

	/**
	 * remember to maintain rightWeight
	 *
	 * @return true if the tree's root is changed
	 * @throws IllegalStateException if this is not a left child
	 */
	private boolean rotateRight() {
		Parameters.checkState(isRoot() || isLeftChild());
		/* cases to consider:
		 * 1. pivot is root
		 * 2. par is root
		 * 3. par is null
		 * 4. par.rightchild is null
		 */
		boolean changedRoot = false;

		//          ?
		//         /
		//       root
		//      /   \
		//  pivot   rr
		//  /  \   / \
		// pl  pr ?  ?
		//
		//      ↓
		//
		//          ?
		//         /
		//       pivot
		//      /    \
		//    rr    root
		//   / \    /  \
		//  ?  ?   pr  pl
		//
		// root: this
		// pivot: pivot
		// pl: pivot.leftChild
		// pr: pivot.rightChild
		// rr: rightChild

		// root: this
		// rs: right
		// os: left
		AVLNode<T> pivot = leftChild;
		// root left = pr
		leftChild = pivot.rightChild;
		// pr = root
		pivot.rightChild = this;
		// rr on new pivot left
		AVLNode<T> pivotLeft = pivot.leftChild;
		pivot.leftChild = rightChild;
		// right = pl
		rightChild = pivotLeft;

		// swap pivot and this
		// this = pivot;
		rightChild = pivot.leftChild;
		if (hasParent()) {
			parent.leftChild = pivot;
			changedRoot = true;
		}
		return changedRoot;
	}

	/**
	 * remember to maintain rightWeight
	 */
	private boolean rotateLeft() {
		Parameters.checkState(isRoot() || isRightChild());
		boolean changedRoot = false;
		// TODO
		return changedRoot;
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
