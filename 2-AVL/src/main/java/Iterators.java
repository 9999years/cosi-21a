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

import java.util.Iterator;
import java.util.function.Function;

public class Iterators {
	private Iterators() {}

	public static <T, R> Iterator<R> map(
			Iterator<T> itr, Function<T, R> mapper) {
		return new Iterator<R>() {
			public boolean hasNext() {
				return itr.hasNext();
			}

			public void remove() {
				itr.remove();
			}

			public R next() {
				return mapper.apply(itr.next());
			}
		};
	}
}
