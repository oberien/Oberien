package model.building.storage;

import model.Layer;
import model.Player;
import model.Type;

public class SmallStorage extends Storage {

	public SmallStorage(Player player) {
		super("Small Storage", Type.Storage, 288, player, 
				100, 100, 0, 
				10, 0, 3, 
				0, true, false, false, false, false, Layer.Ground, 
				100, 0, 0);	
		}
}
