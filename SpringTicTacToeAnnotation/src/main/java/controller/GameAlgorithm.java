package controller;

import model.CellType;
import model.Model;
import model.Point;

public interface GameAlgorithm {
	public Point nextMove(Model model, CellType myCellType);

}
