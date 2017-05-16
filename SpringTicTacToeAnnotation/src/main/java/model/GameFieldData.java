package model;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GameFieldData implements Model {

	private GameFieldMatrix gameField;
	private int DEFAULT_GAME_MATRIX_SIZE = 3;
	private int winSequinceLength = 3;

	/**
	 * Returns Game Field with marked Cells.Matrix contains all Cells with it
	 * types.
	 */
	public GameFieldData() {

		gameField = new GameFieldMatrix(DEFAULT_GAME_MATRIX_SIZE);
		initFieldGame();

	}

	public void setGameField(GameFieldMatrix gameField) {
		this.gameField = gameField;
	}

	private void setDefaltWinSequenceLength() {
		if (gameField.getSize() > 3) {
			winSequinceLength = 3 + gameField.getSize() / 6;
		}
		if (gameField.getSize() == 3) {
			winSequinceLength = 3;
		}
	}

	@Override
	public final int getWinSequinceLength() {
		return winSequinceLength;
	}

	@Override
	public final void setWinSequinceLength(int winSequinceLength) {
		this.winSequinceLength = winSequinceLength;
	}

	public void initFieldGame() {
		gameField.initGameFieldMatrix();

	}

	@Override
	public void setCellValue(Point point, CellType figure) {
		if (point.x >= gameField.getSize() || point.y >= gameField.getSize()) {
			throw new IllegalArgumentException("Trying to set value out of Game Field, " + "field size ("
					+ gameField.getSize() + "," + gameField.getSize() + ")");
		}

		gameField.setCellType(point, figure);
	}

	@Override
	public CellType getCellType(Point point) {
		if (point.x >= gameField.getSize() || point.y >= gameField.getSize()) {
			throw new IllegalArgumentException("Trying to get value out of Game Field, " + "field size ("
					+ gameField.getSize() + "," + gameField.getSize() + ")");
		}
		return gameField.getCellType(point);
	}

	@Override
	public int getGameFieldSize() {

		return gameField.getSize();
	}

	// need for debug
	public GameFieldMatrix getGameFieldMatrix() {
		return gameField;
	}

	@Override
	public void setGameFieldMatrixSize(int size) {

		gameField.setSize(size);
		setDefaltWinSequenceLength();

	}

	@Override
	public boolean isValidPoint(Point p) {
		return (p.x >= 0 && p.x < gameField.getSize() && p.y < gameField.getSize() && p.y >= 0);

	}
}
