package model;

public enum CellType {
	Zero(0), X(1), EMPTY(-1), WIN(2);
	private final int value;

	CellType(int id) {
		value = id;
	}

	public int getValue() {
		return value;
	}
}
