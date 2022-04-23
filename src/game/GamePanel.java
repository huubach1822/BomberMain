package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import bomb.Bomb;
import bomb.ExplosionHandler;
import entity.Enemy;
import entity.Player;
import powerUp.PowerUp;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public final int tileSize = 48;
	public final int maxScreenCol = 17;
	public final int maxScreenRow = 15;
	public final int screenWidth = tileSize * maxScreenCol;	//768
	public final int screenHeight = tileSize * maxScreenRow;	//576
	public String playerName, gameEnd; 
	public int score = 0;
	public BufferedImage win, lose;
	public boolean gamePause = false;
	public JButton btnResetButton = null;
	public DataBase db = null;

	public KeyHandler keyH = new KeyHandler(this);
	public Thread gameThread;
	public Player player = new Player(this,keyH);
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public HeartAndScore heart = new HeartAndScore(this);
	public ExplosionHandler eh = new ExplosionHandler(this);
	public Enemy enemy[] = new Enemy[20];
	public Queue<Bomb> bomb = new LinkedList<Bomb>();
	public List<PowerUp> powerUp = new ArrayList<PowerUp>();

	public GamePanel(String str,JButton btnReset,DataBase db) {	
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		if(str==null||str.length()==0) {
			playerName = "unknown";
		} else {
			playerName = str;
		}
		setUpEnemy();
		try {
			win = ImageIO.read(getClass().getResourceAsStream("/game/win.png"));
			lose = ImageIO.read(getClass().getResourceAsStream("/game/game_over.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.btnResetButton = btnReset;
		this.db = db;
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
			if(gameEnd == "win" || gameEnd == "lose") {
				db.updateData(playerName, score);
				gameThread = null;
			}
		}
	}

	public void update() {
		if(gamePause == false) {
			player.update();
			for(int i=0;i<enemy.length;i++)
			{
				if(enemy[i]!= null)
				{
					enemy[i].update();
				}
			}
			updateAllBomb();
			updateAllPU();
			if(player.life == 0) {
				gameEnd = "lose";
			}
			else if(checkEnemy()) {
				gameEnd = "win";
			}
		}
		else {
			//nothing
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		for(PowerUp x : powerUp) {
			x.draw(g2);
		}
		for(Bomb x : bomb) {
			x.draw(g2);
		}
		for(int i=0;i<enemy.length;i++)
		{
			if(enemy[i]!=null)
			{
				enemy[i].draw(g2);
			}
		}
		player.draw(g2);
		heart.draw(g2);
		checkGameState(g2);
		gameFinished(g2);

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

	public void updateAllBomb() {
		for(Bomb x : bomb) {
			x.update();
		}
		if(bomb.size()>0) {
			if(bomb.peek().end==true) {
				bomb.poll();
			}
		}
	}

	public void updateAllPU() {
		List<PowerUp> toRemove = new ArrayList<>();
		for(PowerUp x : powerUp) {
			x.update();
			if (x.pickedUp) {
				toRemove.add(x);
			}
		}
		powerUp.removeAll(toRemove);
	}

	public void checkGameState(Graphics2D g2) {
		if(gamePause == true) {
			String text;
			int textLength;
			int x, y;

			g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
			g2.fillRect(0, 0, screenWidth, screenHeight);
			g2.setFont(new Font("Arial", Font.PLAIN, 80));
			g2.setColor(Color.WHITE);

			text = "PAUSED";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = screenWidth/2 - textLength/2;
			y = screenHeight/5 + tileSize*2;
			g2.drawString(text, x, y);

			g2.setFont(new Font("Arial", Font.PLAIN, 40));
			text = "Press <ESC> to Contiune";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = screenWidth/2 - textLength/2;
			y += tileSize*2;
			g2.drawString(text, x, y);
		}
	}

	public boolean checkEnemy() {
		for(int i =0 ;i < enemy.length; i++) {
			if(enemy[i] != null) {
				return false;
			}
		}
		return true;
	}

	public void gameFinished(Graphics2D g2) {
		if(gameEnd == "win" || gameEnd == "lose") {
			String text = null;
			int textLength, x, y;

			g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
			g2.fillRect(0, 0, screenWidth, screenHeight);
			
			if(gameEnd == "win") {
				g2.drawImage(win, 208, screenHeight/7, screenWidth/2, screenHeight/7, null);
			} else if(gameEnd == "lose") {
				g2.drawImage(lose, 208, screenHeight/7, screenWidth/2, screenHeight/7, null);
			}

			g2.setFont(new Font("Arial", Font.PLAIN, 40));
			g2.setColor(Color.WHITE);

			text = "Name: " + playerName;
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = screenWidth/2 - textLength/2;
			y = screenHeight/4 + tileSize*2;
			g2.drawString(text, x, y);

			text = "Your score: " + score;
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = screenWidth/2 - textLength/2;
			y += tileSize;
			g2.drawString(text, x, y);

			btnResetButton.setBounds(320, y + tileSize, 160, 60);
			btnResetButton.setVisible(true);
			this.add(btnResetButton);
			btnResetButton.requestFocusInWindow();

		}
	}
}
