package model.unit.tank;

import model.Layer;
import model.Player;
import model.Type;

public class Leopard5 extends Tank {

	public Leopard5(Player player) {
		super("Leopard 5", Type.Tank, 64, player, 
				50, 20, 5,
				250, 5, 7,
				7, true, false, false, false, false, Layer.Ground,
				85, 0, 50, 3, Type.Tank);
	}

}
