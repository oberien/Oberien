package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class Windmill extends ResourceCollector {
	//TODO: img
	public Windmill(Player player) {
		super("Windmill", Type.RescourceCollector, 516, player, 
				250, 50, 2, 
				100, 0, 2, Layer.Ground,
				0, 10, 0);
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
				+ "<i>Windmills are a economic way of producing energy. They cost quite a lot, but produce a good amount, too.</i></body></html>";
	}
}
