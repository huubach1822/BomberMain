package powerUp;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import game.GamePanel;

public abstract class PowerUp {

	GamePanel gp;
	public int colIndex, rowIndex;
	BufferedImage img;
	Rectangle pu;
	public int areaDefaultX, areaDefaultY;
	public int invincibleTime = 60;
	public boolean pickedUp = false;
	
	public PowerUp(GamePanel gp, int colIndex, int rowIndex) {
		this.gp = gp;
		this.colIndex = colIndex;
		this.rowIndex = rowIndex;
		pu = new Rectangle();
		pu.x = 6;
		pu.y = 6;
		pu.width = gp.tileSize*3/4;
		pu.height = gp.tileSize*3/4;
		areaDefaultX = pu.x;
		areaDefaultY = pu.y;
	}
	
	public void update() {
		if(invincibleTime!=0) {
			invincibleTime --;
		}
		gp.player.area.x = gp.player.x + gp.player.area.x;
		gp.player.area.y = gp.player.y + gp.player.area.y;
		pu.x = colIndex*gp.tileSize + pu.x;
		pu.y = rowIndex*gp.tileSize + pu.y;
		if(gp.player.area.intersects(pu)) {
			power();
			pickedUp = true;
		}
		gp.player.area.x = gp.player.areaDefaultX;
		gp.player.area.y = gp.player.areaDefaultY;
		pu.x = areaDefaultX;
		pu.y = areaDefaultY;
	}
	
	public abstract void power();
	
	public void draw(Graphics2D g2) {
		g2.drawImage(img, colIndex*gp.tileSize + pu.x, rowIndex*gp.tileSize + pu.y, pu.width, pu.height, null);
	}
	
}
