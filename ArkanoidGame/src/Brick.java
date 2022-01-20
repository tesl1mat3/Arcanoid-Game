import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Icon;

public class Brick	{
	
	Icon unitImage, unitImageCracked;
	JLabel unitLabel;
	
	public int xCordinate, yCordinate, type;
	public boolean isHit;

	public Brick(int x, int y,int type)	{

		this.xCordinate = x;
		this.yCordinate = y;
		this.type = type;
		
		this.unitImage = new ImageIcon(getClass().getResource("BlueBrick.png"));
		this.unitImageCracked = new ImageIcon(getClass().getResource("BlueBrickCracked.png"));
		
		if (type == 2)
			this.unitImage = new ImageIcon(getClass().getResource("BlueBrickSquare.png"));	
		
		this.unitLabel = new JLabel(unitImage);
		this.unitLabel.setBounds(xCordinate, yCordinate, unitImage.getIconWidth(), unitImage.getIconHeight());	
		
		Main.background.add(this.unitLabel);
	}
}