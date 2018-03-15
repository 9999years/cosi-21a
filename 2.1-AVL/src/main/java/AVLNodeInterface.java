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

/**
 * represents a tree-map from doubles to T
 * @param <T> the value-type of the map
 */
public interface AVLNodeInterface<T> {

	/**
	 *  returns the data object stored in the node with this.value=value
	 * @param value the value to find
	 */
	T getData(double value);

	/**
	 * a method that returns a String of the tree of names (toString() method of
	 * the data), using parentheses to separate subtrees. For example, for the
	 * tree below, the printout would be:
	 * (((alice)bill((dave)fred(jane(joe))))judy(mary(tom)))
	 */
	String treeString();

	/**
	 * inserts a new node into this tree
	 * @param data the new node's data
	 * @param value the new node's value
	 * @return the tree's new root
	 */
	AVLNode<T> insert(T data, double value);

	/**
	 * deletes a given node from this tree. optional operation; doesn't do
	 * anything if no node is found
	 * @param value the value of the node to delete
	 * @return the tree's new root
	 */
	AVLNode<T> delete(double value);
}
