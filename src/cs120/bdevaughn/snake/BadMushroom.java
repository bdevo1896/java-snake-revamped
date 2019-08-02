package cs120.bdevaughn.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class BadMushroom extends Mushroom {

	public BadMushroom(int x, int y, int width, int height, ImageIcon img) {
		super(x, y, width, height, img);
	}

	@Override
	public void whenConsumed(Controller c, Snake s) {
			c.killSnake();
	}

	@Override
	public void drawOn(Graphics gfx) {
		if(img != null){
			gfx.drawImage(img.getImage(), x, y, null);
		}else{
			gfx.setColor(new Color(119,57,234));
			gfx.fillRect(x, y, width, height);
			gfx.setColor(Color.BLACK);
			gfx.drawRect(x, y, width, height);
		}
	}

}
