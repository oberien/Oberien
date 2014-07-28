package model.unit.robot;
import model.AttackingModel;
import model.Layer;
import model.Player;
import model.Type;
import model.unit.Unit;

public class Robot extends Unit implements AttackingModel {
	private int damage;
	private int strike;
	private int attackrange;
	private Type strongAgainst;
	
	public Robot(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int evade, int viewrange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer,
			int damage, int strike, int attackrange, Type strongAgainst) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, evade, viewrange, 
				movespeed, moveGround, moveWater, moveAir, moveUnderground, moveUnderwater, defaultLayer);
		this.damage = damage;
		this.strike = strike;
		this.attackrange = attackrange;
		this.strongAgainst = strongAgainst;
	}

	@Override
	public int getStrike() {
		return strike;
	}

	@Override
	public int getDamage() {
		return damage + (int)((double)damage/10*getLevel());
	}

	@Override
	public int getAttackrange() {
		return attackrange;
	}

	@Override
	public Type getStrongAgainst() {
		return strongAgainst;
	}

}
