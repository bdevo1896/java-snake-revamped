package cs120.bdevaughn.snake;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * This is the base class for all of the mushrooms. Each subclass will spawn a different effect depend-
 * ing on which type is collided into.
 * @author Bryce DeVaughn
 *
 */
public abstract class Mushroom extends Rectangle{

	protected ImageIcon img;

	public Mushroom(int x, int y, int width, int height, ImageIcon img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}
	
	/**
	 * This method will act upon the player (the snake) colliding into a mushroom (consuming it).
	 * @param gfx
	 */
	public abstract void whenConsumed(Controller c, Snake s);
	
//	public void drawOn(Graphics gfx){
//		gfx.drawImage(img.getImage(),x,y,null);
//	}
	
	public abstract void drawOn(Graphics gfx);

	public ImageIcon getImage() {
		return img;
	}

	public void setImage(ImageIcon img) {
		this.img = img;
	}
	
	

}
