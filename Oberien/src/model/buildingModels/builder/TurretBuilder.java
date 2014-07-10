package model.buildingModels.builder;

import model.Layer;
import model.Player;
import model.Type;
import model.buildingModels.BuildingModels;

public class TurretBuilder extends BuildingModels {

	public TurretBuilder(Player player) {
		super("Turret Builder", Type.Builder, 163, player, 
				25, 25, 1,
				10, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.Turret, 5, 1);
	}
	
}
