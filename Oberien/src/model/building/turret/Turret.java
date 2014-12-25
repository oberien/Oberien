package model.building.turret;

import model.AttackingModel;
import model.Layer;
import model.Type;
import model.building.Building;
import model.player.Player;

public class Turret extends Building implements AttackingModel {
	private int damage;
	private int strike;
	private int attackRange;
	private Type strongAgainst;
	
	public Turret (String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewRange, Layer defaultLayer,
			int damage, int strike, int attackRange, Type strongAgainst) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, viewRange, defaultLayer);
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
