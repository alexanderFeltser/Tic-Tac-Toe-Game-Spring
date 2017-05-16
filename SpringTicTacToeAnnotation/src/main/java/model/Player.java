package model;

/**
 * Class Player used to store player data<br>
 * Name, player figure (X or 0) is it person or computer.
 */
public class Player {
	private String name;
	private CellType playerFigure;
	private boolean isComputer;

	public Player(String name, CellType playerFigure, boolean isComputer) {

		this.name = name;
		this.isComputer = isComputer;
		if (playerFigure == CellType.EMPTY) {
			throw new IllegalArgumentException("Player Figure can't be EMPTY");
		}
		this.playerFigure = playerFigure;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final CellType getPlayerFigure() {
		return playerFigure;
	}

	public final void setPlayerFigure(CellType playerFigure) {
		this.playerFigure = playerFigure;
	}

	public boolean isComputer() {
		return isComputer;
	}
}
