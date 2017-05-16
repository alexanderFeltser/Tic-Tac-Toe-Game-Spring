package gameview;

import java.util.ArrayList;

import model.CellType;
import model.Point;
import controller.Controller;

/**
 * This interface will be used by controller and internal components of the View<br>
 * and implemented by GUI . Controller implements all functionality which user
 * can choose from GUI(New GAME , configuration, next move)
 */

public interface View {

	public void initGameField();

	// public void showWinCombination(Point[] points);

	public void closeGameField();

	public void setController(Controller controller);

	public void markCellsOnGameField(ArrayList<Point> pointSequance, CellType cellType);

	public void markCellOnGameField(Point p, CellType cellType);

	/**
	 * Pass coordinates of pressed Cell from Cell to Controller through View.
	 *
	 */
	public void onCellPressed(Point point);

}
