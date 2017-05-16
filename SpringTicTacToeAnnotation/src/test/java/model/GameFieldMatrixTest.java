package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GameFieldMatrixTest {
     private  GameFieldMatrix field;
	@Before
	public void init(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(GameFieldMatrix.class);
		ctx.refresh();
	 field = ctx.getBean(GameFieldMatrix.class);
	}
	@Test
	public void testConstractorgGameFieldSize15() {
		//for(int i = 10; i< 20;i++){
		  assertEquals("In constractor set size 10 ",10, field.getSize());
		 
		   //assertEquals(   "Expected - " + i + " Got " + field.getGameFieldSize(),i,field.getGameFieldSize());	
	}

	@Test
	public void initGameFieldMatrixTest(){
		Point p0= new Point(0,0);
		field.initGameFieldMatrix();
	     assertEquals(CellType.EMPTY,field.getCellType(p0));
		int fieldSize= field.getSize() ;
		assertEquals("Got type " + field.getCellType(p0),CellType.EMPTY,field.getCellType(new Point(fieldSize-1,fieldSize-1)));
		
	}
}
