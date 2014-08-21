package model.unit.robot;

import model.Layer;
import model.Type;
import model.player.Player;

public class Strider extends Robot {

	public Strider(Player player) {
		super("Strider", Type.Robot, 32, player, 
				20, 10, 0,
				50, 2, 7, 5,
				10, true, false, false, false, false, Layer.Ground,
				95, 100, 1, null);
	}

}
