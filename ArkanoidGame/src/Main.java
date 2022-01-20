import javax.swing.JFrame;

public class Main {
		public static JFrame MenuFrame = new JFrame("Arkanoid Game");
		public static GUI background = new GUI(null); 
		public static int difficulty = 0;
		
		public static void main(String[] args)	{
			
			MenuFrame.getContentPane().addMouseListener(new ClickListener());
			MenuFrame.add(background);
			MenuFrame.setVisible(true);
			MenuFrame.setSize(760, 800);
			MenuFrame.setResizable(false);
			MenuFrame.setLocationRelativeTo(null);
			MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
}