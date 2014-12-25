package model.building.storage;

import model.Layer;
import model.Type;
import model.player.Player;

public class BigStorage extends Storage {

	public BigStorage(Player player) {
		super("Big Storage", Type.Storage, 578, player, 
				500, 500, 0, 
				10, 0, 3, Layer.Ground,
				500, 0, 0);	
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Storage: " + getStoragePlus() + " money, " + getStoragePlus() + " energy, " + getPopulationStoragePlus() + " population, " + getCanStore() + " units<br><br>"
				+ "<i>A whole lot of storage! Woohoo!</i></body></html>";
	}
}
