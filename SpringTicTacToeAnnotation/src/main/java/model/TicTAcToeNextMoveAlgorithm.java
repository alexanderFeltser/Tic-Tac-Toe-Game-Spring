package model;

import gameview.GameFieldView;
import gameview.GameProperties;

import java.io.IOException;
import java.util.ArrayList;

import controller.GameAlgorithm;

public class TicTAcToeNextMoveAlgorithm implements GameAlgorithm {

	private class BestMoveSet {
		public int sequence;
		public ArrayList<Point> points;

		private BestMoveSet() {
			points = new ArrayList<>();
			sequence = 0;
		}

		public void changeBestSet(int sequence, Point p) {
			if (this.sequence < sequence) {
				this.sequence = sequence;
				points.clear();
				points.add(p);
			} else if (this.sequence == sequence) {
				points.add(p);
			}
		}
	}

	@Override
	public Point nextMove(Model model, CellType myCellType) {
		Point bestPoint;
		CellType partnerCellType = getPartnerCellType(myCellType);
		BestMoveSet myBestSet = findBestMoveSet(model, myCellType);
		BestMoveSet partnerBestSet = findBestMoveSet(model, partnerCellType);

		if (myBestSet.sequence == partnerBestSet.sequence) {
			bestPoint = getRundomIntersectionPoint(myBestSet, partnerBestSet);
		} else if (myBestSet.sequence > partnerBestSet.sequence) {
			bestPoint = getRundomMyBestSetPoints(myBestSet);
		} else {
			bestPoint = getRundomMyBestSetPoints(partnerBestSet);
		}

		return bestPoint;
	}

	private Point getRundomMyBestSetPoints(BestMoveSet bestSet) {
		int index = bestSet.points.size() / 3 + (int) (Math.random() * bestSet.points.size() / 3);
		return bestSet.points.get(index);
	}

	private Point getRundomIntersectionPoint(BestMoveSet myBestSet, BestMoveSet partnerBestSet) {
		// get array intersection of myBestSet.points & partnerBestSet.points in
		// myBestSet.points
		ArrayList<Point> tempArray = new ArrayList<>();
		tempArray.addAll(myBestSet.points);

		tempArray.retainAll(partnerBestSet.points);
		if (tempArray.size() > 0) {
			myBestSet.points.clear();
			myBestSet.points.addAll(tempArray);
		}
		// compute index of rundom elemnt in myBestSet.points as 1/3 of
		// array size + Rundom of (1/3 of array )
		if (partnerBestSet.sequence > 1 && partnerBestSet.points.size() == 1) {
			return partnerBestSet.points.get(0);
		}

		int index = myBestSet.points.size() / 3 + (int) (Math.random() * myBestSet.points.size() / 3);
		return myBestSet.points.get(index);
	}

	private BestMoveSet findBestMoveSet(Model model, CellType cellType) {
		BestMoveSet bestSet = new BestMoveSet();

		for (int i = 0; i < model.getGameFieldSize(); i++) {
			for (int j = 0; j < model.getGameFieldSize(); j++) {
				Point p = new Point(i, j);
				if (model.getCellType(p) == CellType.EMPTY) {
					bestSet.changeBestSet(getPositiveDiagonalSequencePoint(model, p, cellType), p);
					bestSet.changeBestSet(getNegotiveDiagonalSequencePoint(model, p, cellType), p);
					bestSet.changeBestSet(getGorizontalSequencePoint(model, p, cellType), p);
					bestSet.changeBestSet(getVerticalSequencePoint(model, p, cellType), p);
				}
			}
		}
		return bestSet;
	}

	private int getPositiveDiagonalSequencePoint(Model model, Point p, CellType cellType) {
		int sequence = 0;
		Point currientPoint;
		boolean isEndPointEmpty = false;
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x + i, p.y + i);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == CellType.EMPTY) {
					isEndPointEmpty = true;
				}
				break;
			}
		}

		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x - i, p.y - i);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (isEndPointEmpty && model.isValidPoint(currientPoint)
						&& model.getCellType(currientPoint) == CellType.EMPTY) {
					sequence++;
				}
				break;
			}
		}

		return sequence;
	}

	private int getGorizontalSequencePoint(Model model, Point p, CellType cellType) {
		int sequence = 0;
		Point currientPoint;
		boolean isEndPointEmpty = false;
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x + i, p.y);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == CellType.EMPTY) {
					isEndPointEmpty = true;
				}
				break;
			}
		}
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x - i, p.y);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (isEndPointEmpty && model.isValidPoint(currientPoint)
						&& model.getCellType(currientPoint) == CellType.EMPTY) {
					sequence++;
				}
				break;
			}
		}
		return sequence;
	}

	private int getVerticalSequencePoint(Model model, Point p, CellType cellType) {
		int sequence = 0;
		boolean isEndPointEmpty = false;
		Point currientPoint;
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x, p.y + i);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (isEndPointEmpty && model.isValidPoint(currientPoint)
						&& model.getCellType(currientPoint) == CellType.EMPTY) {
					isEndPointEmpty = true;
				}
				break;
			}
		}
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x, p.y - i);
			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (isEndPointEmpty && model.isValidPoint(currientPoint)
						&& model.getCellType(currientPoint) == CellType.EMPTY) {
					sequence++;
				}
				break;
			}
		}
		return sequence;
	}

	private int getNegotiveDiagonalSequencePoint(Model model, Point p, CellType cellType) {
		int sequence = 0;
		boolean isEndPointEmpty = false;
		Point currientPoint;

		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x - i, p.y + i);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == CellType.EMPTY) {
					isEndPointEmpty = true;
				}
				break;
			}
		}
		for (int i = 1; i < model.getWinSequinceLength(); i++) {
			currientPoint = new Point(p.x + i, p.y - i);

			if (model.isValidPoint(currientPoint) && model.getCellType(currientPoint) == cellType) {
				sequence++;
			} else {
				if (isEndPointEmpty && model.isValidPoint(currientPoint)
						&& model.getCellType(currientPoint) == CellType.EMPTY) {
					sequence++;
				}
				break;
			}
		}
		return sequence;
	}

	private CellType getPartnerCellType(CellType myCellType) {
		CellType partnerCellType = CellType.X;
		if (myCellType == CellType.X) {
			partnerCellType = CellType.Zero;
		}

		return partnerCellType;
	}

	public static void main(String[] args) throws IOException {
		GameProperties properties = new GameProperties();
		GameFieldData model = new GameFieldData();
		model.setGameFieldMatrixSize(5);
		model.setCellValue(new Point(2, 2), CellType.X);
		// model.setCellValue(new Point(3, 4), CellType.X);
		// model.setCellValue(new Point(1, 3), CellType.X);
		// model.setCellValue(new Point(1, 4), CellType.Zero);
		// model.setCellValue(new Point(2, 4), CellType.Zero);
		// model.setCellValue(new Point(2, 2), CellType.X);
		GameFieldView view = new GameFieldView(properties, model);
		view.drowGameField(model.getGameFieldMatrix());

		TicTAcToeNextMoveAlgorithm algorithm = new TicTAcToeNextMoveAlgorithm();
		Point p = algorithm.nextMove(model, CellType.X);
		System.out.println(p);
		model.setCellValue(p, CellType.WIN);
		view.drowGameField(model.getGameFieldMatrix());
	}
}
