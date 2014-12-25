package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class Skyscraper extends ResourceCollector {
	//TODO: img
	public Skyscraper(Player player) {
		super("Skyscraper", Type.RescourceCollector, 517, player, 
				400, 100, 3, 
				300, 2, 5, Layer.Ground,
				0, 0, 100);
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
				+ "<i>Skyscrapers produce a great amount of people, but you will need a good running country to make it worth the money.</i></body></html>";
	}

}
