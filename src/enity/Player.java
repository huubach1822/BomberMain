package enity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
import game.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	public int bombRadius;

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
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
		size = gp.tileSize;
		life = 3;
		areaDefaultX = area.x;
		areaDefaultY = area.y;
		bombRadius = 3;
	}

	public void getPlayerImage() {
		try {
			up = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
		} catch(IOException e) {
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
		g2.drawImage(image, x, y, size, size, null);
		
		// DEBUG
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible:"+invincibleCounter, 10, 400);
		
	}

}
