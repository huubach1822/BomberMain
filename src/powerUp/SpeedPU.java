package powerUp;

import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class SpeedPU extends PowerUp{

	public SpeedPU(GamePanel gp, int colIndex, int rowIndex) {
		super(gp, colIndex, rowIndex);
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/powerUp/item_shoe.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void power() {
		if(gp.player.speed<=7) gp.player.speed+=1;
	}

}
