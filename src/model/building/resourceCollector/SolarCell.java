package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class SolarCell extends ResourceCollector {

	public SolarCell(Player player) {
		super("Solar Cell", Type.RescourceCollector, 513, player, 
				5, 0, 0,
				25, 0, 5, Layer.Ground,
				0, 2, 0);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Producing money: " + getProducingMoney() + "<br>Producing energy: " 
				+ getProducingEnergy() + "<br>Producing population: " + getProducingPopulation() + "<br><br>"
				+ "<i>Solar Cells are the basic energy producers.</i></body></html>";
	}
}
