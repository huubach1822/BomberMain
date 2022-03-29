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
	
}
