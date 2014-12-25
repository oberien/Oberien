package model;

public interface BuildingModel {
	public Type getBuilds();
	public int getBuildSpeed();
	public int getBuildRange();
	public Model getCurrentBuilding();
	public void setCurrentBuilding(Model m);
}
