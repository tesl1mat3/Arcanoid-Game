import java.awt.Rectangle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Laser {
	
	int xCordinate, yCordinate;

	Icon laserImage;
	JLabel laserLabel;

	public Laser(int xCoordinate, int yCoordinate){
		
		this.laserImage = new ImageIcon(getClass().getResource("laserImage.png"));
		this.laserLabel = new JLabel(laserImage);
		this.xCordinate = xCoordinate +18;
		this.yCordinate = yCoordinate - 30;	
					
		
		Main.background.add(laserLabel);
		GameFrame.laserArray.add(this);
	}
	
	public void move()	{ 	
		
			if (this.yCordinate < 0)	{
				this.laserLabel.setIcon(null);
				GameFrame.laserArray.remove(this);	
			}
			else	{
				this.yCordinate -=  10;
				checkForEnemyCollision();
			}
		this.laserLabel.setBounds(this.xCordinate, this.yCordinate, laserImage.getIconWidth(), laserImage.getIconHeight());
	}
	
	private void checkForEnemyCollision() 
	{		
		Rectangle laserRectangle = new Rectangle(this.xCordinate, this.yCordinate, this.laserImage.getIconWidth(), this.laserImage.getIconHeight());
		for(int i = 0; i < GameFrame.brickArray.size(); i++)
		{
			Brick brick = GameFrame.brickArray.get(i);
			Rectangle brickRectangle = new Rectangle(brick.xCordinate, brick.yCordinate,brick.unitImage.getIconWidth(), brick.unitImage.getIconWidth());
			
			if (laserRectangle.intersects(brickRectangle))
			{
				brick.isHit=true;
				brick.unitLabel.setIcon(null);
				this.laserLabel.setIcon(null);
				GameFrame.laserArray.remove(this);
				GameFrame.brickArray.remove(brick);
				GameFrame.playerScore += 100;
				GameFrame.scoreLabel.setText("Score : "+GameFrame.playerScore);
			}	
		}
	}
}