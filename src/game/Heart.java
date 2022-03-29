package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Heart {

	GamePanel gp;
	public BufferedImage image;

	public Heart(GamePanel gp) {
		this.gp = gp;	
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/heart/heart_full.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int x = gp.tileSize/4;
		int y = gp.tileSize/4;	
		for(int i = 0; i < gp.player.life; i++) {
			g2.drawImage(image, x, y, gp.tileSize*3/4, gp.tileSize*3/4, null);
			x += gp.tileSize*3/4;
		}		
	}

}
