package game;

import java.awt.Rectangle;

public class ExplosionHandler {

	GamePanel gp;
	Rectangle er;
	int RectDefaultX, RectDefaultY;

	public ExplosionHandler(GamePanel gp) {
		this.gp = gp;
		er = new Rectangle();
		er.x = 0;
		er.y = 0;
		er.width = gp.tileSize;
		er.height = gp.tileSize;
		RectDefaultX = er.x;
		RectDefaultY = er.y;
	}

	public void checkEvent(int eventCol, int eventRow) {
		if( hit(eventCol,eventRow) == true) {
			if(gp.player.invincible == false) {
				gp.player.life--;
				gp.player.invincible = true;
			}
			
		}	
	}

	public boolean hit(int eventCol, int eventRow) {
		boolean hit = false;
		gp.player.area.x = gp.player.x + gp.player.area.x;
		gp.player.area.y = gp.player.y + gp.player.area.y;
		er.x = eventCol*gp.tileSize + er.x;
		er.y = eventRow*gp.tileSize + er.y;
		if(gp.player.area.intersects(er)) {
			hit = true;
		}
		gp.player.area.x = gp.player.areaDefaultX;
		gp.player.area.y = gp.player.areaDefaultY;
		er.x = RectDefaultX;
		er.y = RectDefaultY;
		return hit;
	}

}
