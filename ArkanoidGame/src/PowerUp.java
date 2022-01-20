import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;
import javax.swing.Icon;

public class PowerUp	{
	public static int size = 1, ballControl = 2, type = 11, seconds, xCordinate, yCordinate;
	public boolean control, laserReady, isHit;
	public static boolean paddleControl, shieldControl, laserControl, magnetControl;
	private Timer timer;
	static AudioClip pickSound;
	static Icon unitImage, shieldImage;
	JLabel unitLabel, shieldLabel;

	public PowerUp(int x, int y)	{
		
		URL pick = GameFrame.class.getResource("PowerUp.wav");
		pickSound = Applet.newAudioClip(pick);	

		this.xCordinate = x;
		this.yCordinate = y;
			
		Random generator = new Random();
		int i = generator.nextInt(11);

		if (i == 0)
			this.unitImage = new ImageIcon(getClass().getResource("powerIncreasedLength.png"));	
		else if (i == 1)
			this.unitImage = new ImageIcon(getClass().getResource("powerDecreasedLength.png"));
		else if (i == 2)
			this.unitImage = new ImageIcon(getClass().getResource("powerIncreasedSpeed.png"));
		else if (i == 3)
			this.unitImage = new ImageIcon(getClass().getResource("powerDecreasedSpeed.png"));	
		else if (i == 4)
			this.unitImage = new ImageIcon(getClass().getResource("powerEnergyIncreased.png"));		
		else if (i == 5)
			this.unitImage = new ImageIcon(getClass().getResource("powerEnergyDown.png"));		
		else if (i == 6)
			this.unitImage = new ImageIcon(getClass().getResource("powerLifeUp.png"));		
		else if (i == 7)
			this.unitImage = new ImageIcon(getClass().getResource("powerLifeDown.png"));	
		else if (i == 8)
			this.unitImage = new ImageIcon(getClass().getResource("powerMagnet.png"));		
		else if (i == 9)
			this.unitImage = new ImageIcon(getClass().getResource("powerShield.png"));	
		else if (i == 10)
			this.unitImage = new ImageIcon(getClass().getResource("powerLaser.png"));
		
		type = i;
		
		this.unitLabel = new JLabel(unitImage);	
		this.unitLabel.setBounds(xCordinate, yCordinate, unitImage.getIconWidth(), unitImage.getIconHeight());
		Main.background.add(unitLabel);
		
		this.timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				seconds++;
				System.out.println(type);
				System.out.println(seconds);
				if ((type == 4) || (type == 5))	{
					if(seconds == 12){
						timer.stop();
						seconds = 0;			
						unitImage = new ImageIcon(getClass().getResource("Ball.png"));
						GameFrame.ball.ballLabel.setIcon(unitImage);
						ballControl=2;
					}
				}
				else 	if (type == 9)	{
					if(seconds == 15){
						timer.stop();
						seconds = 0;	
						shieldLabel.setIcon(unitImage);
						shieldControl=false;
					}
				}
				else 	if (type == 10)	{
					if(seconds == 8){
						timer.stop();
						seconds = 0;
						GameFrame.laserReady = false;		
					}
				}
			}
		});
	}
	
	void move()	{ 			
		
		if (yCordinate >= 800)	{
			this.unitLabel.setIcon(null);
			GameFrame.powerUpArray.remove(this);
		}
		else
			this.yCordinate += 5;
		this.unitLabel.setBounds(xCordinate, yCordinate, unitImage.getIconWidth(), unitImage.getIconHeight());
		checkForCollision();
	}
	
	void checkForCollision() 
	{		
		Rectangle powerUpRectangle = new Rectangle(xCordinate, yCordinate, unitImage.getIconWidth(), unitImage.getIconHeight());
		Rectangle playerPalletRectangle = new Rectangle(GameFrame.player.xCordinate, GameFrame.player.yCordinate,GameFrame.player.unitImage.getIconWidth(), GameFrame.player.unitImage.getIconWidth());
			
		if (powerUpRectangle.intersects(playerPalletRectangle))	{
			this.unitLabel.setIcon(null);
			GameFrame.powerUpArray.remove(this);
			pickSound.play();
			
			control = true;
			if (type == 0)
				PaddleLength();
			else if (type == 2)	
				BallSpeed();		
			else if (type == 4)
				BallPower();			
			else if (type == 6)
				PlayerLife();	
			
			
			control = false;
			if (type == 1)	
				PaddleLength();
			else if (type == 3)	
				BallSpeed();
			else if (type == 5)
				BallPower();
			else if (type == 7)	
				PlayerLife();		
			
			
			else if (type == 8)	
				PlayerMagnet();
			else if (type == 9)	
				PlayerShield();
			else if (type == 10)
				PlayerLaser();
		}
	}
	
	public void PaddleLength()	{
		if (this.control){
			this.unitImage = new ImageIcon(getClass().getResource("PlayerPalletBig.png"));
			GameFrame.player.unitLabel.setIcon(unitImage);
			this.paddleControl=true;
		}
		else	{
			this.unitImage = new ImageIcon(getClass().getResource("PlayerPalletSmall.png"));
			GameFrame.player.unitLabel.setIcon(unitImage);
			this.paddleControl=false;
		}
	}
	
	public void BallSpeed()	{
		if (this.control)
			GameFrame.ball.xMovement += GameFrame.ball.xMovement/4;
		else
			GameFrame.ball.xMovement -= GameFrame.ball.xMovement/4;
	}

	public void BallPower()	{
		this.timer.start();
		if (this.control){
			this.unitImage = new ImageIcon(getClass().getResource("BallRed.png"));
			GameFrame.ball.ballLabel.setIcon(unitImage);
			this.ballControl=1;
		}
		else{
			this.unitImage = new ImageIcon(getClass().getResource("BallGreen.png"));
			GameFrame.ball.ballLabel.setIcon(unitImage);
			this.ballControl=0;
		}
	}
	
	public void PlayerLife()	{
		if (this.control)	{ //3 lives max
			
			if(GameFrame.livesLeft == 2)
				GameFrame.livesLabel1.setIcon(GameFrame.livesLeftImage);
			
			else if(GameFrame.livesLeft == 1)
				GameFrame.livesLabel2.setIcon(GameFrame.livesLeftImage);
			
			else if(GameFrame.livesLeft == 0)	
				GameFrame.livesLabel3.setIcon(GameFrame.livesLeftImage);
			
			GameFrame.livesLeft++;
		}
		else
			GameFrame.updateLivesLeft();
	}
	
	public void PlayerMagnet()	{
		this.magnetControl = true;	
	}
	
	public void PlayerShield()	{
		this.timer.start();
		this.shieldControl = true;
		this.shieldImage = new ImageIcon(getClass().getResource("Shield.png"));
		this.shieldLabel = new JLabel(shieldImage);	
		
		this.shieldLabel.setBounds(0, 750, shieldImage.getIconWidth(), shieldImage.getIconHeight());
		Main.background.add(shieldLabel);	
	}
	
	public void PlayerLaser()	{
		this.timer.start();
		this.laserControl = true;
		GameFrame.laserReady=true;
	}
}