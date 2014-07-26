package model.building.base;

import java.util.ArrayList;

import model.Layer;
import model.Model;
import model.Type;
import model.Player;
import model.building.Building;

public class Base extends Building {
	
	private int storagePlus;
	private int populationStoragePlus;
	
	private int canStore;
	private ArrayList<Model> stored;
	
	public Base(Player player) {
		super("Base", Type.Base, 512, player,
				500, 500, 50,
				500, 0, 10,
				0, true, false, false, false, false, Layer.Ground);
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
}
