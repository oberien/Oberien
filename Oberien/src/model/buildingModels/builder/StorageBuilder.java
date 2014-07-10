package model.buildingModels.builder;

import model.Layer;
import model.Player;
import model.Type;
import model.buildingModels.BuildingModels;

public class StorageBuilder extends BuildingModels {

	public StorageBuilder(Player player) {
		super("Storage Builder", Type.Builder, 161, player, 
				25, 25, 1,
				10, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.Storage, 5, 1);
	}
	
}
