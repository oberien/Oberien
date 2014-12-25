package model.building.producing;

import model.Layer;
import model.Type;
import model.player.Player;

public class RoboHub extends Producing {
	//TODO: img
	public RoboHub(Player player) {
		super("Robo Hub", Type.Producing, 642, player,
				75, 75, 0,
				100, 0, 5,
				Type.Robot, 10, 1, Layer.Ground);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Builds: " + getBuilds() + "<br>Buildspeed: " + getBuildSpeed() + "<br>Buildrange: " + getBuildRange() + "<br><br>"
				+ "<i>Robo Hubs produce robots. Robots are mostly heavy armored, but unmobile units.</i></body></html>";
	}
}
