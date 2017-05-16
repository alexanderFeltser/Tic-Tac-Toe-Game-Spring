package model;

/**
 * Class Point - coordinates of the cell on game field.<br>
 * Used for transferring of cell location between MVC components
 *
 */
public class Point {
	public final int x;
	public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}

		if (!(that instanceof Point)) {
			return false;
		}

		Point p = (Point) that;

		return this.x == p.x && this.y == p.y;
	}
}
