package model.building.turret;

import model.Layer;
import model.Player;
import model.Type;

public class GatlingGun extends Turret {

	public GatlingGun(Player player) {
		super("Gatling Gun", Type.Turret, 352, player, 
				50, 50, 0, 
				100, 5, 7, 
				0, true, false, false, false, false, Layer.Ground,
				100, 10, 3, Type.Infantry);
	}
}
