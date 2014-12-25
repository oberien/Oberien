package model.building.producing;

import model.Layer;
import model.Type;
import model.player.Player;

public class TankManufactory extends Producing {

	public TankManufactory(Player player) {
		super("Factory", Type.Producing, 641, player,
				75, 75, 0,
				100, 0, 5,
				Type.Tank, 10, 1, Layer.Ground);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Builds: " + getBuilds() + "<br>Buildspeed: " + getBuildSpeed() + "<br>Buildrange: " + getBuildRange() + "<br><br>"
				+ "<i>Tank manufactories produce all sorts of tanks. Tanks are heavy armored, long range units.</i></body></html>";
	}
}
