package model.unit.builder;

import model.Layer;
import model.Player;
import model.Type;

public class TurretBuilder extends Builder {

	public TurretBuilder(Player player) {
		super("Turret Builder", Type.Builder, 163, player, 
				25, 25, 1,
				10, 0, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.Turret, 5, 1);
	}
	
}
