package model.building.producing;

import model.Layer;
import model.Type;
import model.player.Player;

public class Barracks extends Producing {

	public Barracks(Player player) {
		super("Barracks", Type.Producing, 320, player,
				75, 75, 0,
				100, 0, 5,
				Type.Infantry, 10, 1, Layer.Ground);
	}
}
