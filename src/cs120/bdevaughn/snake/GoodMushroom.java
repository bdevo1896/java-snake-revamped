package cs120.bdevaughn.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * This mushroom will be used to add a segment to the snake and other functions.
 * @author Bryce DeVaughn
 *
 */
public class GoodMushroom extends Mushroom {

	public GoodMushroom(int x, int y, int width, int height, ImageIcon img) {
		super(x, y, width, height, img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void whenConsumed(Controller c, Snake s) {
		c.onGoodShroomCollision();
		//s.addSegment();
		
	}

	@Override
	public void drawOn(Graphics gfx) {
		if(img != null){
			gfx.drawImage(img.getImage(), x, y, null);
		}else{
			gfx.setColor(Color.red);
			gfx.fillRect(x, y, width, height);
			gfx.setColor(Color.BLACK);
			gfx.drawRect(x, y, width, height);
		}
	}
	
	

}
