package model.building.storage;

import java.util.ArrayList;

import model.Layer;
import model.Model;
import model.Player;
import model.StoringModel;
import model.Type;
import model.building.Building;

public class Storage extends Building implements StoringModel {

	private int storagePlus;
	private int populationStoragePlus;
	private int canStore;
	private ArrayList<Model> stored;
	
	public Storage(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewrange, Layer defaultLayer,
			int storagePlus, int populationStoragePlus, int canStore) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, viewrange, defaultLayer);
		this.storagePlus = storagePlus;
		this.populationStoragePlus = populationStoragePlus;
		this.canStore = canStore;
		this.stored = new ArrayList<Model>();
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
}
