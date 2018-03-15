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

public class Doubles {
	private Doubles() {}

	/**
	 * about 2^-52
	 *
	 * NOTE: since chess elo ratings go up to at max about 3600, a good epsilon
	 * for chess would be about 3600 * epsilon = 7.99361e-13
	 */
	public static final double EPSILON =  2.22045e-16;

	/**
	 * precision of double at x
	 * @param x
	 * @return the difference between x and the next double number
	 */
	public static final double epsilon(double x) {
		return 2 * EPSILON * x;
	}

	/**
	 * compares two doubles to precision epsilon
	 */
	public static final boolean equals(double a, double b, double epsilon) {
		return Math.abs(a - b) < epsilon;
	}

	/**
	 * compares two doubles to precision EPSILON = 2^-52
	 */
	public static final boolean equals(double a, double b) {
		return equals(a, b, EPSILON);
	}
}
