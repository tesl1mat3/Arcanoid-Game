import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Icon;
import java.awt.Rectangle;
import java.util.Random;

public class Ball	{
	public static PowerUp powerUp;
	public int  xMovement = 6, yMovement = 6, brickDestroyed = 0;
	public int xCordinate = 360, yCordinate = 300;
	public static boolean collideControl;
	private Icon ballimage;
	public JLabel ballLabel;
	
	public Ball()	{

		ballimage = new ImageIcon(getClass().getResource("Ball.png"));
		ballLabel = new JLabel(ballimage);	
		
		ballLabel.setBounds(350, 450, ballimage.getIconWidth(), ballimage.getIconHeight());
		Main.background.add(ballLabel);	
	}
	
	void move()	{ 	
		if (!collideControl)	{
			if ((xCordinate <= 0) && (xMovement < 0))	
				xMovement = -xMovement;
			if ((xCordinate >= 730) && (xMovement > 0))
				xMovement = -xMovement;
			if ((yCordinate <= 60) && (yMovement < 0))	
				yMovement = -yMovement;
			if ((yCordinate >= 850) && (yMovement > 0))	{		
				GameFrame.updateLivesLeft();
				xCordinate = 360;
				yCordinate = 300;
			}
			if (((yCordinate >= 740) && (yMovement > 0)) && (PowerUp.shieldControl))
				yMovement = -yMovement;
		
		checkForPlayerCollision();
		checkForBrickCollision();	
		if (PowerUp.paddleControl)
			ballimage = new ImageIcon(getClass().getResource("PlayerPalletBig.png"));
		ballLabel.setBounds(xCordinate += xMovement, yCordinate += yMovement, ballimage.getIconWidth(), ballimage.getIconHeight());
		}
		else{
			xCordinate = GameFrame.player.xCordinate+30;
			ballLabel.setBounds(xCordinate, yCordinate, ballimage.getIconWidth(), ballimage.getIconHeight());
		}
	}
	
	public void checkForPlayerCollision()	{
		
		Rectangle ballRectangle = new Rectangle(xCordinate, yCordinate, ballimage.getIconWidth(), ballimage.getIconHeight());
		Rectangle playerRectangle = new Rectangle(GameFrame.player.xCordinate, GameFrame.player.yCordinate, GameFrame.player.unitImage.getIconWidth(), GameFrame.player.unitImage.getIconHeight());
		
		if (ballRectangle.intersects(playerRectangle)){
			if (PowerUp.magnetControl){
				collideControl=true;
			}
			else	{
				yMovement = -yMovement;		
				GameFrame.bounceSound.play();
			}
		}
	}
	
	public void checkForBrickCollision()	{	
		
		Rectangle ballRectangle = new Rectangle(xCordinate, yCordinate, ballimage.getIconWidth(), ballimage.getIconHeight());
		
		for(int i = 0; i < GameFrame.brickArray.size(); i++)	{
			
			Brick brick = GameFrame.brickArray.get(i);
			Rectangle brickRectangle = new Rectangle(brick.xCordinate, brick.yCordinate,brick.unitImage.getIconWidth(), brick.unitImage.getIconHeight());
			
			if (ballRectangle.intersects(brickRectangle))	{
				
				Random generator = new Random();
				int rand = generator.nextInt(2);
				
				if (PowerUp.ballControl == 0){
					yMovement = -yMovement;	
					return;
				}
				
				if ((brick.type != 2) && (PowerUp.ballControl == 1)){
					brick.unitLabel.setIcon(null);
					GameFrame.brickArray.remove(brick);	
					brickDestroyed++;
					yMovement = -yMovement;	
					if(((PowerUp.seconds==0)&&(rand == 0 ))&&(GameFrame.powerUpArray.size()< 1))	{ // %50 chance power up drop
						powerUp = new PowerUp(xCordinate+10, yCordinate-5);	
						GameFrame.powerUpArray.add(powerUp);
					}
				}
				
				else if (brick.type == 0)	{
					brick.unitLabel.setIcon(null);
					GameFrame.brickArray.remove(brick);		
					brickDestroyed++;
					if(((PowerUp.seconds==0)&&(rand == 0 ))&&(GameFrame.powerUpArray.size()< 1))	{ // %50 chance power up drop
						powerUp = new PowerUp(xCordinate+10, yCordinate-5);	
						GameFrame.powerUpArray.add(powerUp);
					}
				}
				
				else if ((brick.type == 1)&&(brick.isHit == true))	{
					brick.unitLabel.setIcon(null);
					GameFrame.brickArray.remove(brick);		
					brickDestroyed++;
					if(((PowerUp.seconds==0)&&(rand == 0 ))&&(GameFrame.powerUpArray.size()< 1))	{ // %50 chance power up drop
						powerUp = new PowerUp(xCordinate+10, yCordinate-5);	
						GameFrame.powerUpArray.add(powerUp);
					}
				}	
				
				else if ((brick.type == 1)&&(brick.isHit == false))	{			
					brick.unitLabel.setIcon(brick.unitImageCracked);	
					brick.isHit = true;
				}
				
				else if (brick.type == 2)	
					GameFrame.playerScore -= 100;	
				
				GameFrame.playerScore += 100;
				GameFrame.scoreLabel.setText("Score : "+GameFrame.playerScore);
				yMovement = -yMovement;	
				GameFrame.bounceSound.play();
			}	
		}
	}
}