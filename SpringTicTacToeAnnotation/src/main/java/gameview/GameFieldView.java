package gameview;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import model.CellType;
import model.GameFieldData;
import model.GameFieldMatrix;
import model.Model;
import model.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import controller.Controller;
import controller.GameController;

/**
 * Graphic representation of the game field.
 *
 */
@Configuration
public class GameFieldView extends JPanel implements View {

	/**
	 *
	 */
	private static final long serialVersionUID = 23423L;
	private Controller controller;
	private GameProperties properties;
	private JPanel panel;
	private JPanel[][] cellPanelHolder;
	private JFrame frame;
	private Model model;
	private GridLayout gridLayOut;
	private boolean isXTurn;
	private MenuItemListener menuItemListener;
	private boolean isFreezeCells;

	public GameFieldView(GameProperties prop, Model model) throws IOException {
		properties = prop;
		this.model = model;
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		setFirstPlayerCellType(CellType.X);
		// addNewPanel();

	}

	private void setFirstPlayerCellType(CellType cellType) {
		if (cellType == CellType.X) {
			isXTurn = true;
		} else {
			isXTurn = false;
		}
	}

	private void nextTurn() {
		isXTurn = !isXTurn;
	}

	private void addNewPanel() {
		frame.setVisible(false);
		frame = new JFrame();// frame.removeAll();

		panel = new JPanel();
		panel.removeAll();

		frame.add(panel, BorderLayout.SOUTH);
	}

