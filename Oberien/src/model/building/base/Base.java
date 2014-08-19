package model.building.base;

import java.util.ArrayList;

import model.BuildingModel;
import model.Layer;
import model.Model;
import model.ProducingModel;
import model.StoragingModel;
import model.Type;
import model.Player;
import model.building.producing.Producing;

public class Base extends Model implements BuildingModel, ProducingModel, StoragingModel {
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
		super("Base", Type.Base, 512, player,
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
	
	public int getStoragePlus() {
		return storagePlus;
	}
	
	public int getPopulationStoragePlus() {
		return populationStoragePlus;
	}
	
	public int getCanStore() {
		return canStore;
	}
	
	public Model[] getStored() {
		Model[] ret = new Model[stored.size()];
		ret = stored.toArray(ret);
		return ret;
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
