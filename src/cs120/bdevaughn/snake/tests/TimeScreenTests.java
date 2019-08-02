package cs120.bdevaughn.snake.tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import cs120.bdevaughn.snake.DayTimes;
import cs120.bdevaughn.snake.TimeScreen;

public class TimeScreenTests {

	@Test
	public void testGeneration() {
		TimeScreen ts = new TimeScreen(0,0,0,1000,1000,DayTimes.Dawn);
		
		assertTrue(ts.getR() == 0);
		assertTrue(ts.getG() == 0);
		assertTrue(ts.getB() == 0);
		assertTrue(ts.getTime() == DayTimes.Dawn);
		
		Color c = ts.getColor();
		
		assertTrue(c.getRed() == 0);
		assertTrue(c.getGreen() == 0);
		assertTrue(c.getBlue() == 0);
	}
	
	@Test
	public void testDayChange(){
		TimeScreen ts = new TimeScreen(0,0,0,1000,1000,DayTimes.Dawn);
		
		ts.changeTime();
		
		assertTrue(ts.getR()+"",ts.getR() != 95);
		assertTrue(""+ts.getG(),ts.getG() != 102);
		assertTrue(""+ts.getB(),ts.getB() != 164);
	}

}
