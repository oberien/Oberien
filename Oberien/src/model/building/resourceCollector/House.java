package model.building.resourceCollector;

import model.Layer;
import model.Player;
import model.Type;

public class House extends ResourceCollector {

	public House(Player player) {
		super("House", Type.RescourceCollector, 258, player, 
				10, 0, 0,
				35, 0, 5, Layer.Ground,
				0, 0, 1);
	}
}
