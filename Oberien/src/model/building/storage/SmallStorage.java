package model.building.storage;

import model.Layer;
import model.Type;
import model.player.Player;

public class SmallStorage extends Storage {

	public SmallStorage(Player player) {
		super("Small Storage", Type.Storage, 288, player, 
				100, 100, 0, 
				10, 0, 3, Layer.Ground,
				100, 0, 0);	
		}
}
