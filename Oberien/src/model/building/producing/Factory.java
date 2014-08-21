package model.building.producing;

import model.Layer;
import model.Type;
import model.player.Player;

public class Factory extends Producing {

	public Factory(Player player) {
		super("Factory", Type.Producing, 321, player,
				75, 75, 0,
				100, 0, 5,
				Type.Tank, 10, 1, Layer.Ground);
	}
}
