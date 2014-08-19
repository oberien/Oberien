package model.unit.infantry;

import model.Layer;
import model.Player;
import model.Type;

public class Warrior extends Infantry {

	public Warrior(Player player) {
		super("Warrior", Type.Infantry, 4, player, 
				250, 20, 1,
				250, 8, 5, 3, 
				2, true, false, false, false, false, Layer.Ground, 
				80, 98, 2, Type.Robot);
	}
}