	@Autowired
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
		menuItemListener = new MenuItemListener(model, controller, this);

	}

	/*
	 * private void testField() { int size = controller.getGameFieldSize();
	 * JPanel panel = new JPanel(); panel.setLayout(new GridLayout(size, size));
	 * for (int i = 0; i < size; i++) { for (int j = 0; j < size; j++) { if ((i
	 * + j) % 3 == 0) { panel.add(new Cell(new Point(i, j),
	 * properties.getEmptyIconeFileName())); continue; } add(((i + j) % 2 == 0)
	 * ? new Cell(new Point(i, j), properties.getoIconFileName()) : new Cell(new
	 * Point( i, j), properties.getxIconFileName())); } } }
	 */
	@Override
	public void initGameField() {
		int size = model.getGameFieldSize();
		isFreezeCells = false;
		setFirstPlayerCellType(CellType.X);
		addNewPanel();
		gridLayOut = new GridLayout(size, size);
		gridLayOut.setHgap(0);
		panel.setLayout(gridLayOut);

		cellPanelHolder = new JPanel[size][size];
		// frame.setVisible(false);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				cellPanelHolder[i][j] = new JPanel();
				cellPanelHolder[i][j].add(new Cell(new Point(i, j), properties.getEmptyIconeFileName(), this));
				panel.add(cellPanelHolder[i][j]);
				// System.out.println("init" + cellPanelHolder[i][j]);
			}
		}
		JMenu menu = new JMenu("Menu");
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu);
		frame.setJMenuBar(setMenuBar());
		// frame.setVisible(false);
		frame.pack();
		frame.revalidate();

		frame.repaint();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);

	}

	// @Override
	// public void showWinCombination(Point[] points) {
	// // TODO Auto-generated method stub
	//
	// }

	@Override
	public void closeGameField() {
		pullThePlug();

	}

	@Override
	public void onCellPressed(Point p) {
		if (isFreezeCells || model.getCellType(p) != CellType.EMPTY) {
			return;
		}
		markCellOnGameField(p, isXTurn ? CellType.X : CellType.Zero);
		this.controller.onCellPressed(p);

	}

	@Override
	public void markCellOnGameField(Point p, CellType cellType) {
		if (isFreezeCells) {
			return;
		}

		Cell cell = new Cell(p, properties.getIconFileByCellType(cellType), this);

		cellPanelHolder[p.x][p.y].removeAll();
		cellPanelHolder[p.x][p.y].add(cell);
		nextTurn();
		// cellPanelHolder[p.x][p.y].revalidate();
		// cellPanelHolder[p.x][p.y].repaint();
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void markCellsOnGameField(ArrayList<Point> pointSequance, CellType cellType) {
		for (Point p : pointSequance) {
			Cell cell = new Cell(p, properties.getIconFileByCellType(cellType), this);

			cellPanelHolder[p.x][p.y].removeAll();
			cellPanelHolder[p.x][p.y].add(cell);
			// cellPanelHolder[p.x][p.y].revalidate();
			// cellPanelHolder[p.x][p.y].repaint();
		}
		if (cellType == CellType.WIN) {
			isFreezeCells = true;
		}
		frame.pack();
		frame.setVisible(true);
	}

	// @Override
	public void drowGameField(GameFieldMatrix gameFieldMatrix) {
		int size = model.getGameFieldSize();
		addNewPanel();
		gridLayOut = new GridLayout(size, size);
		panel.setLayout(gridLayOut);
		cellPanelHolder = new JPanel[size][size];

		Cell cell;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cellPanelHolder[i][j] = new JPanel();
				if (gameFieldMatrix.getCellType(new Point(i, j)) == CellType.X) {
					cell = new Cell(new Point(i, j), properties.getxIconFileName(), this);
				} else if (gameFieldMatrix.getCellType(new Point(i, j)) == CellType.Zero) {
					cell = new Cell(new Point(i, j), properties.getZeroIconFileName(), this);
				} else if (gameFieldMatrix.getCellType(new Point(i, j)) == CellType.WIN) {
					cell = new Cell(new Point(i, j), properties.getWinIconFileName(), this);
				} else {
					cell = new Cell(new Point(i, j), properties.getEmptyIconeFileName(), this);
				}
				cellPanelHolder[i][j].add(cell);
				// cellPanelHolder[i][j].revalidate();
				// cellPanelHolder[i][j].repaint();
				panel.add(cellPanelHolder[i][j]);
			}
		}

		frame.setJMenuBar(setMenuBar());
		frame.pack();
		frame.setVisible(true);
	}

	private JMenuBar setMenuBar() {
		JMenu gameModeMenu = new JMenu("Game Mode");

		JMenuItem newGameItem = new JMenuItem("New game");
		newGameItem.setActionCommand("New game");
		newGameItem.addActionListener(menuItemListener);
		gameModeMenu.add(newGameItem);

		JMenu fieldSizeMenu = setFieldSizeMenu();
		JMenu winSequenceLengthMenu = setWinSequenceMenu();

		fieldSizeMenu.addActionListener(menuItemListener);
		winSequenceLengthMenu.addActionListener(menuItemListener);

		gameModeMenu.add(fieldSizeMenu);

		gameModeMenu.add(winSequenceLengthMenu);
		JMenu menu2 = new JMenu("Players");

		JMenu menu3 = new JMenu("Exit");
		JMenuItem menu3Item = new JMenuItem("Exit");
		menu3Item.setActionCommand("Exit");
		menu3.add(menu3Item);

		menu3Item.addActionListener(menuItemListener);

		JLabel winSequenceLable = new JLabel();
		Font font = new Font(Font.DIALOG, Font.BOLD, 16);
		winSequenceLable.setFont(font);

		winSequenceLable.setText("  Win seq: " + model.getWinSequinceLength());

		JMenuBar menuBar = new JMenuBar();

		menuBar.add(gameModeMenu);
		menuBar.add(menu2);
		menuBar.add(menu3);

		menuBar.add(winSequenceLable);
		return menuBar;
	}

	private JMenu setWinSequenceMenu() {
		JMenu winSequenceLengthMenu = new JMenu("Win Sequence Length");
		winSequenceLengthMenu.setActionCommand("WIN_SEQUENCE_LENGTH");

		// Set WinSequence menu

		ButtonGroup winSeqRadioButtonGroup = new ButtonGroup();
		JRadioButtonMenuItem winSeqmenuItem4 = new JRadioButtonMenuItem("Win Sequance 4");
		winSeqRadioButtonGroup.add(winSeqmenuItem4);
		winSequenceLengthMenu.add(winSeqmenuItem4);
		winSeqmenuItem4.addActionListener(menuItemListener);

		JRadioButtonMenuItem winSeqmenuItem5 = new JRadioButtonMenuItem("Win Sequance 5");
		winSeqRadioButtonGroup.add(winSeqmenuItem5);
		winSequenceLengthMenu.add(winSeqmenuItem5);
		winSeqmenuItem5.addActionListener(menuItemListener);

		JRadioButtonMenuItem winSeqmenuItem6 = new JRadioButtonMenuItem("Win Sequance 6");
		winSeqRadioButtonGroup.add(winSeqmenuItem6);
		winSequenceLengthMenu.add(winSeqmenuItem6);
		winSeqmenuItem6.addActionListener(menuItemListener);

		return winSequenceLengthMenu;
	}

	private JMenu setFieldSizeMenu() {
		JMenu fieldSizeMenu = new JMenu("Field Size");
		fieldSizeMenu.setActionCommand("SET_FIELD_SIZE");

		ButtonGroup fieldSizeGroup = new ButtonGroup();

		JRadioButtonMenuItem menuItemFieldSize3 = new JRadioButtonMenuItem("3 X 3");
		fieldSizeGroup.add(menuItemFieldSize3);
		fieldSizeMenu.add(menuItemFieldSize3);
		menuItemFieldSize3.addActionListener(menuItemListener);

		JRadioButtonMenuItem menuItemFieldSize4 = new JRadioButtonMenuItem("4 X 4");
		fieldSizeGroup.add(menuItemFieldSize4);
		fieldSizeMenu.add(menuItemFieldSize4);
		menuItemFieldSize4.addActionListener(menuItemListener);

		JRadioButtonMenuItem menuItemFieldSize5 = new JRadioButtonMenuItem("5 X 5");
		fieldSizeGroup.add(menuItemFieldSize5);
		fieldSizeMenu.add(menuItemFieldSize5);
		menuItemFieldSize5.addActionListener(menuItemListener);
		JRadioButtonMenuItem menuItemFieldSize6 = new JRadioButtonMenuItem("6 X 6");
		fieldSizeGroup.add(menuItemFieldSize6);
		fieldSizeMenu.add(menuItemFieldSize6);
		menuItemFieldSize6.addActionListener(menuItemListener);

		JRadioButtonMenuItem menuItemFieldSize10 = new JRadioButtonMenuItem("10 X 10");
		fieldSizeGroup.add(menuItemFieldSize10);
		fieldSizeMenu.add(menuItemFieldSize10);
		menuItemFieldSize10.addActionListener(menuItemListener);

		JRadioButtonMenuItem menuItemFieldSize15 = new JRadioButtonMenuItem("15 X 15");
		fieldSizeGroup.add(menuItemFieldSize15);
		fieldSizeMenu.add(menuItemFieldSize15);
		menuItemFieldSize15.addActionListener(menuItemListener);

		JRadioButtonMenuItem menuItemFieldSize20 = new JRadioButtonMenuItem("20 X 20");
		fieldSizeGroup.add(menuItemFieldSize20);
		fieldSizeMenu.add(menuItemFieldSize20);
		menuItemFieldSize20.addActionListener(menuItemListener);
		//
		// fieldSizeMenu.addSeparator();
		// JTextArea fieldSizeArea = new JTextArea();
		// fieldSizeArea.addKeyListener(menuItemListener);
		// fieldSizeMenu.add(fieldSizeArea);

		return fieldSizeMenu;
	}

	public void pullThePlug() {
		// this will make sure WindowListener.windowClosing() et al. will be
		// called.
		WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);

		// this will hide and dispose the frame, so that the application quits
		// by
		// itself if there is nothing else around.
		frame.setVisible(false);
		frame.dispose();
		// if you have other similar frames around, you should dispose them,
		// too.

		// finally, call this to really exit.
		// i/o libraries such as WiiRemoteJ need this.
		// also, this is what swing does for JFrame.EXIT_ON_CLOSE
		System.exit(0);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		GameProperties properties = new GameProperties();
		Model model = new GameFieldData();
		View view = new GameFieldView(properties, model);
		Controller controller = new GameController(view, model);
		view.setController(controller);
		// controller.initGameField();

		model.setGameFieldMatrixSize(5);
		view.initGameField();
		// view.drowGameField(model.getGameFieldMatrix());
	}
}
