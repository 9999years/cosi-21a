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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

    void checkParents(AVLNode<?> root) {
		if (root.hasLeftChild()) {
			assertSame(root, root.getLeftChild().getParent());
			checkParents(root.getLeftChild());
		}
		if (root.hasRightChild()) {
			assertSame(root, root.getRightChild().getParent());
			checkParents(root.getRightChild());
		}
	}

    @Test
    void parent() {
		AVLNode<Integer> root = new AVLNode<>(0, 0.0);
		for(AVLNode<Integer> n : new AVLNodeGenerator(637275).finite(100)) {
			root = root.insert(n);
			checkParents(root);
		}
	}

	/**
	 * inserts random nodes and deletes them in a random order
	 */
    @Test
    void delete() {
		// insert nodes, delete them 1 by 1
		int count = 1000;
		List<Double> vals = new ArrayList<>(count);
		AVLNodeGenerator gen = new AVLNodeGenerator(359).finite(count);
		AVLNode<Integer> root = gen.next();
		vals.add(root.getValue());
		// insert
		for (AVLNode<Integer> n : gen) {
			vals.add(n.getValue());
			root = root.insert(n);
		}
		// delete
		Random rand = new Random(10);
		while (!vals.isEmpty()) {
			double value = vals.remove(rand.nextInt(vals.size()));
			assertEquals(root.getRoot(), root,
					"asserting returned node is the tree's root");
			assertNotNull(root.get(value));
			// check avl property is maintained
			checkBalances(root);
 			root = root.delete(value);
		}
		// if everything's been deleted, we should have a null root
		assertNull(root);
    }

    @Test
    void treeString() {
		AVLNode<String> root = new AVLNode<>("A", 0.0);
		for (char i = 'A'; i <= 'Z'; i++) {
			root = root.insert(String.valueOf(i), (double) i);
		}
		assertEquals("(((((A)A(B))C((D)E(F)))G(((H)I(J))K((L)M(N))))O"
			+ "(((P)Q(R))S(((T)U(V))W((X)Y(Z)))))", root.treeString());
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

    // makes sure no nodes in a tree are unbalanced
    private <T> void checkBalances(AVLNode<T> root) {
		if (root == null) {
			return;
		}
    	assertFalse(root.isUnbalanced(), "checking balance about " + root);
		checkBalances(root.getLeftChild());
		checkBalances(root.getRightChild());
	}

	/**
	 * you can try this with 10_000 elements if youd like but itll take a while
	 * (like 20-30 seconds a while)
	 */
    @Test
    void balanceTest() {
        AVLNode<Integer> root = new AVLNode<>(0, 0.0);
        for(AVLNode<Integer> n : new AVLNodeGenerator(637275).finite(1000)) {
            root = root.insert(n);
			checkParents(root);
           	checkBalances(root);
        }
    }

    /**
     * make sure inserting stuff doesn't crash
     */
    @Test
    void genericTest() {
        AVLNode<Integer> root = new AVLNode<>(0, 0.0);
        for(AVLNode<Integer> n : new AVLNodeGenerator(637275).finite(80)) {
            root = root.insert(n);
            assertNotNull(root);
        }
    }

    @Test
    void isLeaf() {
		assertTrue(new AVLNode<Integer>(0, 0.0).isLeaf());
		assertFalse(new AVLNode<Integer>(0, 0.0)
				.insert(0, 1.0).isLeaf());
    }

	@Test
    void getBalanceFactor() {
    	assertEquals(0, new AVLNode<Integer>(0, 0.0)
			.getBalanceFactor());
    	assertEquals(1, new AVLNode<Integer>(0, 0.0)
			.insert(0, -1.0)
			.getBalanceFactor());
    }

    @Test
    void hashCodeTest() {
    	Integer data = new Integer(100);
    	double value = 3.2;
    	assertEquals(Objects.hash(data, value),
				new AVLNode<Integer>(data, value).hashCode());
    }

    @Test
    void toStringTest() {
    	assertEquals("AVLNode[0.0 -> 999, BF=0, Height=1]",
				new AVLNode<Integer>(999, 0.0).toString());
    }

	@Test
	void getTest() {
		AVLNode<String> root = new AVLNode<>("A", 10.1);
		root = root.insert("B", 0.1);
		assertSame(root, root.get(10.1));
		assertNull(root.get(10.10001));
		assertSame(root.getLeftChild(), root.get(0.1));
	}

	int actualWeight(AVLNode<?> n) {
		if (n == null) {
			// leaf, base case
			return 0;
		}
		return 1 + actualWeight(n.getLeftChild())
			+ actualWeight(n.getRightChild());
	}

	int actualRightWeight(AVLNode<?> n) {
		return actualWeight(n.getRightChild());
	}

	void assertRightWeight(AVLNode<?> n) {
		assertEquals(actualRightWeight(n), n.getRightWeight());
	}

	@Test
	void rightWeightTest() {
		AVLNode<Integer> root = new AVLNode<>(0, 0.0);
		for(AVLNode<Integer> n : new AVLNodeGenerator(637275).finite(1000)) {
			root = root.insert(n);
			System.out.println("[INFO] [INFO] Done with insert");
			System.out.println(DotDigraph.toString(root));
			assertRightWeight(root);
		}
	}
}
