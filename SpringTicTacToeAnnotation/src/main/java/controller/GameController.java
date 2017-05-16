package controller;

import gameview.View;

import java.io.IOException;
import java.util.ArrayList;

import model.CellType;
import model.Model;
import model.Player;
import model.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

//import model.GameFieldData;

/**
 * Class Controller contains {@link ControllerToGui} - Vew component of MVC<br>
 * contains {@link Model} - Model component of MVC represents current position
 * of game in Array of {@link Point}<br>
 * fieldSize - size of the Game Field<br>
 * firstPlayer, secondPlayer - players data(Names, Players Figure )<br>
 * isFirstPlayerTurn - controls players moves, switch every time when any player
 * moves.<br>
 * gameAlgorithm- compute next Move for certain player and current combination
 *
 */
@Configuration
public class GameController implements Controller, Runnable {

	private Player firstPlayer;
	private Player secondPlayer;
	/**
	 * support passing turns from one player to another
	 */
	private boolean isFirstPlayerTurn;
	private GameAlgorithm gameAlgorithm;

	private Model model;
	private View view;

	public GameController(View view, Model model) {

		this.model = model;
		this.view = view;
		isFirstPlayerTurn = true;
		setFirstPlayerCellType(CellType.X);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void newGame() {
		model.setGameFieldMatrixSize(model.getGameFieldSize());
		isFirstPlayerTurn = true;
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCellPressed(Point p) {
		try {
			checkCellCoordinate(p);
		} catch (IOException e1) {
			return;
		}

		markCellInData(p);
		if (isWinCombination(p)) {
			treatWinSituation(p);
			return;
		}
		if (secondPlayer.isComputer() && !isFirstPlayerTurn) {
			Point partnerMovePoint = gameAlgorithm.nextMove(model, secondPlayer.getPlayerFigure());
			markCellInData(partnerMovePoint);
			view.markCellOnGameField(partnerMovePoint, secondPlayer.getPlayerFigure());
			if (isWinCombination(partnerMovePoint)) {
				treatWinSituation(partnerMovePoint);
				return;
			}
		}

		// CellType cellType = isFirstPlayerTurn ? firstPlayer.getPlayerFigure()
		// : secondPlayer.getPlayerFigure();
		// view.markCellOnGameField(p, cellType);
	}

	private void treatWinSituation(Point p) {
		ArrayList<Point> winSequance = getMaximalSequence(p);
		view.markCellsOnGameField(winSequance, CellType.WIN);
	}

	/**
	 * Defines maximal sequence of same type cell passing through the giving
	 * point.There are a for types of sequence Vertical, Horizontal and two
	 * diagonals.
	 */
	private ArrayList<Point> getMaximalSequence(Point p) {
		ArrayList<Point> pointsSequence;
		ArrayList<Point> maximalSequence;
		maximalSequence = getGorizontalSequence(p);

		pointsSequence = getVerticalSequence(p);
		if (pointsSequence.size() >= maximalSequence.size()) {
			maximalSequence = pointsSequence;
		}
		pointsSequence = getPozitiveDioganalSequence(p);
		if (pointsSequence.size() >= maximalSequence.size()) {
			maximalSequence = pointsSequence;
		}

		pointsSequence = getNegotiveDioganalSequence(p);
		if (pointsSequence.size() >= maximalSequence.size()) {
			maximalSequence = pointsSequence;
		}
		return maximalSequence;
	}

	/**
	 * Defines maximal sequence of same type cell laying on diagonal passing
	 * throw argument Point p(angle between coordinate axes X and diagonal > 90
	 * )
	 */

	private ArrayList<Point> getNegotiveDioganalSequence(Point p) {
		ArrayList<Point> pointsSequence = new ArrayList<>();
		pointsSequence.add(p);
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x - i, p.y + i);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x + i, p.y - i);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}

		return pointsSequence;
	}

	/**
	 * Defines maximal sequence of same type cell laying on diagonal passing
	 * throw argument Point p(angle between coordinate axes X and diagonal < 90
	 * )
	 */

	private ArrayList<Point> getPozitiveDioganalSequence(Point p) {
		ArrayList<Point> pointsSequence = new ArrayList<>();
		pointsSequence.add(p);
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x + i, p.y + i);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x - i, p.y - i);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}
		return pointsSequence;
	}

	/**
	 * Defines maximal sequence of same type cell laying on Vertical straight
	 * passing trough argument Point p
	 */

	private ArrayList<Point> getVerticalSequence(Point p) {
		ArrayList<Point> pointsSequence = new ArrayList<>();
		pointsSequence.add(p);
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x, p.y + i);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x, p.y - i);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}
		return pointsSequence;
	}

	/**
	 * Defines maximal sequence of same type cell laying on Gorizontal straight
	 * passing trough argument Point p
	 */
	private ArrayList<Point> getGorizontalSequence(Point p) {
		ArrayList<Point> pointsSequence = new ArrayList<>();
		pointsSequence.add(p);
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x + i, p.y);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			Point currientPoint = new Point(p.x - i, p.y);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == model.getCellType(p)) {
				pointsSequence.add(currientPoint);
			} else {
				break;
			}
		}

		return pointsSequence;
	}

	private boolean isWinCombination(Point p) {
		return (getMaximalSequence(p).size() >= model.getWinSequinceLength());
	}

	private void markCellInData(Point p) {
		CellType cellType = isFirstPlayerTurn ? firstPlayer.getPlayerFigure() : secondPlayer.getPlayerFigure();
		markCellInModel(p, cellType);

		isFirstPlayerTurn = !isFirstPlayerTurn;
	}

	private void markCellInModel(Point p, CellType cellType) {
		model.setCellValue(p, cellType);
	}

	private void checkCellCoordinate(Point p) throws IOException {
		if (p.x >= model.getGameFieldSize() || p.y >= model.getGameFieldSize()) {
			throw new IllegalArgumentException("Wrong Cell cordinate gotten ");
		}
		if (model.getCellType(p) != CellType.EMPTY) {

			throw new IOException("This cell is allready marked");
		}
	}

	public void setFirstPlayerCellType(CellType cellType) {
		// TODO Auto-generated method stub
		this.firstPlayer = new Player("First", cellType, false);
		if (cellType == CellType.X) {
			secondPlayer = new Player("Second", CellType.Zero, true);
		} else {
			secondPlayer = new Player("Second", CellType.X, true);
		}
	}

	@Autowired
	@Override
	public void setGameAlgorithm(GameAlgorithm gameAlgorithm) {
		this.gameAlgorithm = gameAlgorithm;
	}
}
