package cs120.bdevaughn.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * This class will be used for the rendering, collision detection, and movement of the snake's indivi-
 * dual segments.
 * @author Bryce DeVaughn
 *
 */
public class Segment extends Rectangle {
	
	private Segment prevSeg;//A handle on a Segment object in a chain previous to this instance
	private ImageIcon img;//A handle on an image, will be the image of the segment

	public Segment(Segment prevSeg,int x, int y, int width, int height, ImageIcon img) {
		this.prevSeg = prevSeg;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}
	
	/**
	 * This move method will bring the segment to where the previous segment is, if there is one.
	 * @return
	 */
	public void move(){
		Segment s = getPrevSeg();
		if(s!=null){
			x = s.x;
			y = s.y;
		}
	}

	public Segment getPrevSeg() {
		return prevSeg;
	}

	public void setPrevSeg(Segment prevSeg) {
		this.prevSeg = prevSeg;
	}

	public ImageIcon getImage() {
		return img;
	}

	public void setImg(ImageIcon img) {
		this.img = img;
	}
	
	public void drawOn(Graphics gfx){
		if(img != null){
		gfx.drawImage(img.getImage(),x,y,null);
		}else{
			gfx.setColor(new Color(0,255,150));
			gfx.fillRect(x, y, width, height);
			gfx.setColor(Color.BLACK);
			gfx.drawRect(x, y, width, height);
		}
	}

}
