package model;

public interface BuildingModel {
	public Type getBuilds();
	public int getBuildSpeed();
	public int getBuildrange();
	public Model getCurrentBuilding();
	public void setCurrentBuilding(Model m);
}
