package model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Game Field Matrix contains all Cells with it types (X 0 empty or Win CELL(one
 * of the win sequence cells )).
 */
@Configuration
public class GameFieldMatrix {
	private CellType[][] gameField;
	private int size;

	public GameFieldMatrix(@Value("10") int size) {
		this.size = size;
		makeFieldSizeOf(size);
	}

	public CellType getCellType(Point p) {
		// System.out.println("Size " + gameField.length + "(" + p.y + "," + p.x
		// + ")");
		return gameField[p.y][p.x];
	}

	/**
	 * This functions supports resize of the Game Field.
	 *
	 */

	private void makeFieldSizeOf(int size) {
		gameField = new CellType[size][size];
	}

	public void initGameFieldMatrix() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				gameField[i][j] = CellType.EMPTY;
			}
		}
	}

	public void setCellType(Point p, CellType cellType) {
		this.gameField[p.y][p.x] = cellType;
	}

	public int getSize() {
		return size;
	}

	/**
	 * This functions supports resize of the Game Field.
	 *
	 */
	public final void setSize(int size) {
		this.size = size;
		makeFieldSizeOf(size);
		initGameFieldMatrix();
	}
}
