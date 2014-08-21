package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class Windmill extends ResourceCollector {

	public Windmill(Player player) {
		super("Windmill", Type.RescourceCollector, 260, player, 
				250, 50, 2, 
				100, 0, 2, Layer.Ground,
				0, 10, 0);
	}
}
