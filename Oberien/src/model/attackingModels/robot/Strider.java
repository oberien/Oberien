package model.attackingModels.robot;

import model.Layer;
import model.Player;
import model.Type;
import model.attackingModels.AttackingModels;

public class Strider extends AttackingModels {

	public Strider(Player player) {
		super("Strider", Type.Robot, 32, player, 
				20, 10, 0,
				50, 2, 5,
				10, true, false, false, false, false, Layer.Ground,
				95, 10, 7, 1, null);
	}

}
