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
	 * number of nodes in right subTree
	 */
	private int rightWeight;
	/**
	 * height(leftChild) - height(rightChild)
	 */
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
		if (hasLeftChild()) {
			leftChild.parent = null;
		}
		leftChild = n;
		if (n != null) {
			n.parent = this;
		}
	}

	protected void setRightChild(AVLNode<T> n) {
		if(hasRightChild()) {
			rightChild.parent = null;
		}
		rightChild = n;
		if(n != null) {
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
	 * changes this node's balance factor by delta and updates its parents until
	 * finding one that is unbalanced
	 *
	 * @return the first unbalanced node found or null if no nodes are unbalanced
	 */
	private AVLNode<T> nextUnbalanced() {
		if (unbalanced()) {
			return this;
		} else if (hasParent()) {
			return parent.nextUnbalanced();
		} else {
			// root; tree still balanced
			return null;
		}
	}

	/**
	 *
	 * @param delta the delta to change the BF by; if the left subtree was
	 * inserted on, delta = 1; for the right, -1
	 */
	private void updateBalanceFactors(int delta) {
		balanceFactor += delta;
		 if (hasParent()) {
			if(isLeftChild()) {
				// bf of this node has just increase by `delta`
				// update parent to match
				parent.updateBalanceFactors(delta);
			} else {
				// - delta because this is a right child
				parent.updateBalanceFactors(-delta);
			}
		}
	}

	private void updateRightWeight() {
		rightWeight =
			hasRightChild()
			? 1 + rightChild.rightWeight
			: 0;
		if(hasParent() && isRightChild()) {
			parent.updateRightWeight();
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
			updateBalanceFactors(onLeft ? 1 : -1);
			newThis = rebalance(onLeft, inside);
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
	 * @param onLeft was the insertion performed on the left or the right of
	 *               a node?
	 * @param inside was the insertion an inside case?
	 * @return the new root of this tree or subtree
	 */
	private AVLNode<T> rebalance(boolean onLeft, boolean inside) {
		if (!unbalanced()) {
			if (hasParent()) {
				parent.rebalance(isRightChild(), inside);
			}
			return this;
		}

		System.out.println("rebalancing " + this + " (bf " + balanceFactor + ")\n");
		System.out.println(DotDigraph.toString(this));

		AVLNode<T> newThis = this;

//		while (unbalanced()) {
//			if (balanceFactor < 0) {
//				// left-heavy
//				rotateRight();
//				balanceFactor += 1;
//			} else {
//				rotateLeft();
//				balanceFactor -= 1;
//			}
//		}

//		if (onLeft) {
//			newThis = newThis.rotateRight();
//		} else {
//			newThis = newThis.rotateLeft();
//		}
//		if (inside) {
//			if (onLeft) {
//				newThis = newThis.rotateLeft();
//			} else {
//				newThis = newThis.rotateRight();
//			}
//		}

//		updateBalanceFactors(-balanceFactorDelta(onLeft));
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
		AVLNode<T> pivot = leftChild;
		setLeftChild(pivot.rightChild);
		// sets parent's child to pivot too;
		// sets pivot's parent to null if it's the new root
		pivot.setParent(parent);
		// handles setting parent to pivot
		pivot.setRightChild(this);
		pivot.updateRightWeight();
		// right subtree height += 1
		// parent.updateBalanceFactors(1);
		return pivot;
	}

	/**
	 * can only be called if hasRightChild()
	 */
	private AVLNode<T> rotateLeft() {
		Parameters.checkState(hasRightChild(),
				"left rotate requires right child!");
		AVLNode<T> pivot = rightChild;
		setRightChild(pivot.leftChild);
		pivot.setParent(parent);
		pivot.setLeftChild(this);
		updateRightWeight();
		// left subtree height += 1
		// parent.updateBalanceFactors(-1);
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
				+ ", BF=" + balanceFactor + ", RW=" + rightWeight + "]";
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
				+ "   PAR="+ parent + "\n";
	}
}
