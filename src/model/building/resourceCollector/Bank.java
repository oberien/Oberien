package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class Bank extends ResourceCollector {
	//TODO: img
	public Bank(Player player) {
		super("Bank", Type.RescourceCollector, 515, player, 
				0, 10, 2,
				50, 0, 5, Layer.Ground,
				5, 0, 0);
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
				+ "<i>The basic money maker. You will always need money to be a rich bitch.</i></body></html>";
	}
}
