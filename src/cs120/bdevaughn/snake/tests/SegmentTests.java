package cs120.bdevaughn.snake.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cs120.bdevaughn.snake.Segment;

public class SegmentTests {

	@Test
	public void testGeneration() {
		Segment s = new Segment(null,10,10,100,100,null);
		
		assertTrue(s.getPrevSeg()==null);
		assertTrue(s.getX()==10.0);
		assertTrue(s.getY()==10.0);
		assertTrue(s.getWidth()==100);
		assertTrue(s.getHeight()==100);
		assertTrue(s.getImage()==null);
		
	}
	
	@Test
	public void testMove(){
		Segment s = new Segment(null,10,10,100,100,null);
		
		s.move();
		
		assertTrue(s.x == 10);
		assertTrue(s.y == 10);
		
		s.setPrevSeg(new Segment(null,200,200,100,100,null));
		
		s.move();
		
		assertTrue(s.x == 200);
		assertTrue(s.y == 200);
		
	}

}
