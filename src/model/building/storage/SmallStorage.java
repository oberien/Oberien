package model.building.storage;

import model.Layer;
import model.Type;
import model.player.Player;

public class SmallStorage extends Storage {

	public SmallStorage(Player player) {
		super("Small Storage", Type.Storage, 576, player, 
				100, 100, 0, 
				10, 0, 3, Layer.Ground,
				100, 0, 0);	
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Storage: " + getStoragePlus() + " money, " + getStoragePlus() + " energy, " + getPopulationStoragePlus() + " population, " + getCanStore() + " units<br><br>"
				+ "<i>A bit more storage for you.</i></body></html>";
	}
}
