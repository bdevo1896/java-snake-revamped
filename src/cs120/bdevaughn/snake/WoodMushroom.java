package cs120.bdevaughn.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * This mushroom will be used to make the player lose control of the snake when collided into.
 * @author Bryce DeVaughn
 *
 */
public class WoodMushroom extends Mushroom {

	public WoodMushroom(int x, int y, int width, int height, ImageIcon img) {
		super(x, y, width, height, img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void whenConsumed(Controller c, Snake s) {
		//c.setControllable(false);
		c.onWoodShroomCollision();
	}

	@Override
	public void drawOn(Graphics gfx) {
		if(img != null){
			gfx.drawImage(img.getImage(), x, y, null);
		}else{
			gfx.setColor(new Color(133,100,58));
			gfx.fillRect(x, y, width, height);
			gfx.setColor(Color.BLACK);
			gfx.drawRect(x, y, width, height);
		}
	}

}
