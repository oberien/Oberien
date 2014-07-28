package model.building.resourceCollector;

import model.Layer;
import model.Player;
import model.Type;

public class Skyscraper extends ResourceCollector {

	public Skyscraper(Player player) {
		super("Skyscraper", Type.RescourceCollector, 262, player, 
				400, 100, 3, 
				300, 2, 5, 
				0, true, false, false, false, false, Layer.Ground, 
				0, 0, 100);
	}

}
