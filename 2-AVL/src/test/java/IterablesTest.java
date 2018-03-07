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

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;
import java.util.function.Function;

public class IterablesTest {
	@SafeVarargs
	@SuppressWarnings("varargs")
	final <T, U> U apply(Function<Iterable<T>, U> fn, T... ts) {
		return fn.apply(Arrays.asList(ts));
	}

	@Test
	void simpleToStringTest() {
		assertEquals("[]",           apply(Iterables::toString, new String[] {}));
		assertEquals("[1]",          apply(Iterables::toString, 1));
		assertEquals("[1, 2]",       apply(Iterables::toString, 1, 2));
		assertEquals("[1, 2, 3]",    apply(Iterables::toString, 1, 2, 3));
		assertEquals("[1, null, 3]", apply(Iterables::toString, 1, null, 3));
	}

	@Test
	void simpleEnglishToStringTest() {
		assertEquals("", Iterables.englishToString(Arrays.asList()));
		assertEquals("A", Iterables.englishToString(Arrays.asList("A")));
		assertEquals("A and B", Iterables.englishToString(
			Arrays.asList("A", "B")));
		assertEquals("A, B, and C", Iterables.englishToString(
			Arrays.asList("A", "B", "C")));
		assertEquals("A, B, C, and D", Iterables.englishToString(
			Arrays.asList("A", "B", "C", "D")));
	}
}
