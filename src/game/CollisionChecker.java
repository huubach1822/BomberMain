package game;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {

		int entityLeftWorldX = entity.x + entity.area.x;
		int entityRightWorldX= entity.x + entity.area.x + entity.area.width;
		int entityTopWorldY= entity.y + entity.area.y;
		int entityBottomWorldY = entity.y + entity.area.y + entity.area.height;

		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;

		int tileNum1, tileNum2;

		switch(entity.direction) { 
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
			tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol]; 
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { 
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
			tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol]; 
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { 
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
			tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol]; 
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { 
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
			tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol]; 
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) { 
				entity.collisionOn = true;
			}
			break;
		}
	}
	public int checkEntity(Entity entity, Entity[] target) {

		int index = 999;

		for (int i = 0; i < target.length; i++) {
			if (target[i] != null ) {

				entity.area.x = entity.x + entity.area.x;
				entity.area.y = entity.y + entity.area.y;

				target[i].area.x = target[i].x + target[i].area.x;
				target[i].area.y = target[i].y + target[i].area.y;

				switch(entity.direction) {
				case "up":
					entity.area.y -= entity.speed;
					break;
				case "down":
					entity.area.y += entity.speed;
					break;
				case "left":
					entity.area.x -= entity.speed;
					break;
				case "right":
					entity.area.x += entity.speed;
					break;
				}
				if(entity.area.intersects(target[i].area)) {
					if(target[i] != entity) {
					entity.collisionOn = true; 
					index = i;
					}
				}
				entity.area.x = entity.areaDefaultX;
				entity.area.y = entity.areaDefaultY;
				target[i].area.x = target[i].areaDefaultX;
				target[i].area.y = target[i].areaDefaultY;
			}
		}
		return index;
	}
	public void checkPlayer(Entity entity) {
		entity.area.x = entity.x + entity.area.x;
		entity.area.y = entity.y + entity.area.y;

		gp.player.area.x = gp.player.x + gp.player.area.x;
		gp.player.area.y = gp.player.y + gp.player.area.y;

		switch(entity.direction) {
		case "up":
			entity.area.y -= entity.speed;
			break;
		case "down":
			entity.area.y += entity.speed;
			break;
		case "left":
			entity.area.x -= entity.speed;
			break;
		case "right":
			entity.area.x += entity.speed;
			break;
		}
		if(entity.area.intersects(gp.player.area)) {
			entity.collisionOn = true;
			if(gp.player.invincible == false) {
				gp.player.life--;
				gp.player.invincible = true;
			}
		}
		entity.area.x = entity.areaDefaultX;
		entity.area.y = entity.areaDefaultY;
		gp.player.area.x = gp.player.areaDefaultX;
		gp.player.area.y = gp.player.areaDefaultY;
	}
}
