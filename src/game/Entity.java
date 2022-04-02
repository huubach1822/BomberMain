package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int x,y;
	public int speed;
	public int size;	
	public BufferedImage up, down ,left, right;
	public String direction;
	public Rectangle area;
	public int areaDefaultX, areaDefaultY;
	public boolean collisionOn = false;
	public int life;
	public boolean invincible = false;
	public int invincibleCounter;

}
