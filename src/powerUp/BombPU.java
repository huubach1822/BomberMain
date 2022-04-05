package powerUp;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class BombPU extends PowerUp {

	public BombPU(GamePanel gp, int colIndex, int rowIndex) {
		super(gp, colIndex, rowIndex);
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/powerUp/item_bomb.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void power() {
		if(gp.player.maxBomb<=6) gp.player.maxBomb++;
	}

}
