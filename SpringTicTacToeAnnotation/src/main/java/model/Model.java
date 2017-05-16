package model;

/**
 ** This interface will be used by Controller and will be implemented by
 * {@link GameFieldData} component.<br>
 * GUI implements all functionality which need to support controller commands<br>
 * such as (Write new field, mark Cell as X-Cell or 0-Cell )<br>
 * CloseGameField when user choose to exit
 *
 */
public interface Model {

	void setGameFieldMatrixSize(int size);

	//
	// GameFieldMatrix getGameFieldMatrix();
	void setWinSequinceLength(int winSequinceLength);

	int getGameFieldSize();

	void setCellValue(Point point, CellType win);

	int getWinSequinceLength();

	boolean isValidPoint(Point currientPoint);

	CellType getCellType(Point currientPoint);

}
