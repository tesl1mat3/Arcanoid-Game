import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Icon;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GameFrame implements KeyListener, Runnable{
	
	static ArrayList <Laser> laserArray = new ArrayList<Laser>();
	static ArrayList <Brick> brickArray = new ArrayList<Brick>();
	static ArrayList <PowerUp> powerUpArray = new ArrayList<PowerUp>();
	
	public static Ball ball = new Ball();
	public static PlayerPallet player = new PlayerPallet();;
	static AudioClip bounceSound;
	private Thread thread = new Thread(this);
	
	static Icon livesLeftImage = new ImageIcon(GameFrame.class.getResource("PlayerLives.png"));
	
	public static JLabel scoreLabel = new JLabel("Score : 0"),
						 levelLabel = new JLabel(),
						 livesLabel1 = new JLabel(livesLeftImage), 
						 livesLabel2 = new JLabel(livesLeftImage), 
						 livesLabel3 = new JLabel(livesLeftImage);
	
	static boolean gameIsRunning = true, 
				   laserRemains = false, 
				   laserReady = false,
				   releaseBall = false;

	private boolean controlFlag,
					rightFlag, 
					leftFlag, 
					qFlag;
	
	public static int playerScore = 0, 
	  				  livesLeft = 3;
	
	public static int brickCount;
	
	public GameFrame(){

		
		URL bounce = GameFrame.class.getResource("bounce.wav");
		bounceSound = Applet.newAudioClip(bounce);	
		
		
		scoreLabel.setBounds(15, 10, 200, 30);
		scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
		scoreLabel.setForeground(Color.WHITE);
		
		levelLabel.setBounds(330, 10, 200, 30);
		levelLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
		levelLabel.setForeground(Color.WHITE);
		
		livesLabel1.setBounds(605, 10, livesLeftImage.getIconWidth(), livesLeftImage.getIconHeight());
		livesLabel2.setBounds(650, 10, livesLeftImage.getIconWidth(), livesLeftImage.getIconHeight());
		livesLabel3.setBounds(695, 10, livesLeftImage.getIconWidth(), livesLeftImage.getIconHeight());
		
		Main.background.add(scoreLabel);
		Main.background.add(levelLabel);
		Main.background.add(livesLabel1);
		Main.background.add(livesLabel2);
		Main.background.add(livesLabel3);
	
		Main.MenuFrame.addKeyListener(this);

		thread.start();
	}
	
	@Override
	public void run() {		
			Map();
			while (gameIsRunning)	{	
				if (controlFlag == true && qFlag == true)
					System.exit(0);		
				playerPalletMovement();
				ball.move();
				System.out.println(ball.brickDestroyed);
				System.out.println(brickCount);
				for(int i = 0; i < powerUpArray.size(); i++)	{
					powerUpArray.get(i).move();
				}
				for(int j = 0; j < laserArray.size(); j++)	{
					laserArray.get(j).move();
				}
				Main.background.repaint();	
				try {				
					Thread.sleep(10);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}	
				if (ball.brickDestroyed == brickCount){
					if (Main.difficulty == 3){
						gameIsRunning = false;
						writeScore();
						System.exit(0);	
					}
					ball.xCordinate = 360;
					ball.yCordinate = 300;
					Main.difficulty++;
					Map();
				}
		}
	}

	public void playerPalletMovement() {		
		if ((leftFlag) && (player.xCordinate >= 10))		
			player.xCordinate -= 6;		
		if (((rightFlag) && (player.xCordinate <= 628)) && (!PowerUp.paddleControl))
			player.xCordinate += 6;
		else if ((rightFlag) && (player.xCordinate <= 575))
			player.xCordinate += 6;
	
		player.unitLabel.setBounds(player.xCordinate, player.yCordinate, player.unitImage.getIconWidth(), player.unitImage.getIconHeight());
	}
	
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_LEFT)
			leftFlag = true;	
		if(event.getKeyCode() == KeyEvent.VK_RIGHT)
			rightFlag = true;		
		if(event.getKeyCode() == KeyEvent.VK_CONTROL)
			controlFlag = true;	
		if(event.getKeyCode() == KeyEvent.VK_Q)
			qFlag = true;	
	}

	public void keyReleased(KeyEvent event) {	
		if(event.getKeyCode() == KeyEvent.VK_LEFT)
			leftFlag = false;
		if(event.getKeyCode() == KeyEvent.VK_RIGHT)
			rightFlag = false;
		if(event.getKeyCode() == KeyEvent.VK_CONTROL)
			controlFlag = false;			
		if(event.getKeyCode() == KeyEvent.VK_Q)
			qFlag = false;	
		if((event.getKeyCode() == KeyEvent.VK_SPACE )&& (laserReady))	{
			Laser laser = new Laser(GameFrame.player.xCordinate+20, GameFrame.player.yCordinate-10);
		}
		if(event.getKeyCode() == KeyEvent.VK_SPACE){
			Ball.collideControl = false;
			PowerUp.magnetControl = false;
			ball.yCordinate -= 10;
		}
	}

	public void keyTyped(KeyEvent event) {}

	public static void Map()	{
		int brickNumber, xCor = 20, yCor = 70, type = 0;
		
		if (Main.difficulty == 1 || Main.difficulty == 3)		
			type = 1;
		
		if (Main.difficulty == 0 || Main.difficulty == 1)		
			
			for (brickNumber = 55; brickNumber > 0;  brickNumber--)	{
				
				brickCount ++;
				Brick brick = new Brick(xCor, yCor, type);	
				brickArray.add(brick);
				xCor += 65;
		
				if (xCor > 700)	{			
					yCor += 40;
					xCor = 20;
				}		
			}	
		
		else if (Main.difficulty == 2 || Main.difficulty == 3)	
			
			for (brickNumber = 60; brickNumber > 0;  brickNumber--)	{	
				
				brickCount ++;
				Brick brick = new Brick(xCor, yCor, type);	
				brickArray.add(brick);			
				xCor += 35;
				if (type != 2)
					xCor += 30;
				
				if (Main.difficulty == 2)
					type = 0;
				if (Main.difficulty == 3)
					type = 1;

				if ((xCor == 215) || (xCor == 510))	
					type = 2;
				
				if (xCor > 700)	{
					xCor = 20;
					yCor += 40;
				}
			}	
		levelLabel.setText("Level "+Main.difficulty);
	}

	public static void updateLivesLeft()	{
		livesLeft--;
		if(livesLeft==2)
			livesLabel1.setIcon(null);
		else if(livesLeft==1)
			livesLabel2.setIcon(null);
		else if(livesLeft==0)
			livesLabel3.setIcon(null);
		else if(livesLeft < 0){
			gameIsRunning = false;
			writeScore();
			System.exit(0);	
		}
	}
	
	public static void writeScore()	{	
		JTextField ID = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Name:"));
		myPanel.add(ID);

		int result = JOptionPane.showConfirmDialog(null, myPanel,"GAME IS OVER!!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) 
		{
			String UserName = ID.getText();
			String Score = playerScore + "";
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now)); //2016/11/16 12:08:43
			
			try {
				BufferedWriter wr = new BufferedWriter(new FileWriter("Scoreboard.txt", true));
				wr.append(UserName);
				wr.newLine();
				wr.append(Score);
				wr.newLine();
				wr.append(dtf.format(now));
				wr.newLine();
				wr.close();
			}		      
			catch (IOException e) {
				System.out.println("No Such a File");
			} 
		}			
	}
}