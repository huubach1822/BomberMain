package powerUp;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class HeartPU extends PowerUp{

	public HeartPU(GamePanel gp, int colIndex, int rowIndex) {
		super(gp, colIndex, rowIndex);
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/heart/heart_full.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void power() {
		if(gp.player.life<=5) gp.player.life++;
	}

}
