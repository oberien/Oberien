package model.unit.builder;

import model.Layer;
import model.Player;
import model.Type;

public class StorageBuilder extends Builder {

	public StorageBuilder(Player player) {
		super("Storage Builder", Type.Builder, 161, player, 
				25, 25, 1,
				10, 0, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.Storage, 5, 1);
	}
	
}
