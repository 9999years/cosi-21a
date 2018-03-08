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
		"digraph {\n"
				+ "node[shape=box];\n"
				+ "1[label=\"9.9 &#x2192; 1\"];\n"
				+ "1 -> 2;\n"
				+ "1 -> 3;\n"
				+ "2[label=\"0.0 &#x2192; 2\"];\n"
				+ "2 -> 4;\n"
				+ "4[label=\"-1.0 &#x2192; 4\"];\n"
				+ "3[label=\"20.0 &#x2192; 3\"];\n"
				+ "}\n",
				DotDigraph.toString(n));
	}
}
