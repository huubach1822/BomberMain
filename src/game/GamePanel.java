package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public final int tileSize = 48;
	public final int maxScreenCol = 17;
	public final int maxScreenRow = 15;
	public final int screenWidth = tileSize * maxScreenCol;	//768
	public final int screenHeight = tileSize * maxScreenRow;	//576
	public String playerName; 

	public KeyHandler keyH = new KeyHandler();
	public Thread gameThread;
	public Player player = new Player(this,keyH);
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public HeartAndScore heart = new HeartAndScore(this);
	public ExplosionHandler eh = new ExplosionHandler(this);
	public Enemy enemy[] = new Enemy[20];


	public GamePanel(String str) {	
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		if(str==null||str.length()==0) {
			playerName = "Unknown";
		} else {
			playerName = str;
		}
		setUpEnemy();
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
		for(int i=0;i<enemy.length;i++)
		{
			if(enemy[i]!= null)
			{
				enemy[i].update();
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		for(int i=0;i<enemy.length;i++)
		{
			if(enemy[i]!=null)
			{
				enemy[i].draw(g2);
			}
		}
		player.draw(g2);
		heart.draw(g2);

		if(player.life == 0) {
			gameFinished(g2,"GAME OVER");
			gameThread = null;
		}
		else if(checkEnemy()) {
			gameFinished(g2,"VICTORY");
			gameThread = null;
		}

		g2.dispose();
	}

	public void setUpEnemy() {
		enemy[0] = new Enemy(this, 2*tileSize, 6*tileSize);
		enemy[1] = new Enemy(this, 11*tileSize, 6*tileSize);
		enemy[2] = new Enemy(this, 13*tileSize, 11*tileSize);
		enemy[3] = new Enemy(this, 11*tileSize, 12*tileSize);
		enemy[4] = new Enemy(this, 7*tileSize, 2*tileSize);
		enemy[5] = new Enemy(this, 9*tileSize, 4*tileSize);
		enemy[6] = new Enemy(this, 8*tileSize, 12*tileSize);
		enemy[7] = new Enemy(this, 4*tileSize, 12*tileSize);
		enemy[8] = new Enemy(this, 14*tileSize, 4*tileSize);
		enemy[9] = new Enemy(this, 2*tileSize, 10*tileSize);
	}

	public boolean checkEnemy() {
		for(int i =0 ;i < enemy.length; i++) {
			if(enemy[i] != null) {
				return false;
			}
		}
		return true;
	}

	public void gameFinished(Graphics2D g2, String text) {

		g2.setFont(new Font("Arial", Font.PLAIN, 60));
		g2.setColor(Color.WHITE);
		int textLength;
		int x;
		int y;

		textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = screenWidth/2 - textLength/2;
		y = screenHeight/5;
		g2.drawString(text, x, y);

		g2.setFont(new Font("Arial", Font.PLAIN, 40));

		if(playerName !=null) {
			text = "Name: " + playerName;
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = screenWidth/2 - textLength/2;
			y += tileSize;
			g2.drawString(text, x, y);
		}

		text = "Your score: " + player.score;
		textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = screenWidth/2 - textLength/2;
		y += tileSize;
		g2.drawString(text, x, y);

		text = "High scores";
		textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = screenWidth/2 - textLength/2;
		y += 2*tileSize;
		g2.drawString(text, x, y);
	}

}
