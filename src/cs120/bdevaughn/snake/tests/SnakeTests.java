package cs120.bdevaughn.snake.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cs120.bdevaughn.snake.Segment;
import cs120.bdevaughn.snake.Snake;

public class SnakeTests {

	@Test
	public void testGeneration() {
		Snake s = new Snake(null,10,10,100,100,null,1000,1000,null);
		assertTrue(s.getTail()==null);
		assertTrue(s.getDx()==0);
		assertTrue(s.getDy()==0);
		assertTrue(s.isOutOfBounds()==false);
		assertTrue(s.getSegImg()==null);
	}
	
	@Test
	public void testOutOfBounds(){
		Snake s = new Snake(null,10,10,100,100,null,1000,1000,null);
		/*
		 * Move snake 900 to the right
		 */
		s.x = (int)s.getX()+1000;//completely out of bounds
		
		assertTrue(s.isOutOfBounds()==true);
		
		/*
		 * Move snake back 50, should still be out of bounds
		 */
		s.x = (int)s.getX()-50;
		
		assertTrue(s.isOutOfBounds()==true);
	}
	
	@Test
	public void testMove(){
		Snake s = new Snake(null,10,10,100,100,null,1000,1000,null);
		s.setDx(5);
		s.setDy(5);
		
		s.move();
		
		assertTrue(s.x+"",s.x == 510);
		assertTrue(s.y+"",s.y == 510);
	}
	
	@Test
	public void testAddSegment(){
		Snake s = new Snake(null,500,500,100,100,null,1000,1000,null);
		s.setDx(1);
		s.setDy(1);
		s.addSegment();
		
		assertTrue(s.getSegments() == 1);
		
		Segment tail = s.getTail();
		
		assertTrue(tail.x+"",tail.x == 399);
		assertTrue(tail.y+"",tail.y == s.y);
	}
	
	@Test
	public void testClearSegments(){
		Snake s = new Snake(null,500,500,100,100,null,1000,1000,null);
		s.addSegment();
		s.addSegment();
		s.addSegment();
		
		s.clearSegments();
		
		assertTrue(s.getSegments()+"",s.getSegments()==0);
		assertTrue(s.getTail()==null);
	}

}
