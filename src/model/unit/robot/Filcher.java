package model.unit.robot;

import model.Layer;
import model.Type;
import model.player.Player;

public class Filcher extends Robot {
	//TODO: img
	public Filcher(Player player) {
		super("Filcher", Type.Robot, 35, player, 
				250, 150, 0, 
				800, 25, 0, 2, 
				1, true, false, false, false, false, Layer.Ground, 
				10, 100, 2, null);
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
				+ "<i>Ultra defensive Robot with nearly no mobility.</i></body></html>";
	}
}
