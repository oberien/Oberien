package model.buildingModels.builder;

import model.Layer;
import model.Player;
import model.Type;
import model.buildingModels.BuildingModels;

public class HighRangeTurretBuilder extends BuildingModels {

	public HighRangeTurretBuilder(Player player) {
		super("High Range Turret Builder", Type.Builder, 164, player, 
				25, 25, 1,
				10, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.Turret, 2, 10);
	}
	
}
