package model.building.turret;

import model.Layer;
import model.Player;
import model.Type;

public class LaserCannon extends Turret {

	public LaserCannon(Player player) {
		super("Laser Cannon", Type.Turret, 353, player, 
				75, 75, 0,
				75, 5, 7, Layer.Ground,
				95, 99, 3, Type.Robot);
	}
}
