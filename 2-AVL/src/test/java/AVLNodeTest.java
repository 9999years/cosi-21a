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
    }

    @Test
    void delete() {
    }

    @Test
    void treeString() {
    }

    @Test
    void toStringDot() {
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
}
