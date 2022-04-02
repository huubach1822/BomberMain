package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	public int bombRadius, maxBomb;
	Queue<Bomb> bomb = new LinkedList<Bomb>();
	boolean delay = false;
	int delayCounter = 0;

	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;	
		area = new Rectangle();
		area.x = 6;
		area.y = 16;
		area.width = 34;
		area.height = 28;			
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		x = 150;
		y = 150;
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

		for(Bomb x : bomb) {
			x.draw(g2);
		}

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
		g2.drawImage(image, x, y, size, size, null);
	}

	public void PlantBomb() {

		if(keyH.spacePressed == true && delay == false) {
			if(bomb.size()<=maxBomb) {
				bomb.add(new Bomb(gp));
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

		for(Bomb x : bomb) {
			x.update();
		}

		if(bomb.size()>0) {
			if(bomb.peek().end==true) {
				bomb.poll();
			}
		}
	}
}
