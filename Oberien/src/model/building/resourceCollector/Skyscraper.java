package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class Skyscraper extends ResourceCollector {

	public Skyscraper(Player player) {
		super("Skyscraper", Type.RescourceCollector, 262, player, 
				400, 100, 3, 
				300, 2, 5, Layer.Ground,
				0, 0, 100);
	}

}
