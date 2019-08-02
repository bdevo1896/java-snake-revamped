package cs120.bdevaughn.snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This class will be used to display the game by painting all of the game objects
 * @author Bryce DeVaughn
 *
 */
public class DisplayPanel extends JPanel {
	private Controller c;
	private ImageIcon bground;
	private BufferedImage myImage;
	
	public DisplayPanel(Controller c) {
		this.c = c;
		bground = new ImageIcon("background.png");
		myImage = null;
	}

	@Override
	public synchronized void paint(Graphics gfx){
		if(myImage == null){
			myImage = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		}
		Graphics g;
		//Creates an image to allow for double buffering.
		g = myImage.getGraphics();
		
		if(bground!=null)
			g.drawImage(bground.getImage(),0,0,null);
		else{
			g.setColor(new Color(112,154,78));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		c.drawAll(g);

		if(c.isControllable()==false){
			Graphics2D g2 = (Graphics2D)g;
			BasicStroke b = new BasicStroke(3.0f);
			g2.setStroke(b);
			g2.setColor(new Color(156,41,218));
			g2.drawRect(c.getPlayer().x, c.getPlayer().y, c.getPlayer().width, c.getPlayer().height);
		}

		if(c.isWild()==true){
			Graphics2D g2 = (Graphics2D)g;
			BasicStroke b = new BasicStroke(3.0f);
			g2.setStroke(b);
			g2.setColor(Color.YELLOW);
			g2.drawRect(c.getPlayer().x, c.getPlayer().y, c.getPlayer().width, c.getPlayer().height);
		}

		g.setColor(new Color(0,0,0,100));
		g.fillRect(0, 0, 400, 50);

		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.drawString("Lives: "+c.getLives()+"  "+"Score: "+c.getScore()+"  Day: "+(c.getDays()+1),0,30);

		if(!c.isRunning()){
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, Controller.getMaxx(), Controller.getMaxy());
		}
		
		gfx.drawImage(myImage,0,0,this);
	}
	

}
