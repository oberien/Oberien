package model.building.resourceCollector;

import model.Layer;
import model.Type;
import model.player.Player;

public class NuclearReactor extends ResourceCollector {
	//TODO: img
	public NuclearReactor(Player player) {
		super("Nuclear Reactor", Type.RescourceCollector, 518, player, 
				500, 250, 25,
				100, 0, 3, Layer.Ground,
				0, 50, 0);
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
				+ "<i>The nuclear reactor uses uran to produces great amounts of energy.</i></body></html>";
	}
}
