package gameview;

import java.io.IOException;

import model.GameFieldData;
import model.Model;
import model.TicTAcToeNextMoveAlgorithm;
import controller.Controller;
import controller.GameController;

/**
 * All functionality of the View Component supported by View interface.
 * Functionality of the Model component supported by Model interface.
 * Functionality of Controller supported by Controller Component interface
 * Controller interacts with Model and View. View use Functionality of
 * Controller.
 *
 */
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		GameProperties properties = new GameProperties();

		Model model = new GameFieldData();
		View view = new GameFieldView(properties, model);
		Controller controller = new GameController(view, model);
		controller.setGameAlgorithm(new TicTAcToeNextMoveAlgorithm());
		view.setController(controller);
		// controller.initGameField();
		// view.drowGameField(controller.getGameFieldMatrix());

		// Thread.sleep(3000);
		// controller.setFieldSize(5);
		model.setGameFieldMatrixSize(15);
		view.initGameField();
		// view.drowGameField(model.getGameFieldMatrix());

	}
}
