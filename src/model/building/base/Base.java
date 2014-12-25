package model.building.base;

import model.*;
import model.player.Player;

import java.util.ArrayList;

public class Base extends Model implements BuildingModel, ProducingModel, StoringModel {
	private Type builds;
	private int buildSpeed;
	private int buildRange;
	private Model currentBuilding;
	
	private int producingMoney;
	private int producingEnergy;
	private int producingPopulation;
	
	private int storagePlus;
	private int populationStoragePlus;
	
	private int canStore;
	private ArrayList<Model> stored;
	
	public Base(Player player) {
		super("Base", Type.Base, 1024, player,
				500, 500, 50,
				500, 0, 10, Layer.Ground);
		this.builds = Type.Builder;
		this.buildSpeed = 10;
		this.buildRange = 1;
		this.currentBuilding = null;
		this.producingMoney = 10;
		this.producingEnergy = 10;
		this.producingPopulation = 1;
		this.storagePlus = 100;
		this.populationStoragePlus = 10;
		this.canStore = 5;
		this.stored = new ArrayList<Model>();
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Producing money: " + getProducingMoney() + "<br>Producing energy: " 
				+ getProducingEnergy() + "<br>Producing population: " + getProducingPopulation() + "<br>"
				+ "Builds: " + getBuilds() + "<br>Buildspeed: " + getBuildSpeed() + "<br>Buildrange: " + getBuildRange() + "<br>"
				+ "Storage: " + getStoragePlus() + " money, " + getStoragePlus() + " energy, " + getPopulationStoragePlus() + " population, " + getCanStore() + " units<br><br>"
				+ "<i>The base is the starting point of every operation.</i></body></html>";
	}
	
	@Override
	public int getStoragePlus() {
		return storagePlus;
	}
	
	@Override
	public int getPopulationStoragePlus() {
		return populationStoragePlus;
	}
	
	@Override
	public int getCanStore() {
		return canStore;
	}
	
	@Override
	public Model[] getStored() {
		Model[] ret = new Model[stored.size()];
		ret = stored.toArray(ret);
		return ret;
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
