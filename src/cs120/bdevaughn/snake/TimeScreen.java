package cs120.bdevaughn.snake;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class will be used to simulate the color of the time of day in the day/night cycle of the game.
 * @author Bryce DeVaughn
 *
 */
public class TimeScreen {
	private int r,g,b,pnlWidth,pnlHeight;
	private DayTimes time;//An enum that will be used to know what time it is
	private static final int TRANSPARENCY = 90;//This will be used to show how much the color of the time will look

	public TimeScreen(int r, int g, int b, int pnlWidth, int pnlHeight,DayTimes time) {
		this.r = r;
		this.b = b;
		this. g = g;
		this.pnlWidth = pnlWidth;
		this.pnlHeight = pnlHeight;
		this.time = time;
	}

	/**
	 * This method will change the color representing a time of day.
	 */
	public void changeTime(){
		int newR = 0,newG = 0,newB = 0;
		switch(time){
		case Dawn:
			newR = 215;
			newG = 219;
			newB = 151;
			time = DayTimes.Midday;
			break;
		case Midday:
			newR = 222;
			newG = 117;
			newB = 97;
			time = DayTimes.Evening;
			break;
		case Evening:
			newR = 79;
			newG = 56;
			newB = 184;
			time = DayTimes.Night;
			break;
		case Night:
			newR = 95;
			newG = 102;
			newB = 164;
			time = DayTimes.Dawn;
		}
		
		final int fR = newR;
		final int fG = newG;
		final int fB = newB;

		Runnable runR = new Runnable(){
			@Override
			public void run() {
				while(r != fR){
					if(r > fR){
						r--;
					}else if(r < fR){
						r++;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		Runnable runG = new Runnable(){
			@Override
			public void run() {
				while(g != fG){
					if(g > fG){
						g--;
					}else if(g < fG){
						g++;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		Runnable runB = new Runnable(){

			@Override
			public void run() {
				while(b != fB){
					if(b > fB){
						b--;
					}else if(b < fB){
						b++;
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		};

		Thread thrR = new Thread(runR);
		Thread thrG = new Thread(runG);
		Thread thrB = new Thread(runB);
		
		thrR.start();
		thrG.start();
		thrB.start();
	}

	public void drawOn(Graphics gfx){
		gfx.setColor(new Color(r,g,b,TRANSPARENCY));
		gfx.fillRect(0, 0, pnlWidth, pnlHeight);
	}

	public DayTimes getTime(){
		return time;
	}

	public Color getColor(){
		return new Color(r,g,b);
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getPnlWidth() {
		return pnlWidth;
	}

	public int getPnlHeight() {
		return pnlHeight;
	}

	public void setTime(DayTimes time) {
		this.time = time;
	}

	

}
