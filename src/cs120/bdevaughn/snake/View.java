package cs120.bdevaughn.snake;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 * This class will be used to show the game and the all of the other GUI elements in it. This class will control the speed of the
 * painting and will be able to control the pausing and startin of the game by delegating to the Controller
 * @author Bryce DeVaughn
 *
 */
public class View extends JFrame implements ActionListener{
	private Timer paintTimer;//Timer for the painting
	private static final int PAINT_RATE = 15;//The number of times per second for repainting
	private Controller c;//Controls all of the game's mechanics
	private DisplayPanel dPanel;//Paints all of the artifacts for the game
	private PauseMenu pMenu;//A frame that shows the scores and instructions for the game
	private ScoreListModel sModel;//Used to display the list of scores
	
	public View(){
		c = new Controller(this);
		c.readScores("scores.txt");
		dPanel = new DisplayPanel(c);
		paintTimer = new Timer(1000/PAINT_RATE,this);
		sModel = new ScoreListModel();
		setTitle("Snake");
		setMinimumSize(new Dimension(Controller.getMaxx()+17,Controller.getMaxy()+115));
		setMaximumSize(new Dimension(Controller.getMaxx()+17,Controller.getMaxy()+115));
		setVisible(true);
		setFocusable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(c.getkH());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		/*
		 *Setting up a panel to instruct the user on what buttons he/she can press. Also shows the mushrooms with the number of points they give.
		 */
		JPanel instructionPanel = new JPanel();
		instructionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		addLegendItem(new ImageIcon("qButton.png"),"= Quit",instructionPanel);
		addLegendItem(new ImageIcon("pButton.png"),"= Pause",instructionPanel);
		addLegendItem(new ImageIcon("mushroom_bad.png"),"= DEATH",instructionPanel);
		addLegendItem(new ImageIcon("mushroom_high.png"),"= 300",instructionPanel);
		addLegendItem(new ImageIcon("mushroom_wood.png"),"= 400",instructionPanel);
		addLegendItem(new ImageIcon("mushroom_good.png"),"= 200+(50 per Segment)",instructionPanel);
		
		mainPanel.add(instructionPanel,BorderLayout.NORTH);
		
		mainPanel.add(dPanel,BorderLayout.CENTER);
		
		paintTimer.start();
		
		this.getContentPane().add(mainPanel);
		this.pack();
		
		pMenu = new PauseMenu();
		showPauseMenu();
	}
	
	public static void main(String[] args){
		View v = new View();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	/**
	 * This method will show the pause menu when the player presses the 'P' key.
	 */
	public void showPauseMenu(){
		pMenu.setVisible(true);
		sModel.announceChange();
	}
	
	/**
	 * This method will create a legend item, which is an image with a message by it.
	 */
	public void addLegendItem(ImageIcon img, String des, JPanel targetPnl){
		JLabel pic = new JLabel(img);
		JLabel description = new JLabel(des);
		targetPnl.add(pic);
		targetPnl.add(description);
	}
	
	/**
	 * This class will be the pause menu for the game.
	 */
	private class PauseMenu extends JFrame{
		public PauseMenu(){
			setTitle("Pause Menu");
			setMinimumSize(new Dimension(Controller.getMaxx()/2,Controller.getMaxy()/2));
			setMaximumSize(new Dimension(Controller.getMaxx()/2,Controller.getMaxy()/2));
			setVisible(false);
			setFocusable(true);
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			
			/*
			 * Adding a instruction picture
			 */
			JPanel iPanel = new JPanel();
			JLabel instructLbl = new JLabel(new ImageIcon("instructPage.png"));
			iPanel.add(instructLbl);
			mainPanel.add(iPanel);
			
			/*
			 * Adding the score display
			 */
			JPanel scorePnl = new JPanel();
			scorePnl.setLayout(new BorderLayout());
			JList<String> scoreList = new JList<String>(sModel);
			JScrollPane scoreDisplay = new JScrollPane(scoreList);
			scorePnl.add(scoreDisplay,BorderLayout.CENTER);
			
			JLabel scoreLbl = makeLbl("High Scores");
			scorePnl.add(scoreLbl,BorderLayout.NORTH);
			
			/*
			 * Adding a button in the score panel to add the score to the list
			 */
			mainPanel.add(scorePnl,BorderLayout.EAST);
			
			/*
			 * Adding a button panel to close the pause menu
			 */
			JPanel btnPanel = new JPanel();
			btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton closeBtn = new JButton("Close");
			closeBtn.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					onClose();
				}
			});
			btnPanel.add(closeBtn);
			mainPanel.add(btnPanel,BorderLayout.SOUTH);
			
			this.getContentPane().add(mainPanel);
			this.pack();
		}
		
		/**
		 * This method will return a handle on a modified JLabel object with select preferences.
		 */
		public JLabel makeLbl(String text){
			JLabel rtnLbl = new JLabel(text);
			rtnLbl.setFont(new Font("Arial",Font.BOLD,15));
			return rtnLbl;
		}
		
		/**
		 * This method will start the game up when the frame closes.
		 */
		public void onClose(){
			setVisible(false);
			c.startUp();
		}
	}
	
	/**
	 * This class will contain help show the list of scores
	 */
	private class ScoreListModel extends AbstractListModel{

		@Override
		public Object getElementAt(int arg0) {
			return c.getScores().get(arg0);
		}

		@Override
		public int getSize() {
			return c.getScores().size();
		}
		
		public void announceChange(){
			this.fireContentsChanged(this, 0, c.getScores().size());
		}
	}
}
