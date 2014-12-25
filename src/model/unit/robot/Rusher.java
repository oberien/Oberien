package model.unit.robot;

import model.Layer;
import model.Type;
import model.player.Player;

public class Rusher extends Robot {
	//TODO: img
	public Rusher(Player player) {
		super("Rusher", Type.Robot, 33, player, 
				20, 20, 0, 
				200, 2, 10, 5,
				4, true, false, false, false, false, Layer.Ground, 
				15, 100, 1, null);
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
				+ "<i>Fast unit with low cost. Good for rushing.</i></body></html>";
	}


}
