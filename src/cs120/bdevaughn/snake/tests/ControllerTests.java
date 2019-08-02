package cs120.bdevaughn.snake.tests;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.io.File;

import org.junit.Test;

import cs120.bdevaughn.snake.Controller;
import cs120.bdevaughn.snake.Mushroom;
import cs120.bdevaughn.snake.Score;

public class ControllerTests {

	@Test
	public void testGeneration() {
		Controller c = new Controller(null);

		assertTrue(c.getLives()==2);
		assertTrue(c.getMoveSpeed()==4);
		assertTrue(c.getMoveTimer()!=null);
		assertTrue(c.getShroomTimer()!=null);
		assertTrue(c.getMushrooms()!=null);
		assertTrue(c.getPlayer()!=null && c.getPlayer().getTail()!=null);
		assertTrue(c.isControllable()==true);
	}

	@Test
	public void testKillPlayer(){
		Controller c = new Controller(null);

		/*
		 * Adding 5 more segments to the player
		 */
		for(int i = 0; i < 5; i++){
			c.getPlayer().addSegment();
		}
		assertTrue(c.getPlayer().getSegments()==6);

		c.getPlayer().setDx(200);
		c.getPlayer().move();

		c.killSnake();

		assertTrue("X: "+c.getPlayer().x+" Y: "+c.getPlayer().y,c.getPlayer().x == 400 && c.getPlayer().x == 400);
		assertTrue(c.getPlayer().getSegments()==1);
		assertTrue(c.getLives()==1);

	}

	@Test
	public void testPlaceMushroom(){
		Controller c = new Controller(null);

		c.placeMushroom();
		c.placeMushroom();
		c.placeMushroom();

		assertTrue(c.getMushrooms().size()+"",c.getMushrooms().size()==3);

		for(Mushroom m: c.getMushrooms()){
			assertTrue(m.x < Controller.getMaxx() && m.x >= 0);
		}
	}

	@Test
	public void testKeys(){
		Controller c = new Controller(null);

		c.onKeyPress(KeyEvent.VK_A);
		assertTrue(c.getPlayer().getDx()==-1 && c.getPlayer().getDy()==0);

		c.onKeyPress(KeyEvent.VK_W);
		assertTrue(c.getPlayer().getDx()==0 && c.getPlayer().getDy()==-1);

		c.onKeyPress(KeyEvent.VK_S);
		assertTrue(c.getPlayer().getDx()==0 && c.getPlayer().getDy()==1);

		c.onKeyPress(KeyEvent.VK_D);
		assertTrue(c.getPlayer().getDx()==1 && c.getPlayer().getDy()==0);
	}

	@Test
	public void testWriteToFile(){
		Controller c = new Controller(null);

		Score b = new Score("Bryce",3000);

		c.getScores().add(b);

		c.writeScores("highScores.txt");

		File f = new File("highScores.txt");

		assertTrue(f!=null);
	}

	@Test
	public void testReadIn(){
		Controller c = new Controller(null);

		Score b = new Score("Bryce",3000);

		c.getScores().add(b);

		c.writeScores("highScores.txt");

		Controller newC = new Controller(null);

		newC.readScores("highScores.txt");

		assertTrue(newC.getScores().size()==1);
	}

	@Test
	public void testOrganizeScores(){
		Controller c = new Controller(null);
		Score a = new Score("Bryce",5000);
		Score b = new Score("Pierce",10000);
		Score d = new Score("Colin",2500);

		c.getScores().add(a);
		c.getScores().add(b);
		c.getScores().add(d);

		c.organizeScores();

		for(int i = 0; i < c.getScores().size()-1; i++){
			if(c.getScores().get(i).getScore()<c.getScores().get(i+1).getScore()){
				fail("Not correctly organized."+c.getScores());
			}
		}
	}
	
	@Test
	public void testAddScore(){
		Controller c = new Controller(null);
		for(int i = 0; i < 11; i++){
			c.addNewScore(new Score("Dummy",10000),"highScores.txt");
		}
		
		assertTrue(c.getScores().size()+"",c.getScores().size()==10);
	}

}
