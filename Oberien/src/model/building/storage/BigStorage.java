package model.building.storage;

import model.Layer;
import model.Type;
import model.player.Player;

public class BigStorage extends Storage {

	public BigStorage(Player player) {
		super("Big Storage", Type.Storage, 289, player, 
				500, 500, 0, 
				10, 0, 3, Layer.Ground,
				500, 0, 0);	
		}
}
