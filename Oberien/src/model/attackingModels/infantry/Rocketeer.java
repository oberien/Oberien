package model.attackingModels.infantry;

import model.Layer;
import model.Player;
import model.Type;
import model.attackingModels.AttackingModels;

public class Rocketeer extends AttackingModels {

	public Rocketeer(Player player) {
		super("Rocketeer", Type.Infantry, 1, player, 
				15, 2, 1,
				30, 2, 7,
				6, true, false, false, false, false, Layer.Ground,
				75, 0, 10, 2, Type.Tank);
	}

}
