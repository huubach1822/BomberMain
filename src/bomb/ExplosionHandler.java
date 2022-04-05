package bomb;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import game.GamePanel;
import powerUp.PowerUp;

public class ExplosionHandler {

	GamePanel gp;
	Rectangle eh;

	public ExplosionHandler(GamePanel gp) {
		this.gp = gp;
		eh = new Rectangle();
		eh.width = gp.tileSize;
		eh.height = gp.tileSize;
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
					gp.score +=25;
				}
			}
		}
		for(Bomb x : gp.bomb) {
			if(eventCol == x.colIndex && eventRow == x.rowIndex) {
				x.timeToExplosion = 0;
			}
		}
		List<PowerUp> toRemove = new ArrayList<>();
		for(PowerUp x : gp.powerUp) {
			if (x.colIndex == eventCol && x.rowIndex == eventRow && x.invincibleTime == 0) {
				toRemove.add(x);
			}
		}
		gp.powerUp.removeAll(toRemove);
	}

	public boolean hit(int eventCol, int eventRow,Entity target) {
		boolean hit = false;
		target.area.x = target.x + target.area.x;
		target.area.y = target.y + target.area.y;
		eh.x = eventCol*gp.tileSize;
		eh.y = eventRow*gp.tileSize;
		if(target.area.intersects(eh)) {
			hit = true;
		}
		target.area.x = target.areaDefaultX;
		target.area.y = target.areaDefaultY;
		return hit;
	}

}
