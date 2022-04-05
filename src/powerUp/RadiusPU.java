package powerUp;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class RadiusPU extends PowerUp{

	public RadiusPU(GamePanel gp, int colIndex, int rowIndex) {
		super(gp, colIndex, rowIndex);
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/powerUp/item_bombsize.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void power() {
		if(gp.player.bombRadius<=5) gp.player.bombRadius++;
	}

}
