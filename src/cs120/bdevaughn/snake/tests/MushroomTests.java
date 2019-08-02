package cs120.bdevaughn.snake.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cs120.bdevaughn.snake.BadMushroom;
import cs120.bdevaughn.snake.Controller;
import cs120.bdevaughn.snake.GoodMushroom;
import cs120.bdevaughn.snake.WildMushroom;
import cs120.bdevaughn.snake.WoodMushroom;

public class MushroomTests {

	@Test
	public void testGeneration() {
		BadMushroom bm = new BadMushroom(0,0,100,100,null);
		

		assertTrue(bm.getX()==0.0);
		assertTrue(bm.getY()==0.0);
		assertTrue(bm.getWidth()==100);
		assertTrue(bm.getHeight()==100);
		assertTrue(bm.getImage()==null);
		
	}
	
	@Test
	public void testBadMushroom(){
		BadMushroom bm = new BadMushroom(0,0,100,100,null);
		
		Controller c = new Controller(null);
		c.getPlayer().x = 0;
		
		bm.whenConsumed(c, c.getPlayer());
		
		assertTrue(c.getPlayer().x == 400 && c.getPlayer().y == 400);

	}
	
	@Test
	public void testGoodMushroom(){
		GoodMushroom gm = new GoodMushroom(0,0,100,100,null);
		Controller c = new Controller(null);
		
		gm.whenConsumed(c, c.getPlayer());
		
		assertTrue(c.getPlayer().getSegments()==2);
	}
	
	@Test
	public void testWildMushroom(){
		WildMushroom wm = new WildMushroom(0,0,100,100,null);
		Controller c = new Controller(null);
		
		int beforeMoveSpeed = c.getMoveSpeed();
		
		wm.whenConsumed(c,c.getPlayer());
		
		//assertTrue(c.getMoveSpeed()==beforeMoveSpeed*2);
	}
	
	@Test
	public void testWoodMushroom(){
		WoodMushroom wm = new WoodMushroom(0,0,100,100,null);
		Controller c = new Controller(null);
		
		wm.whenConsumed(c,c.getPlayer());
		
		//assertTrue(c.isControllable()==false);
	}

}
