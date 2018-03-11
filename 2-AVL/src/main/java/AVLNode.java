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

	protected void detectTreeLoop() {
		// for debugging a weird bug
		if (hasParent() && parent.parent == this) {
			throw new IllegalStateException("Tree loop detected --- something has gone disastrously wrong!");
		}
	}

	protected boolean isRoot() {
		return parent == null;
	}

	protected AVLNode<T> getRoot() {
		AVLNode<T> root = this;
		while (!root.isRoot()) {
			detectTreeLoop();
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
			leftHeight = 1 + leftChild.height();
		}
		if (hasRightChild()) {
			return Math.max(leftHeight, 1 + rightChild.height());
		} else {
			return leftHeight;
		}
	}

	protected void manualUpdateBalanceFactor() {
		balanceFactor = 0;
		if (hasRightChild()) {
			balanceFactor = rightChild.height();
		}
		if (hasLeftChild()) {
			balanceFactor -= leftChild.height();
		}
	}

	/**
	 * changes this node's balance factor by delta and updates its parents until
	 * finding one that is unbalanced
	 *
	 * @param delta the delta by which to increase the balance factor
	 * @return the first unbalanced node found
	 */
	private AVLNode<T> nextUnbalanced(int delta) {
		balanceFactor += delta;
		if (unbalanced()) {
			return this;
		} else if (hasParent()) {
			detectTreeLoop();
			return parent.nextUnbalanced(delta);
		} else {
			// root; tree still balanced
			return null;
		}
	}

	/**
	 * @param leftInsert if the insertion was on the left
	 * @return the delta to change the balance factor by
	 */
	private int balanceFactorDelta(boolean leftInsert) {
		return leftInsert ? -1 : 1;
	}

	private void updateRightWeight() {
		rightWeight =
			hasRightChild()
			? 1 + rightChild.rightWeight
			: 0;
		if(hasParent() && isRightChild()) {
			detectTreeLoop();
			parent.updateRightWeight();
		}
	}

	public int getBalanceFactor() {
		return balanceFactor;
	}

	public boolean unbalanced() {
		return balanceFactor < -1 || balanceFactor > 1;
	}

	/**
	 * insertion at Z; ie Z.height += 1
	 * @param Z
	 * @return
	 */
	private AVLNode<T> wikipediaRebalance(AVLNode<T> Z) {
		AVLNode<T> G;
		AVLNode<T> N;
		// X is Z's parent
		for(AVLNode<T> X = Z.getParent(); Z.hasParent(); X = Z.getParent()) {
			// BF(X) needs update
			if(Z.isRightChild()) {
				if(X.getBalanceFactor() > 0) {
					// right-heavy
					// temp BF = +2
					G = X.getParent();
					if(Z.getBalanceFactor() < 0) {
						// RL case
						// verify this
						N = Z.rotateRight();
						N = X.rotateLeft();
					} else {
						// right right
						N = X.rotateLeft();
					}
				} else {
					if (X.getBalanceFactor() < 0) {
						X.balanceFactor = 0;
						break;
					}
					X.balanceFactor += 1;
					Z = X;
					continue;
				}
			} else {
				// left-child case
				if (X.getBalanceFactor() < 0) {
					G = X.getParent();
					if (Z.getBalanceFactor() > 0) {
						// LR case
						N = Z.rotateLeft();
						N = X.rotateRight();
					} else {
						// LL
						N = X.rotateRight();
					}
				} else {
					if (X.getBalanceFactor() > 0) {
						X.balanceFactor = 0;
						break;
					}
					X.balanceFactor = -1;
					Z = X;
					continue;
				}
			}
			N.setParent(G);
			if (G != null) {
				if (X == G.getLeftChild()) {
					G.setLeftChild(N);
				} else {
					G.setRightChild(N);
				}
				break;
			} else {
				// new root is N
				return N;
			}
		}
		return Z;
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
//			newThis = rebalance(onLeft, inside);
			newThis = wikipediaRebalance(n);
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
		System.out.println("rebalancing " + this + " (bf " + balanceFactor + ")\n");
		System.out.println(DotDigraph.toString(this));

		AVLNode<T> newThis = this;

		if (onLeft) {
			newThis = newThis.rotateRight();
		} else {
			newThis = newThis.rotateLeft();
		}
		if (inside) {
			if (onLeft) {
				newThis = newThis.rotateLeft();
			} else {
				newThis = newThis.rotateRight();
			}
		}

		System.out.println(DotDigraph.toString(newThis.getRoot()));

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
