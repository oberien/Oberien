package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class House extends ResourceCollector {

	public House(Player player) {
		super("House", Type.RescourceCollector, 514, player, 
				10, 0, 0,
				35, 0, 5, Layer.Ground,
				0, 0, 1);
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
				+ "<i>The basic element to make population live in the country.</i></body></html>";
	}
}
