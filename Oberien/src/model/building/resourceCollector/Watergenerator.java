package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class Watergenerator extends ResourceCollector {

	public Watergenerator(Player player) {
		super("Watergenerator", Type.RescourceCollector, 261, player, 
				150, 30, 5,
				250, 1, 1, Layer.Ground,
				0, 25, 0);
	}
}
