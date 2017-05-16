package gameview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Model;
import controller.Controller;

class MenuItemListener implements ActionListener, KeyListener {
	private Model model;
	private Controller controller;
	private View view;

	/**
	 * @param model
	 * @param controller
	 * @param view
	 */
	public MenuItemListener(Model model, Controller controller, View view) {

		this.model = model;
		this.controller = controller;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "SET_FIELD_SIZE":
		//	System.out.println("SET_FIELD_SIZE" + "pressed");
			break;
		case "New game":
		//	System.out.println("New game" + " Pressed");

			controller.newGame();
			view.initGameField();
			break;
		case "Exit":

			view.closeGameField();
			break;

		case "WIN_SEQUENCE_LENGTH":
		//	System.out.println("WIN_SEQUENCE_LENGTH" + "pressed");
			break;

		case "Win Sequance 4":
			model.setWinSequinceLength(4);
			view.initGameField();
			break;
		case "Win Sequance 5":
			model.setWinSequinceLength(5);
			view.initGameField();
			break;
		case "Win Sequance 6":
			model.setWinSequinceLength(6);
			view.initGameField();
			break;
		case "3 X 3":
			model.setGameFieldMatrixSize(3);
			view.initGameField();
			break;
		case "4 X 4":
			model.setGameFieldMatrixSize(4);
			view.initGameField();
			break;
		case "5 X 5":
			model.setGameFieldMatrixSize(5);
			view.initGameField();
			break;
		case "6 X 6":
			model.setGameFieldMatrixSize(6);
			view.initGameField();
			break;
		case "10 X 10":
			model.setGameFieldMatrixSize(10);
			view.initGameField();
			break;
		case "15 X 15":
			model.setGameFieldMatrixSize(15);
			view.initGameField();
			break;
		case "20 X 20":
			model.setGameFieldMatrixSize(20);
			view.initGameField();
			break;

		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println(arg0.paramString());

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}