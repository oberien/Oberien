package model.building.turret;

import model.Layer;
import model.Player;
import model.Type;

public class LaserCannon extends Turret {

	public LaserCannon(Player player) {
		super("Laser Cannon", Type.Turret, 353, player, 
				75, 75, 0,
				75, 5, 7, 
				0, true, false, false, false, false, Layer.Ground,
				95, 15, 3, Type.Robot);
	}
}
