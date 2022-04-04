package game;

import java.awt.Rectangle;

public class ExplosionHandler {

	GamePanel gp;
	Rectangle eh;
	int RectDefaultX, RectDefaultY;

	public ExplosionHandler(GamePanel gp) {
		this.gp = gp;
		eh = new Rectangle();
		eh.x = 0;
		eh.y = 0;
		eh.width = gp.tileSize;
		eh.height = gp.tileSize;
		RectDefaultX = eh.x;
		RectDefaultY = eh.y;
	}

	public void checkEvent(int eventCol, int eventRow) {
		if( hit(eventCol,eventRow, gp.player) == true) {
			if(gp.player.invincible == false) {
				gp.player.life--;
				gp.player.invincible = true;
			}
		}
		for(int i = 0; i < gp.enemy.length; i++) {
			if(gp.enemy[i]!= null)
			{
				if( hit(eventCol,eventRow, gp.enemy[i]) == true) {
					gp.enemy[i] = null;
					gp.player.score +=25;
				}
			}
		}
		for(Bomb x : gp.bomb) {
			if(eventCol == x.colIndex && eventRow == x.rowIndex) {
				x.timeToExplosion = 0;
			}
		}
	}

	public boolean hit(int eventCol, int eventRow,Entity target) {
		boolean hit = false;
		target.area.x = target.x + target.area.x;
		target.area.y = target.y + target.area.y;
		eh.x = eventCol*gp.tileSize + eh.x;
		eh.y = eventRow*gp.tileSize + eh.y;
		if(target.area.intersects(eh)) {
			hit = true;
		}
		target.area.x = target.areaDefaultX;
		target.area.y = target.areaDefaultY;
		eh.x = RectDefaultX;
		eh.y = RectDefaultY;
		return hit;
	}

}
