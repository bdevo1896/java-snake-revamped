package cs120.bdevaughn.snake;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * This class will control the game and the GUI. This class will hold handles to three different timers
 * , a snake object to act as the player, a list of mushrooms that will be used for checking, and many 
 * constants for further fucntion of the game.
 * @author User
 *
 */
public class Controller{

	private Snake player;//The player
	private ArrayList<Mushroom> mushrooms;//List of the mushrooms
	private ImageIcon[] snakeImages;
	private Timer shroomTimer,moveTimer,dayTimer;//The different timers for controlling the different aspects of the game
	private static final double BIRTH_RATE = .5;//The rate at which mushrooms will be planted.
	private boolean controllable,wild,running;//If the player can control the snake or not. Also check for if the player is wild (double speed)
	private int moveSpeed;//The rate at which the move timer will tick.
	private int lives;//The amount of times the snake can die
	private int score;//Stores the player's score
	private static final int MAXX = 800,MAXY = 800;//The max size of the of the boundaries for the frame
	private static final int GAIN_LIFE = 5;//The rate a which a life is granted for the growing number of segments (e.g. every 5 gained segments is one life)
	private static final int SHROOM_W = 50,SHROOM_H = 50;//The size of the mushroom images 
	private static final int DIFFICULTY_THRESHOLD = 3;//This number will be used to increase the difficulty for every said amount of segments are gained.
	private static final int SNAKE_H = 50,SNAKE_W = 50;//The size of the snake images
	private static final int WILD_EFFECT_TIME = 4000,WOOD_EFFECT_TIME = 1000;//The times foe the different effects
	private static final int GOOD_SHROOM_SCORE = 200,WILD_SHROOM_SCORE=300,WOOD_SHROOM_SCORE=400;//The different score factors for all of the mushrooms
	private static final int ORG_MOVE_SPEED = 4;
	private int gainLifeCounter = 0;//This will check to see snake has gained enough segments to see if the player gains a life
	private int diffCounter = 0;//This will be used to know when to increase the difficulty
	private int difficulty = 1;
	private ArrayList<Score> scores;//List containing all of the scores
	private TimeScreen day;//Used to draw out the color of the time in the game
	private int days;
	private static final int TIME_TICK = 15000;//Time for each time of day change
	private Random rand;
	private View view;
	private KeyHelper kH;//The object that will allow for the keyboard to be used

	public Controller(View view) {
		this.view = view;
		day = new TimeScreen(95,102,164,MAXX,MAXY,DayTimes.Dawn);//Starts at dawn
		days = 0;
		snakeImages = new ImageIcon[4];
		for(int i = 0; i < 4; i++){
			snakeImages[i] = new ImageIcon("snake_head"+(i+1)+".png");
		}

		player = new Snake(null,MAXX/2,MAXY/2,SNAKE_W,SNAKE_H,snakeImages[2],MAXX,MAXY,new ImageIcon("snake_segment.png"));
		player.addSegment();
		moveSpeed = ORG_MOVE_SPEED;
		mushrooms = new ArrayList<Mushroom>();
		scores = new ArrayList<Score>();
		shroomTimer = new Timer(2000,new ShroomActor());
		moveTimer = new Timer(1000/moveSpeed,new MoveActor());
		dayTimer = new Timer(TIME_TICK,new DayActor());
		lives  = 2;
		controllable = true;
		running = false;
		wild = false;
		rand = new Random();
		player.turnUp();
		kH = new KeyHelper();
	}

