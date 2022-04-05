package powerUp;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class CoinPU extends PowerUp{

	public CoinPU(GamePanel gp, int colIndex, int rowIndex) {
		super(gp, colIndex, rowIndex);
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/powerUp/item_coin.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void power() {
		gp.score+=50;
	}

}
