package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import game.GamePanel;

public class Enemy extends Entity {

	GamePanel gp;
	int actionLockCounter = 119;

	public Enemy (GamePanel gp, int x, int y)
	{
		this.x= x;
		this.y = y;
		this.gp = gp;
		setDefaultValues();
		getEnemyImage();

	}
	public void setDefaultValues() {
		area = new Rectangle();
		area.x = 1;
		area.y = 18;
		area.width = 45;
		area.height = 28;
		speed = 2;
		direction = "down";
		size = gp.tileSize;
		areaDefaultX = area.x;
		areaDefaultY = area.y;
	}
	public void getEnemyImage()
	{
		try {
			up = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_up.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_down.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_left.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_right.png"));
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkPlayer(this);
		gp.cChecker.checkEntity(this, gp.enemy);
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
	public void draw(Graphics2D g2)
	{
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
	public void setAction()
	{	
		actionLockCounter++;
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100)+1;
			if(i <= 25 && direction != "up") {
				direction = "up";
			}
			if(i > 25 && i <= 50 && direction != "down") {
				direction = "down";
			}
			if(i > 50 && i <= 75 && direction != "left") {
				direction = "left";
			}
			if(i > 75 && i <= 100 && direction != "right") {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}

}
