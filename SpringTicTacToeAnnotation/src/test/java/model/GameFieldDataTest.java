package model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import gameview.GameProperties;

public class GameFieldDataTest {

	private Model model;
	@Mock
	private GameFieldMatrix field;
	@Before
	public void init(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(GameFieldData.class);
		ctx.refresh();
		 model = ctx.getBean(GameFieldData.class);
		 field= mock(GameFieldMatrix.class);
		 
		 
	}
	@Test
	public void testGameFieldSize() {
		for(int i = 10; i< 20;i++){
		   model.setGameFieldMatrixSize(i);
		 
		   assertEquals(   "Expected - " + i + " Got " + model.getGameFieldSize(),i,model.getGameFieldSize());
		}		
	}
	
	@Test
	public void testWinSequinceLength() {
		for(int i = 4; i< 6;i++){
		 model.setWinSequinceLength(i);
		   assertEquals("Expected - " + i + " Got " + model.getWinSequinceLength(),i,model.getWinSequinceLength());
		}			
	}
	
	@Test
	public void isValidPointMockoTest(){
		Point p = new Point(0,0);
		GameFieldData gamFieldData = (GameFieldData)model;
		gamFieldData.setGameField(field);
		when(field.getSize()).thenReturn(9);
		assertTrue(gamFieldData.isValidPoint(p));
		 p = new Point(9,2);
		 assertFalse(gamFieldData.isValidPoint(p));
		 p = new Point(1,9);
		 assertFalse(gamFieldData.isValidPoint(p));		 
	}
	

}
