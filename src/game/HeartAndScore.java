package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HeartAndScore {

	GamePanel gp;
	public BufferedImage image;

	public HeartAndScore(GamePanel gp) {
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
			g2.drawImage(image, x, y, gp.tileSize*3/4, gp.tileSize*4/6, null);
			x += gp.tileSize*3/4;
		}		
		g2.setFont(new Font("Arial", Font.PLAIN, 35));
		g2.setColor(Color.WHITE);
		g2.drawString("Score: " + gp.player.score, gp.tileSize*13, gp.tileSize*5/6);
	}

}
