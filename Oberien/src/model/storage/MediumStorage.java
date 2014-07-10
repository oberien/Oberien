package model.storage;

import model.Layer;
import model.Player;
import model.Type;

public class MediumStorage extends Storage {

	public MediumStorage(Player player) {
		super("Medium Storage", Type.Storage, 289, player, 
				250, 250, 0, 
				10, 0, 3, 
				0, true, false, false, false, false, Layer.Ground, 
				250, 0, 0);	
		}
}
