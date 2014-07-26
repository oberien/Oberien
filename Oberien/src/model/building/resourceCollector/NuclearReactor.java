package model.building.resourceCollector;

import model.Layer;
import model.Player;
import model.Type;

public class NuclearReactor extends ResourceCollector {

	public NuclearReactor(Player player) {
		super("Nuclear Reactor", Type.RescourceCollector, 259, player, 
				500, 250, 25,
				100, 0, 3,
				0, true, false, false, false, false, Layer.Ground,
				0, 50, 0);
	}
}
