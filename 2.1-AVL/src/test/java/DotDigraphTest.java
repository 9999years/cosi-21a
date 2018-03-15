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

class DotDigraphTest {
	@Test
	void toStringTest() {
		AVLNode<Integer> n = new AVLNode<>(1, 9.9);
		// left
		n.insert(2, 0.0);
		// right
		n.insert(3, 20.0);
		// left left
		n.insert(4, -1.0);
		assertEquals(
				"digraph { "
				+ "node[shape=box]; "
				+ "graph[splines=polyline]; "
				+ "1[label=\"9.9 -> 1, BF=1, Height=3\"]; "
				+ "1:s -> 2:ne; "
				+ "1:s -> 3:nw; "
				+ "2[label=\"0.0 -> 2, BF=1, Height=2\"]; "
				+ "2:s -> 4:ne; "
				+ "4[label=\"-1.0 -> 4, BF=0, Height=1\"]; "
				+ "3[label=\"20.0 -> 3, BF=0, Height=1\"]; "
				+ "}",
				DotDigraph.toString(n));
	}
}
