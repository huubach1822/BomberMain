package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import enity.Player;
import object.Bomb;
import object.Heart;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	public final int tileSize = 48;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;	//768
	public final int screenHeight = tileSize * maxScreenRow;	//576
	
	public KeyHandler keyH = new KeyHandler();
	public Thread gameThread;
	public Player player = new Player(this,keyH);
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Bomb bombE = new Bomb(this);
	public Heart heart = new Heart(this);
	public ExplosionRadius er = new ExplosionRadius(this);
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		while(gameThread != null) {
			requestFocusInWindow();
			update();
			repaint();
			
			try {
				Thread.sleep(1000/60);	// 1sec/60
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void update() {
		
		player.update();
		bombE.update();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		bombE.draw(g2);
		player.draw(g2);
		heart.draw(g2);
		
		g2.dispose();
	}

	
}
