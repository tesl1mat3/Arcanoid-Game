import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Icon;

public class PlayerPallet	{

	public Icon unitImage, unitImageBig;
	public JLabel unitLabel;
	public int xCordinate = 320, yCordinate = 700;
	 
	public PlayerPallet()	{
		
		
		unitImage = new ImageIcon(getClass().getResource("PlayerPallet.png"));
		unitLabel = new JLabel(unitImage);	

		unitLabel.setBounds(350, 600, unitImage.getIconWidth(), unitImage.getIconHeight());
		Main.background.add(unitLabel);
	}
}