package model.unit.robot;

import model.Layer;
import model.Type;
import model.player.Player;

public class AnnoyBot extends Robot {

	public AnnoyBot(Player player) {
		super("Annoy Bot", Type.Robot, 33, player, 
				10, 1, 0, 
				1, 0, 0, 1,
				2, true, false, false, false, false, Layer.Ground, 
				0, 0, 0, null);
	}
}
