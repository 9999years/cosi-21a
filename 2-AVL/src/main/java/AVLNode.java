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
	private T data;
	private double value;
	private AVLNode<T> parent;
	private AVLNode<T> leftChild;
	private AVLNode<T> rightChild;
	/**
	 * number of nodes in right subTree
	 */
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
		leftChild = n;
		if (n != null) {
			n.parent = this;
		}
	}

	protected void setRightChild(AVLNode<T> n) {
		rightChild = n;
		if(n != null) {
			n.parent = this;
		}
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

	public int getBalanceFactor() {
		return balanceFactor;
	}

	public boolean unbalanced() {
		return balanceFactor < -1 || balanceFactor > 1;
	}


	private AVLNode<T> insert(
			AVLNode<T> n, boolean onLeft, boolean inside) {
		boolean inserted = false;
		if (n.value < value) {
			if (hasLeftChild()) {
				leftChild.insert(n, true, inside && onLeft);
			} else {
				// no left child
				setLeftChild(n);
				inserted = true;
			}
		} else {
			// newValue >= value
			if (hasRightChild()) {
				rightChild.insert(n, false, inside && !onLeft);
			} else {
				// no right child
				setRightChild(n);
				inserted = true;
			}
		}

		AVLNode<T> newThis = this;
		if (inserted) {
			n.updateBalanceFactor();
			newThis = rebalance(onLeft, inside);
			n.updateBalanceFactor();
		}
		return newThis;
	}


	public AVLNode<T> insert(AVLNode<T> n) {
		return insert(n, false, true);
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
	 * NOTE: doesn't correct this node's parent's bf
	 */
	private void updateBalanceFactor() {
		balanceFactor = 0;
		if (hasRightChild()) {
			balanceFactor = 1 + rightChild.balanceFactor;
		}
		if (hasLeftChild()) {
			balanceFactor -= 1 + leftChild.balanceFactor;
		}
	}

	private AVLNode<T> rebalance(boolean onLeft, boolean inside) {
		updateBalanceFactor();
		if(!unbalanced()) {
			// node is well-balanced; try the parent
			if(hasParent()) {
				System.out.println("skipping " + this + " (bf " + balanceFactor + ")\n");
				parent.rebalance(onLeft, inside);
			}
			return this;
		}

		System.out.println("rebalancing " + this + " (bf " + balanceFactor + ")\n");
		System.out.println(DotDigraph.toString(this));

		AVLNode<T> newThis = this;
		if (inside) {
			if (onLeft) {
				System.out.println("rotating right");
				newThis = newThis.rotateRight();
				System.out.println("rotating left");
				newThis = newThis.rotateLeft();
			} else {
				System.out.println("rotating left");
				newThis = newThis.rotateLeft();
				System.out.println("rotating right");
				newThis = newThis.rotateRight();
			}
		} else {
			// outside case
			if (onLeft) {
				System.out.println("rotating right");
				newThis = newThis.rotateRight();
			} else {
				System.out.println("rotating left");
				newThis = newThis.rotateLeft();
			}
		}

		System.out.println(DotDigraph.toString(newThis));

		return newThis;
	}

	/**
	 * can only be called if hasLeftChild()
	 *
	 * @return true if the tree's root is changed
	 * @throws IllegalStateException if this is not a left child
	 */
	private AVLNode<T> rotateRight() {
		Parameters.checkState(hasLeftChild(),
				"right rotate requires a left child!");
		// TODO maintain rightWeight

		//         ?
		//         |
		//       root
		//      /   \
		//  pivot   rr
		//  /  \   / \
		// pl  pr ?  ?
		//
		//      ↓
		//
		//         ?
		//         |
		//       pivot
		//      /    \
		//    pl    root
		//   / \    /  \
		//  ?  ?   pr  rr
		//
		// root: this
		// pivot: pivot
		// pl: pivot.leftChild
		// pr: pivot.rightChild
		// rr: rightChild
		//
		// unchanged: root -> rr
		//            pivot -> pl

		AVLNode<T> pivot = leftChild;
		if (hasParent()) {
			if (isLeftChild()) {
				parent.setLeftChild(pivot);
			} else {
				parent.setRightChild(pivot);
			}
		}
		setLeftChild(pivot.rightChild);
		pivot.setRightChild(this);
		pivot.rightWeight = 1 + rightWeight;
		return pivot;
	}

	/**
	 * remember to maintain rightWeight
	 */
	private AVLNode<T> rotateLeft() {
		Parameters.checkState(hasRightChild(),
				"left rotate requires right child!");
		//         ?
		//         |
		//       root
		//      /   \
		//    rl   pivot
		//  /  \   / \
		// ?   ?  pl  pr
		//
		//      ↓
		//
		//        ?
		//        |
		//      pivot
		//     /   \
		//   root   pr
		//  /  \
		// rl  pl

		// root: this
		// pivot: rightChild
		// pl: pivot.leftChild
		// pr: pivot.rightChild
		// rl: leftChild

		// unchanged: pivot -> pr
		//            root -> rl

		AVLNode<T> pivot = rightChild;
		if (hasParent()) {
			if (isLeftChild()) {
				parent.setLeftChild(pivot);
			} else {
				parent.setRightChild(pivot);
			}
		}
		setRightChild(pivot.leftChild);
		pivot.setLeftChild(this);
		rightWeight = hasRightChild() ? rightChild.rightWeight : 0;
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
		return "AVLNode[" + value + " -> " + data
				+ ", BF=" + balanceFactor + ", RW=" + rightWeight + "]";
	}
}
