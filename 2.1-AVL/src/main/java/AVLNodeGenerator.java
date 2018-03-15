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

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * generates an infinite predictable sequence of AVLNode&lt;Integer&gt;s
 *
 * useful for, for example, generating a sizeable tree without much typing
 *
 * both an Iterator and an Iterable
 *
 * try: new AVLNodeGenerator().finite(100) to get 100 elements
 */
public class AVLNodeGenerator
		implements Iterator<AVLNode<Integer>>, Iterable<AVLNode<Integer>> {
	public static final int MIN_DATA = -100;
	public static final int MAX_DATA = 100;
	public static final int DATA_RANGE = MAX_DATA - MIN_DATA;
	public static final double MIN_VALUE = -1000.0;
	public static final double MAX_VALUE = 1000.0;
	public static final double VALUE_RANGE = MAX_VALUE - MIN_VALUE;

	private static final int INFINITE_ITERATOR = -1;
	private Random random = new Random();
	/**
	 * how many nodes are left, or INFINITE_ITERATOR if this iterator is infinite
	 */
	private int remaining = INFINITE_ITERATOR;

	AVLNodeGenerator() {}

	AVLNodeGenerator(long seed) {
		random.setSeed(seed);
	}

	/**
	 * transforms this AVLNodeGenerator to one containing a finite amount of
	 * elements and returns it
	 * @param count
	 * @return this object
	 */
	public AVLNodeGenerator finite(int count) {
		Parameters.checkArgument(count >= 0);
		remaining = count;
		return this;
	}

	@Override
	public boolean hasNext() {
		return remaining == INFINITE_ITERATOR || remaining != 0;
	}

	@Override
	public AVLNode<Integer> next() {
		Parameters.check(hasNext(), NoSuchElementException::new);
		if(remaining != INFINITE_ITERATOR) {
			remaining--;
		}
		return new AVLNode<>(
				MIN_DATA + random.nextInt(DATA_RANGE),
				MIN_VALUE + VALUE_RANGE * random.nextDouble());
	}

	@Override
	public Iterator<AVLNode<Integer>> iterator() {
		return this;
	}
}
