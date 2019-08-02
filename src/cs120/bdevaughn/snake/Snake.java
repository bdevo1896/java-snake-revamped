package cs120.bdevaughn.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * This class will act as the head of the snake. Will move around based off of key presses and will 
 * keep track of the number of segments including a handle on the last segment, the tail.
 * @author Bryce DeVaughn
 *
 */
public class Snake extends Segment{
	private Segment tail;
	private int segments,dx,dy,boundX,boundY,speed;
	private ImageIcon segImg;
	private boolean canDie;


	public Snake(Segment prevSeg, int x, int y, int width, int height, ImageIcon img,int boundX, int boundY,ImageIcon segImg) {
		super(prevSeg, x, y, width, height, img);
		tail = null;
		segments = 0;
		speed = 1;
		dx = 0;
		dy = 0;
		this.boundX = boundX;
		this.boundY = boundY;
		this.segImg = segImg;
		canDie = true;
	}



	/**
	 * This will return a boolean based on the x and y positions being checked to see if they are 
	 * beyond or in bounds according the the bounded region.
	 * @return
	 */
	public boolean isOutOfBounds(){
		if( x < 0){
			return true;
		}else if(y < 0){
			return true;
		}else if((x+width) > boundX){
			return true;
		}else if((y+height) > boundY){
			return true;
		}
		return false;
	}

	/**
	 * This method will move the snake according to the direction in x and y
	 * @return
	 */
	@Override
	public void move(){
		if(tail!=null){
			Segment s = tail;
			while(s.getPrevSeg()!=null){
				s.move();
				s = s.getPrevSeg();
			}
		}
		x += dx * width;
		y += dy * height;

	}

	/**
	 * This method will add a segment object to the snake chain. This will make the tail point to the new-
	 * ly added segment.
	 * @return
	 */
	public void addSegment(){
		segments++;
		Segment s = null;

		/*
		 * Checking to see if there is already a tail
		 */
		if(tail!=null)
			s = new Segment(tail,x,y,width,height,segImg);
		else
			s = new Segment(this,x,y,width,height,segImg);

		tail = s;

		/*
		 * Checking to see if there is room on the x-axis or y-axis to place the new segment
		 */
		if(dx < 0){
			if(tail.x+(2*width) < boundX){
				s.x = tail.x+width+1;
			}
		}else if(dx > 0){
			if(tail.x-(2*width) > 0){
				s.x = tail.x-width-1;
			}
		}else if(dy < 0){
			if(tail.y+(2*height) < boundY){
				s.y = tail.y+height+1;
			}
		}else if(dy > 0){
			if(tail.y-(2*height) > 0){
				s.y = tail.y-height-1;
			}
		}
	}

	/**
	 * This method will clear all of the segments from the snake.
	 */
	public void clearSegments(){
		while(tail.getPrevSeg()!=null){
			Segment s = tail.getPrevSeg();
			tail.setPrevSeg(null);
			tail = s;
			segments--;
		}
		tail = null;
	}
	/**
	 * This draw method will go from the tail and draw up all of the segments including the head.
	 * @return
	 */
	@Override
	public void drawOn(Graphics gfx){
		super.drawOn(gfx);
		if(tail!=null){
			Segment s = tail;
			while(s.getPrevSeg()!=null){
				s.drawOn(gfx);
				if(!canDie){
					gfx.setColor(new Color(255,255,255,75));
					gfx.fillRect(s.x,s.y,s.width,s.height);
				}
				s = s.getPrevSeg();
			}
		}
		
		if(!canDie){
			gfx.setColor(new Color(255,255,255,75));
			gfx.fillRect(x,y,width,height);
		}
	}
	
	/**
	 * This method will make it so the player is not killable for a short amount of time.
	 */
	public void makeImmune(){
		Runnable run = new Runnable(){
			@Override
			public void run() {
				canDie = false;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				canDie = true;
			}
		};
		Thread thr = new Thread(run);
		thr.start();
	}

	/**
	 * This method will make the snake go left.
	 * @return
	 */
	public void turnLeft(){
		dx = -speed;
		dy = 0;
	}

	/**
	 * This method will make the snake go right.
	 * @return
	 */
	public void turnRight(){
		dx = speed;
		dy = 0;
	}

	/**
	 * This method will make the snake go up.
	 * @return
	 */
	public void turnUp(){
		dx = 0;
		dy = -speed;
	}

	/**
	 * This method will make the snake go down.
	 * @return
	 */
	public void turnDown(){
		dx = 0;
		dy = speed;
	}


	public Segment getTail() {
		return tail;
	}


	public void setTail(Segment tail) {
		this.tail = tail;
	}


	public int getDx() {
		return dx;
	}


	public void setDx(int dx) {
		this.dx = dx;
	}


	public int getDy() {
		return dy;
	}


	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getBoundX() {
		return boundX;
	}


	public void setBoundX(int boundX) {
		this.boundX = boundX;
	}


	public int getBoundY() {
		return boundY;
	}


	public void setBoundY(int boundY) {
		this.boundY = boundY;
	}


	public int getSegments() {
		return segments;
	}

	public ImageIcon getSegImg() {
		return segImg;
	}

	public void setSegImg(ImageIcon segImg) {
		this.segImg = segImg;
	}



	public int getSpeed() {
		return speed;
	}



	public void setSpeed(int speed) {
		this.speed = speed;
	}



	public boolean isCanDie() {
		return canDie;
	}



	public void setCanDie(boolean canDie) {
		this.canDie = canDie;
	}
	
	
}
