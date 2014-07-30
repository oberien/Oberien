package model.building.storage;

import model.Layer;
import model.Player;
import model.Type;

public class MediumStorage extends Storage {

	public MediumStorage(Player player) {
		super("Medium Storage", Type.Storage, 289, player, 
				250, 250, 0, 
				10, 0, 3, Layer.Ground,
				250, 0, 0);	
		}
}
