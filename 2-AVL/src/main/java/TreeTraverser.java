import java.util.Iterator;
import java.util.function.Consumer;

public class TreeTraverser {
    protected static class PreOrderIterator<T> implements Iterator<AVLNode<T>> {
        private enum BDecision {
            Left, Right
        }

        private AVLNode<T> tree;
        // has the current node been visited? i.e. do we need to calculate another
        private boolean visited = false;
        private Deque<BDecision> decisions;

        PreOrderIterator(AVLNode<T> tree) {
            this.tree = tree;
            decisions = new Deque<>(tree.approximateHeight());
        }

        public boolean hasNext() {
            if(visited) {
                // we need to get another node
                if(tree.hasLeftChild()) {
                    decisions.addLast(BDecision.Left);
                    tree = tree.getLeftChild();
                } else if(tree.hasRightChild()) {
                    decisions.addLast(BDecision.Right);
                    tree = tree.getRightChild();
                } else {
                    // no children
                    /**
                     * go up the decision tree:
                     * first time we see a left, go right
                     * if we get to the top of the tree and the top node is right, we're done
                     * if the decision tree is empty, the tree is empty
                     */

                    while(!decisions.isEmpty() && decisions.removeLast() == BDecision.Right) {
                        tree = tree.getParent();
                    }
                    if(decisions.isEmpty()) {
                        return false;
                    }
                    // we've hit a left turn, but we might not have a right child

                }
                visited = false;
                return true;
            } else {
                return true;
            }
        }

        public AVLNode<T> next() {
            if(!hasNext()) {
                throw new UnsupportedOperationException();
            }
            AVLNode<T> ret = next;
            next = null;
            return ret;
        }
    }

    public static <E> Iterator<AVLNode<E>> preOrder(AVLNode<E> tree) {
        return new PreOrderIterator<E>(tree);
    }
}