package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bomb {

	GamePanel gp;
	int colIndex, rowIndex;
	BufferedImage boom, explosion;	
	int timeToExplosion = 80, explosionCounter = 50;
	public int limitUp, limitDown, limitLeft, limitRight;
	public boolean end = false;

	public Bomb(GamePanel gp) {
		this.gp = gp;
		colIndex = (gp.player.x + gp.player.size/2)/gp.tileSize;
		rowIndex = (gp.player.y + gp.player.size/2)/gp.tileSize;
		try {
			boom = ImageIO.read(getClass().getResourceAsStream("/boom/boom_1.png"));
			explosion = ImageIO.read(getClass().getResourceAsStream("/boom/bombbang_mid_1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update() {

		if(timeToExplosion!=0) {
			timeToExplosion --;
		} else if(explosionCounter!=0) {
			explosionCounter--;
		} else {end = true;}

		bombRadiusLimit();

		if(timeToExplosion==0 && explosionCounter!=0) {
			gp.eh.checkEvent(colIndex, rowIndex);
			for(int i = 1; i <= gp.player.bombRadius; i++) {				
				if(i <= limitRight) gp.eh.checkEvent(colIndex+i, rowIndex);
				if(i <= limitDown) gp.eh.checkEvent(colIndex, rowIndex+i);
				if(i <= limitLeft) gp.eh.checkEvent(colIndex-i, rowIndex);
				if(i <= limitUp) gp.eh.checkEvent(colIndex, rowIndex-i);
			}
		}
	}

	public void draw(Graphics2D g2) {	
		if(timeToExplosion!=0) {
			g2.drawImage(boom, colIndex*gp.tileSize+6, rowIndex*gp.tileSize+6, gp.tileSize*3/4, gp.tileSize*3/4, null);
		}
		if(timeToExplosion==0 && explosionCounter!=0) {
			g2.drawImage(explosion, colIndex*gp.tileSize, rowIndex*gp.tileSize, gp.tileSize, gp.tileSize, null);
			for(int i = 1; i <= gp.player.bombRadius; i++) {
				if(i <= limitRight) drawExplosion(g2,colIndex+i,rowIndex);
				if(i <= limitDown) drawExplosion(g2,colIndex,rowIndex+i);
				if(i <= limitLeft) drawExplosion(g2,colIndex-i,rowIndex);
				if(i <= limitUp) drawExplosion(g2,colIndex,rowIndex-i);
			}
		} 	
	}

	public void drawExplosion(Graphics2D g2, int col, int row) {
		int tileNum;
		try {
			tileNum = gp.tileM.mapTileNum[row][col];
		} catch(ArrayIndexOutOfBoundsException e) {
			tileNum = 1;
		}
		if( gp.tileM.tile[tileNum].collision == false ) {
			g2.drawImage(explosion, col*gp.tileSize, row*gp.tileSize, gp.tileSize, gp.tileSize, null);
		}
		if( gp.tileM.tile[tileNum].breakable == true) {
			g2.drawImage(explosion, col*gp.tileSize, row*gp.tileSize, gp.tileSize, gp.tileSize, null);
			gp.tileM.mapTileNum[row][col] = 0;
			gp.player.score+=5;
		}
	}

	public void bombRadiusLimit() {
		limitUp = limitDown = limitRight = limitLeft = gp.player.bombRadius;
		for(int i = 1; i <= gp.player.bombRadius; i++) {
			if(i <= limitRight) setLimit(colIndex+i, rowIndex, i, "right");
			if(i <= limitDown)	setLimit(colIndex, rowIndex+i, i, "down");
			if(i <= limitLeft)	setLimit(colIndex-i, rowIndex, i, "left");
			if(i <= limitUp)	setLimit(colIndex, rowIndex-i, i, "up");
		}
	}

	public void setLimit(int col, int row, int i, String s) {
		int tileNum;
		try {
			tileNum = gp.tileM.mapTileNum[row][col];
		} catch(ArrayIndexOutOfBoundsException e) {
			tileNum = 0;
		}
		if(gp.tileM.tile[tileNum].collision == true && gp.tileM.tile[tileNum].breakable == false) {
			switch (s) {
			case "up":
				limitUp = i-1;
				break;
			case "down":
				limitDown = i-1;
				break;
			case "left":
				limitLeft = i-1;
				break;
			case "right":
				limitRight = i-1;
				break;
			}
		}
	}
}



