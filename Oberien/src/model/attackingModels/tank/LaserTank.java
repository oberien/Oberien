package model.attackingModels.tank;

import model.Layer;
import model.Player;
import model.Type;
import model.attackingModels.AttackingModels;

public class LaserTank extends AttackingModels {

	public LaserTank(Player player) {
		super("Laser Tank", Type.Tank, 65, player, 
				25, 50, 3,
				150, 5, 7,
				7, true, false, false, false, false, Layer.Ground,
				95, 0, 20, 2, Type.Robot);
	}

}
