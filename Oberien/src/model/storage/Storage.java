package model.storage;

import java.util.ArrayList;

import model.Layer;
import model.Model;
import model.Player;
import model.Type;

public class Storage extends Model {

	private int storagePlus;
	private int populationStoragePlus;
	private int canStore;
	private ArrayList<Model> stored;
	
	public Storage(String name, Type type, int id, Player player,
			int costMoney, int costEnergy, int costPopulation, 
			int maxLife, int defense, int viewrange,
			int movespeed, boolean moveGround, boolean moveWater, boolean moveAir, boolean moveUnderground, boolean moveUnderwater, Layer defaultLayer,
			int storagePlus, int populationStoragePlus, int canStore) {
		super(name, type, id, player, 
				costMoney, costEnergy, costPopulation, 
				maxLife, defense, viewrange, 
				movespeed, moveGround, moveWater, moveAir, moveUnderground, moveUnderwater, defaultLayer);
		this.storagePlus = storagePlus;
		this.populationStoragePlus = populationStoragePlus;
		this.canStore = canStore;
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
}
