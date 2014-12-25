package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class HamsterWheel extends ResourceCollector {

	public HamsterWheel(Player player) {
		super("Hamster Wheel", Type.RescourceCollector, 512, player, 
				2, 0, 0,
				5, 0, 1, Layer.Ground,
				0, 1, 0);
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
				+ "<i>Woa! It's a hamster!</i></body></html>";
	}
}
