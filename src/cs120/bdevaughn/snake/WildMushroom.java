package cs120.bdevaughn.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * This mushroom will be used to speed the player up upon collision
 * @author Bryce DeVaughn
 *
 */
public class WildMushroom extends Mushroom {

	public WildMushroom(int x, int y, int width, int height, ImageIcon img) {
		super(x, y, width, height, img);
	}

	@Override
	public void whenConsumed(Controller c, Snake s) {
		c.onWildShroomCollision();
		//c.setMoveSpeed(c.getMoveSpeed()*2);
	}

	@Override
	public void drawOn(Graphics gfx) {
		if(img != null){
			gfx.drawImage(img.getImage(), x, y, null);
		}else{
			gfx.setColor(Color.yellow);
			gfx.fillRect(x, y, width, height);
			gfx.setColor(Color.BLACK);
			gfx.drawRect(x, y, width, height);
		}
		
	}

}
