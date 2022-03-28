package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import enity.Player;
import game.GamePanel;


public class Bomb {

	GamePanel gp;
	int colIndex, rowIndex;
	BufferedImage boom, explosion;	
	int timeToExplosion, explosionCounter;
	boolean plantBomb = false, bombExplode = false;
	public int limitUp = 10, limitDown = 10, limitLeft = 10, limitRight = 10;

	public Bomb(GamePanel gp) {
		this.gp = gp;
		try {
			boom = ImageIO.read(getClass().getResourceAsStream("/boom/boom_1.png"));
			explosion = ImageIO.read(getClass().getResourceAsStream("/boom/bombbang_mid_1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update() {
		if(gp.keyH.spacePressed == true && plantBomb == false && bombExplode == false) {
			colIndex = (gp.player.x + gp.player.size/2)/gp.tileSize;
			rowIndex = (gp.player.y + gp.player.size/2)/gp.tileSize;
			plantBomb = true;
		}
		if(plantBomb == true) {
			timeToExplosion ++;
		}
		if(timeToExplosion>80) {
			plantBomb = false;
			timeToExplosion = 0;
			bombExplode = true;
		}
		if(bombExplode == true) {
			explosionCounter++;
			gp.er.checkEvent(colIndex, rowIndex);
			for(int i = 1; i <= gp.player.bombRadius; i++) {
				bombRadiusLimit(i);
				if(i < limitRight) gp.er.checkEvent(colIndex+i, rowIndex);
				if(i < limitDown) gp.er.checkEvent(colIndex, rowIndex+i);
				if(i < limitLeft) gp.er.checkEvent(colIndex-i, rowIndex);
				if(i < limitUp) gp.er.checkEvent(colIndex, rowIndex-i);
			}
		}
		if(explosionCounter>50) {
			bombExplode = false;
			explosionCounter = 0;
			limitUp = 10;
			limitDown = 10; 
			limitLeft = 10; 
			limitRight = 10;
		}
	}

	public void draw(Graphics2D g2) {	
		if(plantBomb == true) {
			g2.drawImage(boom, colIndex*gp.tileSize+6, rowIndex*gp.tileSize+6, gp.tileSize*3/4, gp.tileSize*3/4, null);
		}
		if(bombExplode == true) {
			g2.drawImage(explosion, colIndex*gp.tileSize, rowIndex*gp.tileSize, gp.tileSize, gp.tileSize, null);
			for(int i = 1; i <= gp.player.bombRadius; i++) {
				if(i < limitRight) drawExplosion(g2,colIndex+i,rowIndex);
				if(i < limitDown) drawExplosion(g2,colIndex,rowIndex+i);
				if(i < limitLeft) drawExplosion(g2,colIndex-i,rowIndex);
				if(i < limitUp) drawExplosion(g2,colIndex,rowIndex-i);
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
		}
	}

	public void bombRadiusLimit(int i) {
		if(i < limitRight) setLimit(colIndex+i, rowIndex, i, "right");
		if(i < limitDown)	setLimit(colIndex, rowIndex+i, i, "down");
		if(i < limitLeft)	setLimit(colIndex-i, rowIndex, i, "left");
		if(i < limitUp)	setLimit(colIndex, rowIndex-i, i, "up");
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
				limitUp = i;
				break;
			case "down":
				limitDown = i;
				break;
			case "left":
				limitLeft = i;
				break;
			case "right":
				limitRight = i;
				break;
			}
		}
	}
}



