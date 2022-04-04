package game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	public int bombRadius, maxBomb;
	boolean delay = false;
	int delayCounter = 0;
	int score = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;	
		area = new Rectangle();
		area.x = 10;
		area.y = 17;
		area.width = 27;
		area.height = 28;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		x = 3*gp.tileSize;
		y = 3*gp.tileSize;
		speed = 4;
		direction = "down";
		size = gp.tileSize;
		life = 3;
		areaDefaultX = area.x;
		areaDefaultY = area.y;
		bombRadius = 2;
		maxBomb = 2;
	}

	public void getPlayerImage() {
		try {
			up = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if(keyH.upPressed==true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			if(keyH.upPressed==true) {
				direction = "up";
			}
			else if(keyH.downPressed==true) {
				direction = "down";
			}
			else if(keyH.leftPressed==true) {
				direction = "left";
			}
			else if(keyH.rightPressed==true) {
				direction = "right";
			}

			collisionOn = false;
			gp.cChecker.checkTile(this);

			int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
			interactEnemy(enemyIndex);

			if(collisionOn == false) {
				switch(direction) {
				case "up":
					y -= speed;
					break;
				case "down":
					y += speed;
					break;
				case "left":
					x -= speed;
					break;
				case "right":
					x += speed;
					break;
				}
			}
		}

		PlantBomb();

		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 80) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		switch(direction) {
		case "up":
			image = up;
			break;
		case "down": 
			image = down;
			break;
		case "left":
			image = left;
			break;
		case "right":
			image = right;
			break;
		}
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		}
		g2.drawImage(image, x, y, size, size, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public void PlantBomb() {

		if(keyH.spacePressed == true && delay == false && gp.bomb.size()<=maxBomb) {
			int colIndex = (gp.player.x + gp.player.size/2)/gp.tileSize;
			int rowIndex = (gp.player.y + gp.player.size/2)/gp.tileSize;
			if(doesBombExist(colIndex,rowIndex)) {
				gp.bomb.add(new Bomb(gp,colIndex,rowIndex));
				delay = true;
			}
		}

		if(delay== true) {
			delayCounter++;
			if(delayCounter >= 10) {
				delay = false;
				delayCounter = 0;
			}
		}

	}
	
	public boolean doesBombExist(int col, int row) {
		for(Bomb x : gp.bomb) {
			if(col == x.colIndex && row == x.rowIndex) {
				return false;
			}
		}
		return true;
	}
	
	public void interactEnemy(int i) {
		if(i != 999) {
			if(invincible == false) {
				life--;
				invincible = true;
			}
		}
	}
}
