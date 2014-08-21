package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class NuclearReactor extends ResourceCollector {

	public NuclearReactor(Player player) {
		super("Nuclear Reactor", Type.RescourceCollector, 259, player, 
				500, 250, 25,
				100, 0, 3, Layer.Ground,
				0, 50, 0);
	}
}
