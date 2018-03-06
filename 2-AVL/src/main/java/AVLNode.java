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
        //TODO
    }

    protected AVLNode<T> getParent() {
        return parent;
    }

    protected boolean hasLeftChild() {
        return leftChild != null;
    }

    protected boolean hasRightChild() {
        return rightChild != null;
    }

    protected AVLNode<T> getLeftChild() {
        return leftChild;
    }

    protected AVLNode<T> getRightChild() {
        return rightChild;
    }

    /**
     * This should return the new root of the tree
     * make sure to update the balance factor and right weight
     * and use rotations to maintain AVL condition
     */
    public AVLNode<T> insert(T newData, double value) {
        //TODO
        return null;
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
}