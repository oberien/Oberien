package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class Rocketeer extends Infantry {

	public Rocketeer(Player player) {
		super("Rocketeer", Type.Infantry, 1, player, 
				15, 2, 1,
				30, 2, 10, 7,
				6, true, false, false, false, false, Layer.Ground,
				75, 85, 2, Type.Tank);
	}

}
