package game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		String str;
		ImageIcon icon = new ImageIcon("boom_icon.png");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("BoomGame");
		window.setIconImage(icon.getImage());
		
		str = (String) JOptionPane.showInputDialog(window,"Enter Your Name","Start Game!",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		
		GamePanel gamePanel = new GamePanel(str);
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		gamePanel.setOpaque(false);
		
	}
}
