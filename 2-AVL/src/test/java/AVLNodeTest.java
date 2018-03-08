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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLNodeTest {
    @Test
    void hasLeftChild() {
        AVLNode<Integer> n = new AVLNode<>(100, 9.9);
        assertFalse(n.hasLeftChild());
        // insert on left
        n.insert(100, 0);
        assertTrue(n.hasLeftChild());
    }

    @Test
    void hasRightChild() {
        AVLNode<Integer> n = new AVLNode<>(100, 9.9);
        assertFalse(n.hasRightChild());
        // insert on left
        n.insert(100, 20.0);
        assertTrue(n.hasRightChild());
    }

    @Test
    void hasParent() {
        AVLNode<Integer> n = new AVLNode<>(100, 9.9);
        // insert on right
        n.insert(100, 20.0);
        assertTrue(n.getRightChild().hasParent());
    }

    @Test
    void getLeftChild() {
    }

    @Test
    void getRightChild() {
    }

    @Test
    void getParent() {
        AVLNode<Integer> n = new AVLNode<>(100, 9.9);
        // insert on right
        n.insert(100, 20.0);
        assertEquals(n, n.getRightChild().getParent());
    }

    @Test
    void isRightChild() {
        AVLNode<Integer> n = new AVLNode<>(100, 9.9);
        // insert on right
        n.insert(100, 20.0);
        assertTrue(n.getRightChild().isRightChild());
        assertFalse(n.getRightChild().isLeftChild());
    }

    @Test
    void isLeftChild() {
        AVLNode<Integer> n = new AVLNode<>(100, 9.9);
        // insert on left
        n.insert(100, 0.0);
        assertFalse(n.getLeftChild().isRightChild());
        assertTrue(n.getLeftChild().isLeftChild()) ;
    }

    @Test
    void approximateHeight() {
    }

    @Test
    void insert() {
        AVLNode<Integer> n = new AVLNode<>(1, 9.9);
        // left
        n.insert(2, 0.0);
        assertEquals(new AVLNode<>(2, 0.0), n.getLeftChild());
        // right
        n.insert(3, 20.0);
        assertEquals(new AVLNode<>(3, 20.0), n.getRightChild());
        // left left
        n.insert(4, -1.0);
        assertEquals(new AVLNode<>(4, -1.0),
                n.getLeftChild().getLeftChild());

        // check first 2 again
        assertEquals(new AVLNode<>(2, 0.0), n.getLeftChild());
        assertEquals(new AVLNode<>(3, 20.0), n.getRightChild());
    }

    @Test
    void delete() {
    }

    @Test
    void treeString() {
    }

    @Test
    void toStringDot() {
        AVLNode<Integer> n = new AVLNode<>(1, 9.9);
        // left
        n.insert(2, 0.0);
        // right
        n.insert(3, 20.0);
        // left left
        n.insert(4, -1.0);
        assertEquals("digraph {\nnode[shape=box];\n"
                + "\"9.9 &#x2192; 1\" -> \"0.0 &#x2192; 2\";\n"
                + "\"9.9 &#x2192; 1\" -> \"20.0 &#x2192; 3\";\n"
                + "\"0.0 &#x2192; 2\" -> \"-1.0 &#x2192; 4\";\n}\n", n.toStringDot());
    }

    @Test
    void equals() {
        assertEquals(new AVLNode<>(100, 20.0),
                new AVLNode<>(100, 20.0));
        assertEquals(new AVLNode<>(-33.2, 0.0),
                new AVLNode<>(-33.2, 0.0));
        assertNotEquals(new AVLNode<>(100, 20.0), new AVLNode<>(100, 21.0));
        assertNotEquals(new AVLNode<>(102, 20.0),
                new AVLNode<>(100, 20.0));
        assertNotEquals(new AVLNode<>(102, 20.000000000001),
                new AVLNode<>(100, 20.0), "checking within epsilon");
    }

    @Test
    void balanceTest() {
        // by choosing a root node with a value of -10_000 we ensure that, if
        // the tree isn't self-balanced, the generator's nodes between -1000 and
        // 1000 will surely make the tree incredibly right-heavy
        AVLNode<Integer> root = new AVLNode<>(0, -10_000.0);
        for(AVLNode<Integer> n : new AVLNodeGenerator(637275).finite(100)) {
            root.insert(n);
            int bf = root.getBalanceFactor();
            assertTrue(-1 <= bf, "balance factor " + bf);
            assertTrue(1 >= bf, "balance factor " + bf);
        }
    }
}
