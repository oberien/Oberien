package model.unit.robot;
import model.AttackingModel;
import model.Layer;
import model.Player;
import model.Type;
import model.unit.Unit;

public class Robot extends Unit implements AttackingModel {
	private int damage;
	private int strike;
	private int attackRange;
	private Type strongAgainst;
	
	public Robot(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int evade, int viewRange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer,
			int damage, int strike, int attackRange, Type strongAgainst) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, evade, viewRange, 
				movespeed, moveGround, moveWater, moveAir, moveUnderground, moveUnderwater, defaultLayer);
		this.damage = damage;
		this.strike = strike;
		this.attackRange = attackRange;
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
	public int getAttackRange() {
		return attackRange;
	}

	@Override
	public Type getStrongAgainst() {
		return strongAgainst;
	}

}
