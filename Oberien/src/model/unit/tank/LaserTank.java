package model.unit.tank;

import model.Layer;
import model.Player;
import model.Type;

public class LaserTank extends Tank {

	public LaserTank(Player player) {
		super("Laser Tank", Type.Tank, 65, player, 
				25, 50, 3,
				150, 5, 20, 7,
				7, true, false, false, false, false, Layer.Ground,
				95, 99, 2, Type.Robot);
	}

}
