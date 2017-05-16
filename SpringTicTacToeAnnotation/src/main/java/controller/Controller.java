package controller;

import model.Point;

/**
 ** This interface will be used by Controller and will be implemented by GUI
 * component.<br>
 * GUI implements all functionality which need to support controller commands<br>
 * such as (Write new field, mark Cell as X-Cell or 0-Cell )<br>
 * CloseGameField when user choose to exit
 *
 *
 */

public interface Controller {

	public void onCellPressed(Point p);

	public void newGame();

	public void exit();

	void setGameAlgorithm(GameAlgorithm gameAlgorithm);

}
