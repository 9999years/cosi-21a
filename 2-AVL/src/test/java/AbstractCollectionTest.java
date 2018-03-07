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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * collection backed by a java.util.ArrayList
 * @param <T>
 */
class RealCollection<T> extends AbstractCollection<T> {
    List<T> dat; // lol

    RealCollection(int cap) {
        dat = new ArrayList<>(cap);
    }

    RealCollection(Collection<? extends T> col) {
        this(col.size());
        addAll(col);
    }

    RealCollection() {
        this(16);
    }

    @Override
    public boolean add(T t) {
        return dat.add(t);
    }

    @Override
    public Iterator<T> iterator() {
        return dat.iterator();
    }

    @Override
    public int size() {
        return dat.size();
    }
}

class AbstractCollectionTest {
    List<Integer> ints = Arrays.asList(46, 82, 11, 81, 33, 55, 97, 48, 75, 5, 70, 16, 1, 13, 45);

    Collection<Integer> col() {
        return new RealCollection<>(ints);
    }

    @Test
    void clear() {
        Collection<Integer> col = col();
        int i = 0;
        for(int c : col) {
            assertEquals((int) ints.get(i), c);
            i++;
        }
        col.clear();
        assertEquals(0, col.size());
    }

    @Test
    void contains() {
        Collection<Integer> col = col();
        for(int i : ints) {
            assertTrue(col.contains(i));
        }
    }
    @Test
    void contains2() {
        Collection<Integer> col = new RealCollection<>();
        col.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        for(int i = 0; i > -100; i--) {
            assertFalse(col.contains(i));
        }
    }

    @Test
    void containsAll() {
        Collection<Integer> col = col();
        assertTrue(col.containsAll(ints));
        col.remove(1); // removes the number, not the index 1
        assertFalse(col.containsAll(ints));
    }

    @Test
    void isEmpty() {
        Collection<Integer> col = new RealCollection<>();
        assertEquals(0, col.size());
        assertTrue(col.isEmpty());
        col.add(4848);
        assertFalse(col.isEmpty());
    }

    @Test
    void remove() {
        Collection<Integer> col = new RealCollection<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(5, col.size());
        assertTrue(col.remove(1));
        assertFalse(col.remove(-1));
        assertEquals(4, col.size());
        //assertArrayEquals(new Object[] {2, 3, 4, 5}, col.toArray());
        col.remove(4);
        assertEquals(3, col.size());
        //assertArrayEquals(new Object[] {2, 3, 5}, col.toArray());
    }

    @Test
    void removeAll() {
        Collection<Integer> col = new RealCollection<>(Arrays.asList(1, 2, 3, 4, 5));
        col.removeAll(Arrays.asList(2, 3, 5, 849));
        assertArrayEquals(new Object[] {1, 4}, col.toArray());
    }

    @Test
    void retainAll() {
        Collection<Integer> col = new RealCollection<>(Arrays.asList(1, 2, 3, 4, 5));
        col.retainAll(Arrays.asList(2, 3, 5, 849));
        assertArrayEquals(new Object[] {2, 3, 5}, col.toArray());
    }

    @Test
    void toArray() {
        Collection<Integer> col = new RealCollection<>(Arrays.asList(1, 2, 3, 4, 5));
        assertArrayEquals(new Object[] {1, 2, 3, 4, 5}, col.toArray());
    }

    @Test
    void toArray1() {
        Collection<Integer> col = new RealCollection<>(Arrays.asList(1, 2, 3, 4, 5));
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, col.toArray(new Integer[0]));
    }

    @Test
    void toStringTest() {
        Collection<Integer> col = new RealCollection<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals("[1, 2, 3, 4, 5]", col.toString());
        col.clear();
        assertEquals("[]", col.toString());
        col.add(3);
        assertEquals("[3]", col.toString());
        col.add(3);
        assertEquals("[3, 3]", col.toString());
    }
}