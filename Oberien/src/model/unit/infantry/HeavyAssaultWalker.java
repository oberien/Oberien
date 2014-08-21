package model.unit.infantry;

import model.Layer;
import model.Type;
import model.player.Player;

public class HeavyAssaultWalker extends Infantry {

	public HeavyAssaultWalker(Player player) {
		super("Heavy Assault Walker", Type.Infantry, 2, player, 
				150, 25, 1, 
				500, 5, 10, 3, 
				3, true, false, false, false, false, Layer.Ground,
				70, 100, 1, null);
	}

}
