package model.building.producing;

import model.Layer;
import model.Type;
import model.player.Player;

public class MilitaryTrainingArea extends Producing {

	public MilitaryTrainingArea(Player player) {
		super("Military Training Area", Type.Producing, 640, player,
				75, 75, 0,
				100, 0, 5,
				Type.Infantry, 10, 1, Layer.Ground);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Builds: " + getBuilds() + "<br>Buildspeed: " + getBuildSpeed() + "<br>Buildrange: " + getBuildRange() + "<br><br>"
				+ "<i>Train you infantry units in the military training area. Infantry units are mostly light armored, mobile and deal medium damage.</i></body></html>";
	}
}
