package model.building.resourceCollector;

import model.Layer;
import model.Player;
import model.Type;
import model.building.Building;

public class ResourceCollector extends Building {
	private int producingMoney;
	private int producingEnergy;
	private int producingPopulation;
	
	public ResourceCollector(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewrange, 
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer,
			int producingMoney, int producingEnergy, int producingPopulation) {
		super(name, type, id, player, costMoney, costEnergy, costPopulation, maxLife,
				defense, viewrange, movespeed, moveGround, moveWater, moveAir,
				moveUnderground, moveUnderwater, defaultLayer);
		this.producingEnergy = producingEnergy;
		this.producingMoney = producingMoney;
		this.producingPopulation = producingPopulation;
	}
	
	public int getProducingMoney() {
		return producingMoney;
	}

	public int getProducingEnergy() {
		return producingEnergy;
	}

	public int getProducingPopulation() {
		return producingPopulation;
	}
}
