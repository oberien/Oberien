package model.building.resourceCollector;

import model.Layer;
import model.Player;
import model.ProducingModel;
import model.Type;
import model.building.Building;

public class ResourceCollector extends Building implements ProducingModel {
	private int producingMoney;
	private int producingEnergy;
	private int producingPopulation;
	
	public ResourceCollector(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewRange, Layer defaultLayer,
			int producingMoney, int producingEnergy, int producingPopulation) {
		super(name, type, id, player, costMoney, costEnergy, costPopulation,
				maxLife, defense, viewRange, defaultLayer);
		this.producingEnergy = producingEnergy;
		this.producingMoney = producingMoney;
		this.producingPopulation = producingPopulation;
	}
	
	@Override
	public int getProducingMoney() {
		return producingMoney;
	}

	@Override
	public int getProducingEnergy() {
		return producingEnergy;
	}

	@Override
	public int getProducingPopulation() {
		return producingPopulation;
	}
}
