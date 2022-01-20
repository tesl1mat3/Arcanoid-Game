import java.awt.LayoutManager;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GUI extends JPanel {
	
	public int Menu = 1;
	
	private Image icon, play, options, highScore, help, about, exit, novice, intermediate, advanced;
		
	public GUI(LayoutManager l) {
		super.setLayout(l);
	}

	protected void paintComponent(Graphics g) {		
		super.paintComponent(g);
			
		icon = new ImageIcon(getClass().getResource("Background.jpg")).getImage();
		play = new ImageIcon(getClass().getResource("buttonNewGame.jpg")).getImage();
		options = new ImageIcon(getClass().getResource("buttonOptions.jpg")).getImage();
		highScore = new ImageIcon(getClass().getResource("buttonHighScores.jpg")).getImage();
		help = new ImageIcon(getClass().getResource("buttonHelp.jpg")).getImage();
		about = new ImageIcon(getClass().getResource("buttonAbout.jpg")).getImage();
		exit = new ImageIcon(getClass().getResource("buttonExit.jpg")).getImage();
		novice = new ImageIcon(getClass().getResource("buttonNovice.jpg")).getImage();
		intermediate = new ImageIcon(getClass().getResource("buttonIntermediate.jpg")).getImage();
		advanced = new ImageIcon(getClass().getResource("buttonAdvanced.jpg")).getImage();
		
		g.drawImage(icon, 0, 0, 800, icon.getHeight(null), null);
		
		if(Menu == 1)	{		
			
			g.drawImage(play, 270, 200, null);
			g.drawImage(options, 270, 280, null);
			g.drawImage(highScore, 270, 360, null);
			g.drawImage(help, 270, 440, null);
			g.drawImage(about, 270, 520, null);
			g.drawImage(exit, 270, 600, null);		
		}
		
		else if (Menu ==2)	{
			
			g.drawImage(novice, 270, 280, null);
			g.drawImage(intermediate, 270, 360, null);
			g.drawImage(advanced, 270, 440, null);	
		}
		
		else if (Menu == 0){
			g.drawRect(0, 0, 800, 55);
			g.fillRect(0, 0, 800, 55);
		}
	}	
}