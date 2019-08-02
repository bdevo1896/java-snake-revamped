package cs120.bdevaughn.snake.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cs120.bdevaughn.snake.Score;

public class ScoreTests {

	@Test
	public void testGeneration() {
		Score s = new Score("Bryce",3000);
		assertTrue("Bryce".equals(s.getName()));
		assertTrue(3000 == s.getScore());
	}
	
	@Test
	public void testToString(){
		Score s = new Score("Bryce",3000);
		
		assertTrue(s.toString(),"Bryce: 3000".equals(s.toString()));
	}
	
	@Test
	public void testToWrite(){
		Score s = new Score("Bryce",3000);
		
		assertTrue("Bryce 3000".equals(s.toWrite(' ')));
	}

}