	/**
	 * This method will read in the file associated with the highscores and then will populate the scores list.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void readScores(String filename){
		File f = new File(filename);
		try(BufferedReader br = new BufferedReader(new FileReader(f))){
			String line = br.readLine();
			while(line != null){
				line = line.trim().replace("\\s+", "\\s");
				String[] parts = line.split("\\s");
				Score s = new Score(parts[0],Integer.parseInt(parts[1]));
				scores.add(s);
				line = br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		organizeScores();
	}

	/**
	 * This method will write the scores to a file
	 */
	public void writeScores(String filename){
		File f = new File(filename);//File containing the scores.
		try(PrintWriter pw = new PrintWriter(new FileWriter(f))){
			if(scores.size()>0){
				for(Score s: scores){
					pw.println(s.toWrite(' '));
				}
			}else{
				pw.print("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will kill the snake and take a life away.
	 */
	public synchronized void killSnake(){
		/*
		 * Clearing the snakes body
		 */
		player.clearSegments();
		player.addSegment();
		player.x = MAXX/2;
		player.y = MAXY/2;
		int dir = rand.nextInt(4);//Used to make the snake go a different direction
		switch(dir){
		case 0:
			player.turnLeft();
			player.setImg(snakeImages[1]);
			break;
		case 1:
			player.turnUp();
			player.setImg(snakeImages[2]);
			break;
		case 2:
			player.turnRight();
			player.setImg(snakeImages[3]);
			break;
		case 3:
			player.turnDown();
			player.setImg(snakeImages[0]);
		}
		lives--;
		gainLifeCounter = 0;
		diffCounter = 0;
		difficulty = 1;
		moveSpeed = ORG_MOVE_SPEED;
		player.makeImmune();
		gameOver();
	}

	/**
	 * This method will make the moveSpeed of the snake increase for 4 seconds
	 */
	public void onWildShroomCollision(){
		score += WILD_SHROOM_SCORE;
		int tempMoveSpeed = moveSpeed;
		Runnable run = new Runnable(){
			@Override
			public void run() {
				moveSpeed = moveSpeed * 400;
				wild  = true;
				try {
					Thread.sleep(WILD_EFFECT_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				wild = false;
				moveSpeed = tempMoveSpeed;
			}
		};

		Thread thr = new Thread(run);
		thr.start();
	}

	/**
	 * This method will make the user unable to control the snake for 4 seconds
	 */
	public void onWoodShroomCollision(){
		score += WOOD_SHROOM_SCORE;
		Runnable run = new Runnable(){
			@Override
			public void run() {
				controllable = false;
				try {
					Thread.sleep(WOOD_EFFECT_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				controllable = true;
			}
		};

		Thread thr = new Thread(run);
		thr.start();
	}

	/**
	 * This method will add a segment to the snake and increase lives for every 3 segments that were added. 
	 */
	public void onGoodShroomCollision(){
		score += GOOD_SHROOM_SCORE + (50*player.getSegments());
		player.addSegment();
		gainLifeCounter++;
		diffCounter++;
		/*
		 * This will check to see if the difficulty needs to increase.
		 */
		if(diffCounter == DIFFICULTY_THRESHOLD){
			difficulty++;
			moveSpeed = ORG_MOVE_SPEED * difficulty;
		}
		
		/*
		 * This will check to see if a life is gained.
		 */
		if(gainLifeCounter == GAIN_LIFE){
			lives++;
			gainLifeCounter = 0;
		}
	}

	/**
	 * This method will check for the collisions between the player and the mushrooms and the player and its segments.
	 */
	public synchronized void checkCollision(){
		if(player.isCanDie()){
			checkPlayerSegmentCollision();
			checkShroomSnakeCollision();
		}
		checkShroomCollision();
	}

	/**
	 * Checks collision of the snake and its segments
	 */
	public synchronized void checkPlayerSegmentCollision(){
		if(player.getTail()!=null){
			Segment s = player.getTail();
			while(s.getPrevSeg()!=null){
				if(player.intersects(s)){
					killSnake();
					return;
				}
				s = s.getPrevSeg();
			}
		}
	}

	/**
	 * Checks collision of mushrooms and the snake
	 */
	public synchronized void checkShroomSnakeCollision(){
		for(Mushroom m: mushrooms){
			if(m.intersects(player)){
				m.whenConsumed(this, player);
				mushrooms.remove(m);
				return;
			}
		}
	}

	/**
	 * Checks collision the mushrooms versus the other mushrooms.
	 */
	public synchronized void checkShroomCollision(){
		if(mushrooms.size()>1){
			for(int i = 0; i < mushrooms.size(); i++){
				for(int j = mushrooms.size()-1; j > i; j--){
					if(mushrooms.get(i).intersects(mushrooms.get(j))){
						mushrooms.remove(mushrooms.get(j));
					}
				}
			}
		}
	}

	/**
	 * This method will add a mushroom to the level
	 */
	public synchronized void placeMushroom(){
		int shroomType = rand.nextInt(10);
		int xPos = rand.nextInt(MAXX/SHROOM_W);//Finds an x that is within the boundaries
		int yPos = rand.nextInt(MAXY/SHROOM_H);//Finds a y that is within the boundaries

		switch(shroomType){
		case 0:
		case 1:
			BadMushroom bm = new BadMushroom(xPos*SHROOM_W,(yPos*SHROOM_H),SHROOM_W,SHROOM_H,new ImageIcon("mushroom_bad.png"));
			mushrooms.add(bm);
			break;
		case 2:
		case 3:
			WildMushroom wm = new WildMushroom(xPos*SHROOM_W,(yPos*SHROOM_H),SHROOM_W,SHROOM_H,new ImageIcon("mushroom_high.png"));
			mushrooms.add(wm);
			break;
		case 4:
		case 5:
			WoodMushroom woodm = new WoodMushroom(xPos*SHROOM_W,(yPos*SHROOM_H),SHROOM_W,SHROOM_H,new ImageIcon("mushroom_wood.png"));
			mushrooms.add(woodm);
			break;
		case 6:
		case 7:
		case 8:
		case 9:
			GoodMushroom gm = new GoodMushroom(xPos*SHROOM_W,(yPos*SHROOM_H),SHROOM_W,SHROOM_H,new ImageIcon("mushroom_good.png"));
			mushrooms.add(gm);
			break;
		}
	}

	/**
	 * This method will pause the game.
	 */
	public void pause(){
		stop();
		running = false;
		view.showPauseMenu();
	}

	/**
	 * This method will stop the game.
	 */
	public void stop(){
		shroomTimer.stop();
		moveTimer.stop();
		dayTimer.stop();
	}

	/**
	 * This method will pause the game.
	 */
	public void quit(){
		stop();
		/*
		 * Just in case if the user wanted to quit but had a highscore
		 */
		if(score > scores.get(scores.size()-1).getScore() || scores.size()<10){
			String name = JOptionPane.showInputDialog("New Highscore! Enter your name!");
			name = name.replaceAll("\\s+", "");
			addNewScore(new Score(name,score),"scores.txt");
		}

		int restart = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "Wait",JOptionPane.YES_NO_OPTION);

		switch(restart){
		case JOptionPane.YES_OPTION:
			restart();
			break;
		case JOptionPane.NO_OPTION:
			System.exit(0);
		}
	}

	/**
	 * This will reset everything for the game.
	 */
	public void restart(){
		running = true;
		lives = 2;
		mushrooms.clear();
		/*
		 * Clearing the snakes body
		 */
		player.clearSegments();
		player.addSegment();
		player.x = MAXX/2;
		player.y = MAXY/2;
		player.setDx(0);
		player.setDy(-1);
		player.setImg(snakeImages[2]);
		score = 0;
		days = 0;
		day.setTime(DayTimes.Dawn);
		startUp();
	}

	/**
	 * This method will run the game over sequence.
	 */
	public void gameOver(){
		if(lives==0){
			running = false;
			stop();
			if(score > scores.get(scores.size()-1).getScore() || scores.size()<10){
				String name = JOptionPane.showInputDialog("New Highscore! Enter your name!");
				name = name.replaceAll("\\s+", "");
				addNewScore(new Score(name,score),"scores.txt");
			}

			int restart = JOptionPane.showConfirmDialog(null, "Do you want to restart?", "Wait",JOptionPane.YES_NO_OPTION);
			switch(restart){
			case JOptionPane.YES_OPTION:
				restart();
				break;
			case JOptionPane.NO_OPTION:
				System.exit(0);
			}
		}
	}

	/**
	 * This method starts up the game again.
	 */
	public void startUp(){
		moveTimer.start();
		shroomTimer.start();
		dayTimer.start();
		running = true;
	}

	/**
	 * Adds a new score and then writes it to the file to be saved.
	 */
	public void addNewScore(Score s,String filename){
		if(scores.size()<10){
			scores.add(s);
			organizeScores();
			writeScores(filename);
		}else{
			scores.add(s);
			organizeScores();
			scores.remove(scores.size()-1);//Removes the eleventh element to ensure that there are only 10 top scores
			writeScores(filename);
		}
	}

	/**
	 * This method organizes the list of scores from greatest to least.
	 */
	public void organizeScores(){
		Score[] tempList = new Score[scores.size()];
		for(int i = 0; i < tempList.length; i++){
			tempList[i] = scores.get(i);
		}
		scores.clear();
		for(int i = tempList.length-1; i >= 0; i--){
			for(int j = 1; j <= i; j++){
				if(tempList[j-1].getScore() < tempList[j].getScore()){
					Score s = tempList[j-1];
					tempList[j-1] = tempList[j];
					tempList[j] = s;
				}
			}
		}

		for(int i = 0; i < tempList.length;i++){
			scores.add(tempList[i]);
		}
	}


	/**
	 * This method will draw all of the objects
	 */
	public void drawAll(Graphics gfx){
		for(Mushroom m: mushrooms){
			m.drawOn(gfx);
		}

		player.drawOn(gfx);

		day.drawOn(gfx);
	}
	
	/**
	 * This method will act upon the key press of the user. WASD will be used to move the snake around.
	 */
	public void onKeyPress(int e){

		if(controllable){
			/*
			 * Moves snake to the left
			 */
			if(e == KeyEvent.VK_A){
				player.turnLeft();
				player.setImg(snakeImages[1]);
			}

			/*
			 * Moves snake up
			 */
			if(e == KeyEvent.VK_W){
				player.turnUp();
				player.setImg(snakeImages[2]);
			}

			/*
			 * Moves snake down
			 */
			if(e == KeyEvent.VK_S){
				player.turnDown();
				player.setImg(snakeImages[0]);
			}

			/*
			 * Moves the snake to the right
			 */
			if(e == KeyEvent.VK_D){
				player.turnRight();
				player.setImg(snakeImages[3]);
			}
			
			/*
			 * Moves the snake with the arrow directions
			 */
			if(e == KeyEvent.VK_UP){
				player.turnUp();
				player.setImg(snakeImages[2]);
			}
			
			if(e == KeyEvent.VK_DOWN){
				player.turnDown();
				player.setImg(snakeImages[0]);
			}
			
			if(e == KeyEvent.VK_LEFT){
				player.turnLeft();
				player.setImg(snakeImages[1]);
			}
			
			if(e == KeyEvent.VK_RIGHT){
				player.turnRight();
				player.setImg(snakeImages[3]);
			}
		}

		if(e == KeyEvent.VK_SPACE){
			onGoodShroomCollision();
		}

		if(e == KeyEvent.VK_P){
			pause();
		}

		if(e == KeyEvent.VK_Q){
			quit();
		}
	}

	public Snake getPlayer() {
		return player;
	}

	public ArrayList<Mushroom> getMushrooms() {
		return mushrooms;
	}

	public Timer getShroomTimer() {
		return shroomTimer;
	}

	public Timer getMoveTimer() {
		return moveTimer;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed){
		this.moveSpeed = moveSpeed;
	}

	public int getLives() {
		return lives;
	}

	public static int getMaxx() {
		return MAXX;
	}

	public static int getMaxy() {
		return MAXY;
	}

	public boolean isControllable() {
		return controllable;
	}

	public void setControllable(boolean controllable) {
		this.controllable = controllable;
	}

	public int getScore() {
		return score;
	}

	public ArrayList<Score> getScores() {
		return scores;
	}

	public boolean isWild() {
		return wild;
	}

	public boolean isRunning(){
		return running;
	}

	public TimeScreen getDay() {
		return day;
	}

	public int getDays(){
		return days;
	}
	
	public KeyHelper getkH() {
		return kH;
	}



	/**
	 * This class will be used to act upon the mushroom timer.
	 */
	private class ShroomActor implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			double birth = rand.nextDouble();
			if(birth <= BIRTH_RATE){
				placeMushroom();
			}
		}

	}

	/**
	 * This class will move the snake
	 */
	public class MoveActor implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			player.move();
			if(player.isOutOfBounds()){
				killSnake();
			}
			checkCollision();
		}

	}

	/**
	 * This class will change the time of day every daytime tick
	 */
	private class DayActor implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			changeTime();
		}

		public void changeTime(){
			day.changeTime();
			if(day.getTime() == DayTimes.Dawn){
				days++;
			}
		}
	}
	
	/**
	 * This class will act as the key listener.
	 */
	private class KeyHelper implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			onKeyPress(e.getKeyCode());

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}
	}
}
