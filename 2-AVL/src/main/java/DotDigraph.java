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

import java.util.function.Function;

public class DotDigraph {
	private static final int INITIAL_ID = 1;

	/**
	 * @return "value -> data" (quotes are included in result string)
	 */
	private static String dotNodeString(AVLNode n) {
		String ret = n.toString();
		// AVLNode[.....]
		// ^       ^   ^
		// 0       8   n-1
		return ret.substring(8, ret.length() - 1);
	}

	/**
	 * creates a node label declaration such that the node's label stays the same
	 * but node uniqueness is determined by the node's memory location, so that
	 * a nodeText function generating many of the same value will still create an
	 * accurate representation of the tree
	 * @return the declaration statement
	 */
	private static String nodeDeclaration(
			AVLNode<?> n, Function<AVLNode<?>, String> nodeText, int id) {
		return id + "[label=\"" + nodeText.apply(n) + "\"];\n";
	}

	private static int leftId(int id) {
		int ret = id * 2;
		Parameters.checkState(id < ret, "Tree is too tall!");
		return ret;
	}

	private static int rightId(int id) {
		int ret = id * 2 + 1;
		Parameters.checkState(id < ret, "Tree is too tall!");
		return ret;
	}

	/**
	 * returns the "unwrapped" (no `digraph { ... }` boilerplate) GraphViz dot
	 * representation of this node
	 *
	 * this uses a clever 'id-tracking' system; because the nodeText function might
	 * return identical values for many nodes, we need to keep track of the nodes
	 * and their values separately
	 *
	 * to do this, we assign each node in the tree an id and do "some math" (see
	 * leftId and rightId) to ensure every node is unique
	 */
	private static String dotStringNoWrapper(
			AVLNode<?> tree, Function<AVLNode<?>, String> nodeText, int id) {
		StringBuilder sb = new StringBuilder();
		int leftId = leftId(id);
		int rightId = rightId(id);
		sb.append(nodeDeclaration(tree, nodeText, id));
		if(tree.hasLeftChild()) {
			sb.append(id).append(":s -> ").append(leftId).append(":ne;\n");
		}
		if(tree.hasRightChild()) {
			sb.append(id).append(":s -> ").append(rightId).append(":nw;\n");
		}
		if(tree.hasLeftChild()) {
			sb.append(dotStringNoWrapper(tree.getLeftChild(), nodeText, leftId));
		}
		if(tree.hasRightChild()) {
			sb.append(dotStringNoWrapper(tree.getRightChild(), nodeText, rightId));
		}
		return sb.toString();
	}

	/**
	 * creates a GraphViz dot representation of this tree
	 */
	public static String toString(AVLNode<?> tree) {
		return toString(tree, DotDigraph::dotNodeString);
	}

	/**
	 * creates a GraphViz dot representation of this tree with each node's text
	 * created with the specified nodeText function
	 */
	public static String toString(
			AVLNode<?> tree, Function<AVLNode<?>, String> nodeText) {
		return "digraph {\nnode[shape=box];\ngraph[splines=polyline];\n"
				+ dotStringNoWrapper(tree, nodeText, INITIAL_ID)
				+ "}\n";
	}

}
