package model.unit;

import model.Layer;
import model.Model;
import model.Player;
import model.Type;
import model.map.FieldList;

public class Unit extends Model {
	private byte[] canPass;
	
	public Unit(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewrange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, viewrange, 
				movespeed, moveGround, moveWater, moveAir, moveUnderground, moveUnderwater, defaultLayer);
		this.canPass = FieldList.getInstance().getCanPass(type);
	}
	
	public boolean canPass(byte b) {
		for (int i = 0; i < canPass.length; i++) {
			if (canPass[i] == b) {
				return true;
			}
		}
		return false;
	}
}

