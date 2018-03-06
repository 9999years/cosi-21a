import java.util.Iterator;
import java.util.function.Consumer;

public class TreeTraverser {
    protected static class PreOrderIterator<T> implements Iterator<AVLNode<T>> {
        private AVLNode<T> tree;
        private AVLNode<T> next = null;

        PreOrderIterator(AVLNode<T> tree) {
            this.tree = tree;
        }

        public boolean hasNext() {
            if (next != null) {
                return true;
            } else if (tree.hasLeftChild()) {
                tree = tree.getLeftChild();
                return true;
            } else if (tree.hasRightChild()) {
                tree = tree.getRightChild();
                return true;
            } else if (tree.hasParent()) {
                // no children; ie tree is a bottom node; try its parents
                tree = tree.getParent();
                return hasNext();
            } else {
                return false;
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