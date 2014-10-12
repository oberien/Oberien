package model.unit;

import model.Layer;
import model.Model;
import model.Type;
import model.map.FieldList;
import model.player.Player;

public class Unit extends Model {
	private boolean moveGround;
	private boolean moveWater;
	private boolean moveAir;
	private boolean moveUnderground;
	private boolean moveUnderwater;
	
	private int movespeed;
	
	private int evade;
	private byte[] canPass;
	private boolean moved;
	
	public Unit(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int evade, int viewRange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, viewRange, defaultLayer);
		this.movespeed = movespeed;
		this.moveGround = moveGround;
		this.moveWater = moveWater;
		this.moveAir = moveAir;
		this.moveUnderground = moveUnderground;
		this.moveUnderwater = moveUnderwater;
		this.evade = evade;
		this.canPass = FieldList.getInstance().getCanPass(type);
	}
	
	public boolean isMoveGround() {
		return moveGround;
	}

	public boolean isMoveWater() {
		return moveWater;
	}

	public boolean isMoveAir() {
		return moveAir;
	}

	public boolean isMoveUnderground() {
		return moveUnderground;
	}

	public boolean isMoveUnderwater() {
		return moveUnderwater;
	}

	public int getMovespeed() {
		return movespeed;
	}
	
	public int getEvasion() {
		return evade;
	}
	
	public boolean canPass(byte b) {
		for (int i = 0; i < canPass.length; i++) {
			if (canPass[i] == b) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}

