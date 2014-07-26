package model.unit.infantry;

import model.Layer;
import model.Player;
import model.Type;

public class Soldier extends Infantry {

	public Soldier(Player player) {
		super("Soldier", Type.Infantry, 0, player, 
				10, 1, 1, 
				50, 5, 5,
				5, true, false, false, false, false, Layer.Ground,
				90, 0, 10, 1, Type.Infantry);
	}

}
