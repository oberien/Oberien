package model.building.storage;

import model.Layer;
import model.Player;
import model.Type;

public class BigStorage extends Storage {

	public BigStorage(Player player) {
		super("Big Storage", Type.Storage, 289, player, 
				500, 500, 0, 
				10, 0, 3, 
				0, true, false, false, false, false, Layer.Ground, 
				500, 0, 0);	
		}
}
