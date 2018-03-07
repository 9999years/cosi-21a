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

import java.util.Comparator;
import java.util.Objects;

/**
 * Your code goes in this file
 * fill in the empty methods to allow for the required
 * operations. You can add any fields or methods you want
 * to help in your implementations.
 */

public class AVLNode<T> {
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

    /**
     * a lower bound on the tree's height; actual height of bottom leaves may be
     * greater than this by 1
     *
     * O(log n) time because there's no way in hell i'm adding another field to
     * this bloated class
     */
    public int approximateHeight() {
        return 1 + leftChild.approximateHeight();
    }

    /**
     * This should return the new root of the tree
     * make sure to update the balance factor and right weight
     * and use rotations to maintain AVL condition
     */
    public AVLNode<T> insert(T newData, double value) {
        Objects.requireNonNull(newData);
        if(value < this.value) {
            if(hasLeftChild()) {
                // recurse
                leftChild.insert(newData, value);
            } else {
                // no left child
                leftChild = new AVLNode<>(newData, value);
            }
        } else {
            // value >= this.value
            if(hasRightChild()) {
                // recurse
                rightChild.insert(newData, value);
            } else {
                // no left child
                rightChild = new AVLNode<>(newData, value);
            }
        }
        return this;
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

    //remember to maintain rightWeight
    private void rotateRight() {
        //TODO
    }

    //remember to maintain rightWeight
    private void rotateLeft() {
        //TODO
    }

    /**
     * this should return the tree of names with parentheses separating subtrees
     * eg "((bob)alice(bill))"
     *
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

    private String toStringDotUnwrapped() {
        return "\"" + data.toString()
                + "\" -> \"" + leftChild.toString() + "\";\n"
                + "\"" + data.toString()
                + "\" -> \"" + rightChild.toString() + "\";\n";
    }

    /**
     * creates a GraphViz dot representation of this tree
     */
    public String toStringDot() {
        return "digraph {\nnode[shape=box];"
                + toStringDotUnwrapped()
                + leftChild.toStringDotUnwrapped()
                + rightChild.toStringDotUnwrapped()
                + "}\n";
    }

    /**
     * uses a fairly large epsilon for comparing `data` fields
     *
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof AVLNode)) {
            return false;
        }
        AVLNode<?> n = (AVLNode<?>) o;
        return Objects.equals(data, n.data)
                && Doubles.equals(value, n.value, 0.000001);
    }
}
