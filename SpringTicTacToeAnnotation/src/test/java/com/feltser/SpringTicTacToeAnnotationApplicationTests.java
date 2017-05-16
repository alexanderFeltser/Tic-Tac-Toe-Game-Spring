package com.feltser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootConfiguration
//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@SpringApplicationConfiguration(SpringTicTacToeAnnotationApplicationTests.class)
public class SpringTicTacToeAnnotationApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println("Test works");
	}
	
	@Test
	public void test() {
	    assertEquals(15, 15);
	}


}
