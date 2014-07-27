package model.unit.builder;

import model.Layer;
import model.Player;
import model.Type;

public class ProducingBuilder extends Builder {

	public ProducingBuilder(Player player) {
		super("Producing Builder", Type.Builder, 162, player, 
				25, 25, 1,
				10, 0, 3,
				8, true, false, false, false, false, Layer.Ground, 
				Type.Producing, 5, 1);
	}
	
}