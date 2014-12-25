package model.building.producing;

import model.Layer;
import model.Type;
import model.player.Player;

public class SpiderNest extends Producing {
	//TODO: img
	public SpiderNest(Player player) {
		super("Spider Nest", Type.Producing, 643, player,
				75, 75, 0,
				100, 0, 5,
				Type.Spider, 10, 1, Layer.Ground);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ ">Standard layer: " + getDefaultLayer() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Builds: " + getBuilds() + "<br>Buildspeed: " + getBuildSpeed() + "<br>Buildrange: " + getBuildRange() + "<br><br>"
				+ "<i>Spider Nests are used to create new spiders. Spiders are very mobile, but very light armored with medium damage.</i></body></html>";
	}
}
