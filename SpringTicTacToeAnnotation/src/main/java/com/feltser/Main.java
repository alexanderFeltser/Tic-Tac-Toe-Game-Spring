package com.feltser;

import gameview.GameFieldView;
import gameview.GameProperties;
import model.GameFieldData;
import model.Model;
import model.TicTAcToeNextMoveAlgorithm;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import controller.GameController;

public class Main {
	public static void main(String[] args) {
		// ApplicationContext ctx = new
		// AnnotationConfigApplicationContext(GameProperties.class,
		// GameFieldData.class);
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(GameProperties.class);
		ctx.register(GameFieldData.class);
		ctx.register(GameFieldView.class);
		ctx.register(GameController.class);
		ctx.register(TicTAcToeNextMoveAlgorithm.class);
	//	ctx.register(SpringTicTacToeAnnotationApplicationTests.class);
		ctx.refresh();

		// GameProperties prop = ctx.getBean(GameProperties.class);
		Model model = ctx.getBean(GameFieldData.class);
		model.setGameFieldMatrixSize(16);
		GameFieldView view = ctx.getBean(GameFieldView.class);
		// Controller controller = ctx.getBean(GameController.class);
		view.initGameField();
	}
}
