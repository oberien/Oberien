package model.unit.robot;

import model.Layer;
import model.player.Player;
import model.Type;

public class Scrounger extends Robot {
	//TODO: img
	public Scrounger(Player player) {
		super("Scrounger", Type.Robot, 36, player, 
				500, 250, 0, 
				500, 10, 5, 6,
				1, true, false, false, false, false, Layer.Ground, 
				50, 100, 6, Type.Infantry);
	}
	
	@Override
	public String getDescription() {
		return "<html><body><h4>" + getName() + "</h4>"
				+ "Type: " + getType() + "<br>"
				+ "Costs " + getCostMoney() + " money, " + getCostEnergy() + " energy, " + getCostPopulation() + " population<br>"
				+ "HP: " + getMaxLife() + "<br>Defense: " + getDefense() + "<br>Viewrange: " + getViewRange() + "<br>"
				+ "Movespeed: " + getMovespeed() + "<br>Can move on (Ground/Water/Air/Underground/Underwater): " + isMoveGround() + "/" + isMoveWater() + "/"
				+ isMoveAir() + "/" + isMoveUnderground() + "/" + isMoveUnderwater() + "<br>Standard layer: " + getDefaultLayer() + "<br>"
				+ "Damage: " + getDamage() + "<br>Strikechance: " + getStrike() + "%<br>Attackrange: " + getAttackRange() + "<br>Strong against: " + getStrongAgainst() + "<br><br>"
				+ "<i>Very defensive tank with low mobility.</i></body></html>";
	}


}
