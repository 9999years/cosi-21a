import java.util.Iterator;

public class TreeTraverser {
    protected static class PreOrderIterator<T> implements Iterator<AVLNode<T>> {
        private enum BDecision {
            Left, Right
        }

        private AVLNode<T> tree;
        // has the current node been visited? i.e. do we need to calculate another
        private boolean visited = false;
        // it might be better to use like... a bit stack of some
        // sort? but a couple more pointers won't break the bank
        private Deque<BDecision> decisions;

        PreOrderIterator(AVLNode<T> tree) {
            this.tree = tree;
            decisions = new Deque<>(tree.approximateHeight());
        }

        public boolean hasNext() {
            if (!visited) {
                return true;
            }
            // we need to get another node
            visited = false;
            if(tree.hasLeftChild()) {
                decisions.addLast(BDecision.Left);
                tree = tree.getLeftChild();
            } else if(tree.hasRightChild()) {
                decisions.addLast(BDecision.Right);
                tree = tree.getRightChild();
            } else {
                /* no children;
                 * go up the decision tree:
                 * first time we see a left, go right
                 * if we get to the top of the tree and
                 * the top node is right, we're done
                 * if the decision tree is empty, the tree is empty
                 */
                while(!decisions.isEmpty()
                        && (decisions.removeLast() == BDecision.Right
                            || !tree.hasRightChild())) {
                    tree = tree.getParent();
                }

                if(decisions.isEmpty()) {
                    // we've exhausted all the nodes and gone back up the tree
                    return false;
                }
                // we've hit a left turn, but we might not have a right child
            }
            return true;
        }

        public AVLNode<T> next() {
            if(!hasNext()) {
                throw new UnsupportedOperationException();
            }
            return null;
        }
    }

    public static <T> Iterator<AVLNode<T>> preOrderNodes(AVLNode<T> tree) {
        return new PreOrderIterator<>(tree);
    }

    public static <T> Iterator<T> preOrder(AVLNode<T> tree) {
        return Iterators.map(preOrderNodes(tree), t -> t.getData());
    }
}
