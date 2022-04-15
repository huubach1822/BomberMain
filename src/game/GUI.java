package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
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

import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class GUI extends JFrame implements MouseListener {

	TextField textField = null;	
	JButton btnNewButton = null, btnNewButton_1 = null;
	
	public GUI() {
		setTitle("Boom Game");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\admin\\BoomGame\\boom_icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 608);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
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
		btnNewButton.setBounds(135, 357, 138, 50);
		panel.add(btnNewButton);
		btnNewButton.addMouseListener(this);
		
		btnNewButton_1 = new JButton("High Score");
		btnNewButton_1.setForeground(Color.DARK_GRAY);
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_1.setBounds(322, 357, 150, 50);
		panel.add(btnNewButton_1);
		
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\admin\\Downloads\\Some MeMes\\boom-m-02_jkgu.jpg"));
		lblNewLabel_1.setBounds(0, 0, 773, 571);
		panel.add(lblNewLabel_1);
		
		Label label = new Label("Created by Hai and Bach");
		label.setBackground(new Color(204, 255, 255));
		label.setBounds(553, 24, 150, 21);
		panel.add(label);
		//panel1
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridBagLayout());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(btnNewButton)) {
		JFrame window = new JFrame();
		ImageIcon icon = new ImageIcon("boom_icon.png");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("BoomGame");
		window.setIconImage(icon.getImage());
		
		
		GamePanel gamePanel = new GamePanel(textField.getText());
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
		
		this.dispose();
		}
		
		if(e.getSource().equals(btnNewButton_1)) {
			
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
