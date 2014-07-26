package model.building.producing;

import model.Layer;
import model.Player;
import model.Type;

public class Factory extends Producing {

	public Factory(Player player) {
		super("Factory", Type.Producing, 321, player,
				75, 75, 0,
				100, 0, 5, 
				0, true, false, false, false, false, Layer.Ground,
				Type.Tank, 10, 1);
	}
}
