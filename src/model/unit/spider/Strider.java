package model.unit.spider;

import model.Layer;
import model.Type;
import model.player.Player;

public class Strider extends Spider {

	public Strider(Player player) {
		super("Strider", Type.Robot, 128, player, 
				20, 10, 0,
				50, 2, 7, 5,
				10, true, false, false, false, false, Layer.Ground,
				25, 100, 1, null);
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
				+ "<i>Striders are the basic spider unit with high mobility.</i></body></html>";
	}


}
