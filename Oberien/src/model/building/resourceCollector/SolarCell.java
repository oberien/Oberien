package model.building.resourceCollector;

import model.Layer;
import model.Player;
import model.Type;

public class SolarCell extends ResourceCollector {

	public SolarCell(Player player) {
		super("Solar Cell", Type.RescourceCollector, 256, player, 
				5, 0, 0,
				25, 0, 5, Layer.Ground,
				0, 2, 0);
	}
}
