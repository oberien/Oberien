package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class Soldier extends Infantry {

	public Soldier(Player player) {
		super("Soldier", Type.Infantry, 0, player, 
				10, 1, 1, 
				50, 5, 10, 5,
				5, true, false, false, false, false, Layer.Ground,
				90, 100, 1, Type.Infantry);
	}

}
