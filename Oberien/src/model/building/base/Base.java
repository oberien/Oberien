package model.building.base;

import java.util.ArrayList;

import model.Layer;
import model.Model;
import model.Type;
import model.Player;
import model.building.Producing;

public class Base extends Producing {
	
	private int storagePlus;
	private int populationStoragePlus;
	
	private int canStore;
	private ArrayList<Model> stored;
	
	public Base(Player player) {
		super("Base", Type.Base, 512, player,
				500, 500, 50,
				500, 0, 10,
				Type.Builder, 25, 1, Layer.Ground);
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
		return 10;
	}
	
	public int getProducingEnergy() {
		return 10;
	}
	
	public int getProducingPopulation() {
		return 1;
	}
}
