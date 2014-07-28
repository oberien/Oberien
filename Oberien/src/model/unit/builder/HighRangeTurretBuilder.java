package model.unit.builder;

import model.Layer;
import model.Player;
import model.Type;

public class HighRangeTurretBuilder extends Builder {

	public HighRangeTurretBuilder(Player player) {
		super("High Range Turret Builder", Type.Builder, 164, player, 
				25, 25, 1,
				10, 0, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.Turret, 2, 10);
	}
	
}
