package game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
	
	GamePanel gp;
	
		
	public Enemy (GamePanel gp, int x, int y)
	{
		this.x= x;
		this.y = y;
		this.gp = gp;
		area = new Rectangle();
		area.x = 3;
		area.y = 18;
		area.width = 42;
		area.height = 30;
		setDefaultValues();
		getEnemyImage();
		
	}
	public void setDefaultValues() {
		speed = 3;
		size = gp.tileSize;
		life = 1;
		areaDefaultX = area.x;
		areaDefaultY = area.y;
		direction = "down";
	}
	public void getEnemyImage()
	{
		try {
			up = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_up_1.png"));
			down = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_donw_1.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_left_1.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/enemy/boss_right_1.png"));
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void getAction()
	{
		
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
	
	
	

}
