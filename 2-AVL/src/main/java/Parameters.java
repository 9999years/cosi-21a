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

import java.lang.Exception;

import java.util.function.Supplier;

import java.lang.IndexOutOfBoundsException;

/**
 * class for validating function parameters; some companion functions for
 * Objects.requireNonNull. api mostly compatible with Guava's Preconditions
 *
 * each method accepts a {@code Supplier<String>} or a {@code String} or
 * nothing.
 */
public class Parameters {
	protected static <E extends Exception> void check(
			boolean expression, Supplier<E> exceptionSupplier)
			throws E {
		if(!expression) {
			throw exceptionSupplier.get();
		}
	}

	/**
	 * throws an IllegalArgumentException with a custom message if
	 * test fails
	 */
	public static void checkArgument(boolean expression,
			Supplier<String> msg) throws IllegalArgumentException {
		if(!expression) {
			throw new IllegalArgumentException(msg.get());
		}
	}

	public static void checkState(boolean expression,
			Supplier<String> msg) throws IllegalStateException {
		if(!expression) {
			throw new IllegalStateException(msg.get());
		}
	}

	public static void checkArgument(boolean expression, String msg) {
		if(!expression) {
			throw new IllegalArgumentException(msg);
		}
	}

	public static void checkState(boolean expression, String msg) {
		if(!expression) {
			throw new IllegalStateException(msg);
		}
	}

	public static void checkArgument(boolean expression) {
		if(!expression) {
			throw new IllegalArgumentException();
		}
	}

	public static void checkState(boolean expression) {
		if(!expression) {
			throw new IllegalStateException();
		}
	}

	// INDEX CHECKING METHODS

	/**
	 * checks an index i.e. one valid from 1..size
	 * @param index the index
	 * @param size the size of the list
	 * @return the index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	public static int checkPositionIndex(int index, int size) {
		checkArgument(size >= 0);
		check(index > 0 && index <= size,
			IndexOutOfBoundsException::new);
		return index;
	}

	public static int checkPositionIndex(int index, int size, String msg) {
		checkArgument(size >= 0);
		check(index > 0 && index <= size,
			() -> new IndexOutOfBoundsException(msg));
		return index;
	}

	public static void checkPositionIndexes(int start, int end, int size) {
		checkArgument(size >= 0);
		check(start > 0 && start <= size,
			IndexOutOfBoundsException::new);
		check(end > 0 && end <= size,
			IndexOutOfBoundsException::new);
		check(start <= end, IndexOutOfBoundsException::new);
	}

	public static void checkPositionIndexes(int start, int end, int size, String msg) {
		checkArgument(size >= 0);
		check(start > 0 && start <= size,
			() -> new IndexOutOfBoundsException(msg));
		check(end > 0 && end <= size,
			() -> new IndexOutOfBoundsException(msg));
		check(start <= end, () -> new IndexOutOfBoundsException(msg));
	}

	public static int checkElementIndex(int index, int size) {
		checkArgument(size >= 0);
		check(index >= 0 && index < size,
			IndexOutOfBoundsException::new);
		return index;
	}

	public static int checkElementIndex(int index, int size, String msg) {
		checkArgument(size >= 0);
		check(index >= 0 && index < size,
			() -> new IndexOutOfBoundsException(msg));
		return index;
	}

	public static void checkElementIndexes(int start, int end, int size) {
		checkArgument(size >= 0);
		check(start >= 0 && start < size,
			IndexOutOfBoundsException::new);
		check(end >= 0 && end < size,
			IndexOutOfBoundsException::new);
		check(start <= end, IndexOutOfBoundsException::new);
	}

	public static void checkElementIndexes(int start, int end, int size, String msg) {
		checkArgument(size >= 0);
		check(start >= 0 && start < size,
			() -> new IndexOutOfBoundsException(msg));
		check(end >= 0 && end < size,
			() -> new IndexOutOfBoundsException(msg));
		check(start <= end, () -> new IndexOutOfBoundsException(msg));
	}
}
