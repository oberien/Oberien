package model.unit.builder;

import model.BuildingModel;
import model.Layer;
import model.Model;
import model.Player;
import model.Type;
import model.unit.Unit;

public class Builder extends Unit implements BuildingModel {
	private Type builds;
	private int buildSpeed;
	private int buildRange;
	
	private Model currentBuilding;

	public Builder(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int evade, int viewrange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer, 
			Type builds, int buildSpeed, int buildRange) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, evade, viewrange, 
				movespeed, moveGround, moveWater, moveAir, moveUnderground, moveUnderwater, defaultLayer);
		this.builds = builds;
		this.buildSpeed = buildSpeed;
		this.buildRange = buildRange;
		this.currentBuilding = null;
	}

	@Override
	public Type getBuilds() {
		return builds;
	}

	@Override
	public int getBuildSpeed() {
		return buildSpeed;
	}

	@Override
	public int getBuildRange() {
		return buildRange;
	}

	@Override
	public Model getCurrentBuilding() {
		return currentBuilding;
	}

	@Override
	public void setCurrentBuilding(Model m) {
		this.currentBuilding = m;
	}
}
