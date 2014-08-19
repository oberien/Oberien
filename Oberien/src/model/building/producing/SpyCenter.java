package model.building.producing;

import model.Layer;
import model.Player;
import model.Type;

public class SpyCenter extends Producing {

	public SpyCenter(Player player) {
		super("Spy Center", Type.Producing, 322, player,
				75, 75, 0,
				100, 0, 5,
				Type.Recon, 10, 1, Layer.Ground);
	}
}
