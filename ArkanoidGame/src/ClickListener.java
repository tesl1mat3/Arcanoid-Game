import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import javax.swing.JOptionPane;

public class ClickListener implements MouseListener {

	public int xCordinate, yCordinate;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		xCordinate = e.getX();
		yCordinate = e.getY();

		if (Main.background.Menu == 1)	{
	
			if((yCordinate >= 200) && (yCordinate <=250 ))	{	
				Main.background.Menu=0;
				new GameFrame();	
			}
			
			if((yCordinate >= 280) && (yCordinate <=330 ))	{
				Main.background.Menu = 2;
				Main.background.repaint();
			}
			
			if((yCordinate >= 360) && (yCordinate <=410 ))	{
				String line, str="";
				try {
					BufferedReader rd = new BufferedReader(new FileReader("Scoreboard.txt"));
						
					while((line = rd.readLine()) != null) 
			                str+= line+"\n"; 
			
					rd.close();
					JOptionPane.showMessageDialog(null, str,"Scoreboard",JOptionPane.PLAIN_MESSAGE);	
				}
				catch (IOException event) {
					System.out.println("No Such a File");
				}
			}
			
			if((yCordinate >= 440) && (yCordinate <=490 ))	{
				JOptionPane.showMessageDialog(null, "Move Left = Left arrow key\nMove Right = Right arrow key\nFire = Space button","Credits",JOptionPane.PLAIN_MESSAGE);
				
			}
			
			if((yCordinate >= 520) && (yCordinate <=570 ))	{
				JOptionPane.showMessageDialog(null, "Dev Name = Oðuzhan Kaçamak","Credits",JOptionPane.PLAIN_MESSAGE);
			}
			
			if((yCordinate >= 600) && (yCordinate <=650 ))	{	
				System.exit(0);
			}
		}
		else if (Main.background.Menu == 2){
			
			if((yCordinate >= 280) && (yCordinate <=330 ))			
				Main.difficulty = 1;
				
			if((yCordinate >= 360) && (yCordinate <=410 ))			
				Main.difficulty = 2;
				
			if((yCordinate >= 440) && (yCordinate <=490 ))		
				Main.difficulty = 3;	
			Main.background.Menu=0;
			new GameFrame();
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
