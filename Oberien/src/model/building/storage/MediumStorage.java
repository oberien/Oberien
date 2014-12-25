package model.building.storage;

import model.Layer;
import model.Type;
import model.player.Player;

public class MediumStorage extends Storage {

	public MediumStorage(Player player) {
		super("Medium Storage", Type.Storage, 577, player, 
				250, 250, 0, 
				10, 0, 3, Layer.Ground,
				250, 0, 0);	
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Storage: " + getStoragePlus() + " money, " + getStoragePlus() + " energy, " + getPopulationStoragePlus() + " population, " + getCanStore() + " units<br><br>"
				+ "<i>Even more storage. *yey*</i></body></html>";
	}
}
