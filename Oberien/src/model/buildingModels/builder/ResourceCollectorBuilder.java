package model.buildingModels.builder;

import model.Layer;
import model.Player;
import model.Type;
import model.buildingModels.BuildingModels;

public class ResourceCollectorBuilder extends BuildingModels {

	public ResourceCollectorBuilder(Player player) {
		super("Resource Collector Builder", Type.Builder, 160, player, 
				25, 25, 1,
				10, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.RescourceCollector, 5, 1);
	}
	
}
