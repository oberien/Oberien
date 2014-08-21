package model.unit.builder;

import model.Layer;
import model.Type;
import model.player.Player;

public class ResourceCollectorBuilder extends Builder {

	public ResourceCollectorBuilder(Player player) {
		super("Resource Collector Builder", Type.Builder, 160, player, 
				25, 25, 1,
				10, 0, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.RescourceCollector, 5, 1);
	}
	
}
