package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1},
			{1,0,0,1,1,1,0,0,0,0,1,1,1,0,0,1},
			{1,0,0,0,0,2,0,0,0,0,0,0,0,0,0,1},
			{1,2,0,2,0,0,0,1,0,0,0,0,0,0,0,1},
			{1,0,0,1,1,1,0,0,0,0,1,1,1,0,0,1},
			{1,2,0,1,1,1,1,2,0,1,1,1,1,0,0,1},
			{1,2,2,1,1,1,1,0,0,1,1,1,1,0,0,1},
			{1,2,2,2,0,2,2,2,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		getTileImage();
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/background_1.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rock_1.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree_1.png"));
			tile[2].collision = true;
			tile[2].breakable = true;
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNum[row][col];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			if(col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
	}
}
