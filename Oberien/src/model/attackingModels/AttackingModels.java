package model.attackingModels;

import model.Layer;
import model.Model;
import model.Player;
import model.Type;

public class AttackingModels extends Model {
	private int strike; 
	private int evade;
	private int damage;
	private int attackrange;
	private Type strongAgainst;
	
	public AttackingModels(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewrange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer,
			int strike, int evade, int damage, int attackrange, Type strongAgainst) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, viewrange, 
				movespeed, moveGround, moveWater, moveAir, moveUnderground, moveUnderwater, defaultLayer);
		this.strike = strike;
		this.evade = evade;
		this.damage = damage;
		this.attackrange = attackrange;
		this.strongAgainst = strongAgainst;
	}
	
	public int getStrike() {
		return strike;
	}

	public int getEvade() {
		return evade;
	}

	public int getDamage() {
		return damage + (int)((double)damage/10*getLevel());
	}

	public int getAttackrange() {
		return attackrange;
	}
	
	public Type getStrongAgainst() {
		return strongAgainst;
	}
}
