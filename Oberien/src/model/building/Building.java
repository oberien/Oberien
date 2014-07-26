package model.building;

import model.Layer;
import model.Model;
import model.Player;
import model.Type;

public class Building extends Model {

	public Building(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewrange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, viewrange, movespeed, 
				moveGround, moveWater, moveAir, moveUnderground, moveUnderwater, defaultLayer);
	}

}
