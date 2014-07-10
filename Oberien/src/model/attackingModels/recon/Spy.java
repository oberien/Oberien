package model.attackingModels.recon;

import model.Layer;
import model.Player;
import model.Type;
import model.attackingModels.AttackingModels;

public class Spy extends AttackingModels {

	public Spy(Player player) {
		super("Spy", Type.Recon, 96, player, 
				20, 5, 1,
				1, 0, 30,
				15, true, false, false, false, false, Layer.Ground,
				0, 0, 0, 0, null);
	}

}
