package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	public final int tileSize = 48;
	public final int maxScreenCol = 17;
	public final int maxScreenRow = 15;
	public final int screenWidth = tileSize * maxScreenCol;	//768
	public final int screenHeight = tileSize * maxScreenRow;	//576

	public KeyHandler keyH = new KeyHandler();
	public Thread gameThread;
	public Player player = new Player(this,keyH);
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Heart heart = new Heart(this);
	public ExplosionHandler eh = new ExplosionHandler(this);
	public Enemy enemy[] = new Enemy[10];
	

	public GamePanel() {	
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
			enemy[0] = new Enemy(this, 250, 300);
			enemy[1] = new Enemy(this, 400, 500);
			enemy[2] = new Enemy(this, 600, 450);
			enemy[3] = new Enemy(this, 700, 600);		
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
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		player.draw(g2);
		heart.draw(g2);
		for(int i=0;i<10;i++)
		{
			if(enemy[i]!=null)
			{
				enemy[i].draw(g2);
			}
		}
		g2.dispose();
	}

}
