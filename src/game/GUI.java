package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.GridBagConstraints;
import java.awt.Label;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Button;
import javax.swing.JLabel;
import java.awt.TextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class GUI extends JFrame implements MouseListener {

	TextField textField = null;	
	JButton btnNewButton = null, btnNewButton_1 = null, btnResetButton = null;
	ImageIcon icon = new ImageIcon("boom_icon.png");
	GamePanel gamePanel = null;
	DataBase db = new DataBase();
	JFrame scoreJFrame = new JFrame();

	public GUI() {
		setTitle("Boom Game");
		setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 608);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		scoreJFrame.setVisible(false);
		scoreJFrame.setSize(300, 378);
		scoreJFrame.setIconImage(icon.getImage());
		scoreJFrame.setTitle("HighScore Table");
		scoreJFrame.setLocationRelativeTo(null);
		scoreJFrame.setResizable(false);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Insert name:");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setBackground(new Color(204, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(178, 284, 138, 34);
		panel.add(lblNewLabel);

		textField = new TextField();
		textField.setBounds(322, 287, 256, 21);
		panel.add(textField);

		btnNewButton = new JButton("Start");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(200, 357, 150, 50);
		panel.add(btnNewButton);
		btnNewButton.addMouseListener(this);

		btnNewButton_1 = new JButton("High Score");
		btnNewButton_1.setForeground(Color.DARK_GRAY);
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_1.setBounds(420, 357, 150, 50);
		panel.add(btnNewButton_1);
		btnNewButton_1.addMouseListener(this);

		btnResetButton = new JButton("Play Again ?");
		btnResetButton.setForeground(Color.DARK_GRAY);
		btnResetButton.setBackground(Color.LIGHT_GRAY);
		btnResetButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnResetButton.addMouseListener(this);
		btnResetButton.setVisible(false);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("boom_background.png"));	
		lblNewLabel_1.setBounds(0, 0, 773, 571);
		panel.add(lblNewLabel_1);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(btnNewButton) || e.getSource().equals(btnResetButton)) {
			if(scoreJFrame.isVisible()==true) {
				scoreJFrame.setVisible(false);
			}
			this.getContentPane().removeAll();
			this.repaint();
			GamePanel gamePanel = new GamePanel(textField.getText(),btnResetButton,db);
			this.add(gamePanel);
			this.pack();
			this.setLocationRelativeTo(null);
			gamePanel.startGameThread();
		}

		if(e.getSource().equals(btnNewButton_1)) {
			if(scoreJFrame.isVisible()==false) {
				JPanel scoreJPanel = new JPanel();
				scoreJPanel.setLayout(new BorderLayout());

				String[] columns = {"ID", "Name", "Score"};
				Object[][] data = db.readTable("Select * from player order by Score DESC limit 20");
				JTable playerTable = new JTable(data, columns);
				scoreJPanel.add(playerTable.getTableHeader(), BorderLayout.NORTH);
				scoreJPanel.add(playerTable, BorderLayout.CENTER);
				scoreJFrame.add(scoreJPanel);
				scoreJFrame.setVisible(true);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
