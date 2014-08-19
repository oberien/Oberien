package model;

public interface StoragingModel {
	public int getStoragePlus();
	public int getPopulationStoragePlus();
	public int getCanStore();
	public Model[] getStored();
}
