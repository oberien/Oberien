package model.building.producing;

import model.Layer;
import model.Player;
import model.Type;

public class Barracks extends Producing {

	public Barracks(Player player) {
		super("Barracks", Type.Producing, 320, player,
				75, 75, 0,
				100, 0, 5, 
				0, true, false, false, false, false, Layer.Ground,
				Type.Infantry, 10, 1);
	}
}
